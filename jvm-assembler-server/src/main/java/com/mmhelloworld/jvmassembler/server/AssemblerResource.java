package com.mmhelloworld.jvmassembler.server;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.objectweb.asm.Opcodes.AALOAD;
import static org.objectweb.asm.Opcodes.AASTORE;
import static org.objectweb.asm.Opcodes.ACONST_NULL;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ANEWARRAY;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.I2C;
import static org.objectweb.asm.Opcodes.I2L;
import static org.objectweb.asm.Opcodes.IADD;
import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.ICONST_2;
import static org.objectweb.asm.Opcodes.ICONST_3;
import static org.objectweb.asm.Opcodes.ICONST_4;
import static org.objectweb.asm.Opcodes.ICONST_5;
import static org.objectweb.asm.Opcodes.ICONST_M1;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.IMUL;
import static org.objectweb.asm.Opcodes.ISTORE;
import static org.objectweb.asm.Opcodes.ISUB;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.SIPUSH;

@Path("/assembler")
public class AssemblerResource {

    @POST
    @Path("/assemble")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AssemblerResponse assemble(CreateBytecode request) throws Exception {
        ClassWriter cw = null;
        MethodVisitor mv = null;
        Map<String, Object> env = new HashMap<>();

        for (Asm asm : request.getInstructions()) {
            switch (asm.getType()) {
                case Aaload:
                    mv.visitInsn(AALOAD);
                    break;
                case Aastore:
                    mv.visitInsn(AASTORE);
                    break;
                case Aconstnull:
                    mv.visitInsn(ACONST_NULL);
                    break;
                case Aload:
                    Asm.Aload aload = (Asm.Aload) asm;
                    mv.visitVarInsn(ALOAD, aload.getIndex());
                    break;
                case Anewarray:
                    Asm.Anewarray anewarray = (Asm.Anewarray) asm;
                    mv.visitTypeInsn(ANEWARRAY, anewarray.getDesc());
                    break;
                case Astore:
                    Asm.Astore astore = (Asm.Astore) asm;
                    mv.visitVarInsn(ASTORE, astore.getIndex());
                    break;
                case Areturn:
                    mv.visitInsn(ARETURN);
                    break;
                case Checkcast:
                    Asm.Checkcast checkcast = (Asm.Checkcast) asm;
                    mv.visitTypeInsn(CHECKCAST, checkcast.getDesc());
                    break;
                case ClassCodeStart:
                    Asm.ClassCodeStart classCodeStart = (Asm.ClassCodeStart) asm;
                    cw.visit(classCodeStart.getVersion(),
                        classCodeStart.getAcc(),
                        classCodeStart.getName(),
                        classCodeStart.getSig(),
                        classCodeStart.getParent(),
                        classCodeStart.getInterfaces());
                    break;
                case ClassCodeEnd:
                    cw.visitEnd();
                    Asm.ClassCodeEnd classCodeEnd = (Asm.ClassCodeEnd) asm;
                    try (OutputStream out = new FileOutputStream(classCodeEnd.getOut())) {
                        out.write(cw.toByteArray());
                    }
                    break;
                case CreateClass:
                    cw = new ClassWriter(((Asm.CreateClass) asm).getFlags());
                    break;
                case CreateLabel: {
                    String labelName = ((Asm.CreateLabel) asm).getLabel();
                    Label label = new Label();
                    env.put(labelName, label);
                    break;
                }
                case CreateMethod:
                    Asm.CreateMethod createMethod = (Asm.CreateMethod) asm;
                    mv = cw.visitMethod(
                        createMethod.getAcc(),
                        createMethod.getName(),
                        createMethod.getDesc(),
                        createMethod.getSig(),
                        createMethod.getExcs());
                    break;
                case Dup:
                    mv.visitInsn(DUP);
                    break;
                case Field:
                    Asm.Field field = (Asm.Field) asm;
                    mv.visitFieldInsn(field.getFtype(), field.getCname(), field.getFname(), field.getDesc());
                    break;
                case Frame:
                    Asm.Frame frame = (Asm.Frame) asm;
                    mv.visitFrame(
                        frame.getFtype(),
                        frame.getNlocal(),
                        Arrays.stream(frame.getLocal()).map(s -> s.equalsIgnoreCase("opcodes.integer") ? Opcodes.INTEGER : s).toArray(),
                        frame.getNstack(),
                        Arrays.stream(frame.getStack()).map(s -> s.equalsIgnoreCase("opcodes.integer") ? Opcodes.INTEGER : s).toArray()
                    );
                    break;
                case Goto:
                    String labelName = ((Asm.Goto) asm).getLabel();
                    mv.visitJumpInsn(Opcodes.GOTO, (Label) env.get(labelName));
                    break;
                case I2c:
                    mv.visitInsn(I2C);
                    break;
                case I2l:
                    mv.visitInsn(I2L);
                    break;
                case Iadd:
                    mv.visitInsn(IADD);
                    break;
                case Iconst:
                    Asm.Iconst iconst = (Asm.Iconst) asm;
                    int n = iconst.getN();
                    if (n >= 0 && n <= 5) {
                        final int[] opcodes = {ICONST_0, ICONST_1, ICONST_2, ICONST_3, ICONST_4, ICONST_5};
                        mv.visitInsn(opcodes[n]);
                    } else if (n == -1) {
                        mv.visitInsn(ICONST_M1);
                    } else if (n >= (-128) && n <= 127) {
                        mv.visitIntInsn(Opcodes.BIPUSH, n);
                    } else if (n >= (-32768) && n <= 32767) {
                        mv.visitIntInsn(SIPUSH, n);
                    } else {
                        mv.visitLdcInsn(new Integer(n));
                    }
                    break;
                case Ifeq:
                    Asm.Ifeq ifeq = (Asm.Ifeq) asm;
                    mv.visitJumpInsn(Opcodes.IFEQ, (Label) env.get(ifeq.getLabel()));
                    break;
                case Ificmpge:
                    Asm.Ificmpge ificmpge = (Asm.Ificmpge) asm;
                    mv.visitJumpInsn(Opcodes.IF_ICMPGE, (Label) env.get(ificmpge.getLabel()));
                    break;
                case Ificmpgt:
                    Asm.Ificmpgt ificmpgt = (Asm.Ificmpgt) asm;
                    mv.visitJumpInsn(Opcodes.IF_ICMPGT, (Label) env.get(ificmpgt.getLabel()));
                    break;
                case Ificmple:
                    Asm.Ificmple ificmple = (Asm.Ificmple) asm;
                    mv.visitJumpInsn(Opcodes.IF_ICMPLE, (Label) env.get(ificmple.getLabel()));
                    break;
                case Ificmplt:
                    Asm.Ificmplt ificmplt = (Asm.Ificmplt) asm;
                    mv.visitJumpInsn(Opcodes.IF_ICMPLT, (Label) env.get(ificmplt.getLabel()));
                    break;
                case Iload:
                    mv.visitVarInsn(ILOAD, ((Asm.Iload)asm).getN());
                    break;
                case Imul:
                    mv.visitInsn(IMUL);
                    break;
                case InvokeMethod:
                    Asm.InvokeMethod invokeMethod = (Asm.InvokeMethod) asm;
                    mv.visitMethodInsn(
                        invokeMethod.getInvocType(),
                        invokeMethod.getCname(),
                        invokeMethod.getMname(),
                        invokeMethod.getDesc(),
                        invokeMethod.isIntf());
                    break;
                case InvokeDynamic:
                    Asm.InvokeDynamic invokeDynamic = (Asm.InvokeDynamic) asm;
                    Asm.Handle handle = invokeDynamic.getHandle();
                    final Asm.BsmArg[] invokeDynamicArgs = invokeDynamic.getArgs();
                    final Object[] bsmArgs = new Object[invokeDynamicArgs.length];
                    for (int index = 0; index < bsmArgs.length; index++) {
                        Asm.BsmArg arg = invokeDynamicArgs[index];
                        switch (arg.getType()) {
                            case BsmArgGetType:
                                Asm.BsmArg.BsmArgGetType getType = (Asm.BsmArg.BsmArgGetType) arg;
                                bsmArgs[index] = Type.getType(getType.getDesc());
                                break;
                            case BsmArgHandle:
                                Asm.BsmArg.BsmArgHandle bsmHandle = (Asm.BsmArg.BsmArgHandle) arg;
                                bsmArgs[index] = getAsmHandle(bsmHandle.getHandle());
                                break;
                        }
                    }

                    mv.visitInvokeDynamicInsn(
                        invokeDynamic.getName(),
                        invokeDynamic.getDesc(),
                        getAsmHandle(handle),
                        bsmArgs
                    );
                    break;
                case Istore:
                    mv.visitVarInsn(ISTORE, ((Asm.Istore)asm).getN());
                    break;
                case Isub:
                    mv.visitInsn(ISUB);
                    break;
                case LabelStart: {
                    String label = ((Asm.LabelStart)asm).getLabel();
                    mv.visitLabel((Label)(env.get(label)));
                    break;
                }
                case Ldc:
                    Asm.Ldc ldc = (Asm.Ldc) asm;
                    switch (ldc.getConstType()) {
                        case IntegerConst:
                            mv.visitLdcInsn(((Asm.Ldc.IntegerConst)ldc).getVal());
                            break;
                        case StringConst:
                            mv.visitLdcInsn(((Asm.Ldc.StringConst)ldc).getVal());
                            break;
                    }
                    break;
                case LookupSwitch:
                    Asm.LookupSwitch lookupSwitch = (Asm.LookupSwitch) asm;
                    mv.visitLookupSwitchInsn(
                        (Label)env.get(lookupSwitch.getDlabel()),
                        lookupSwitch.getVals(),
                        Arrays.stream(lookupSwitch.getClabels())
                            .map(s -> (Label)env.get(s))
                            .toArray(Label[]::new)
                    );
                    break;
                case MaxStackAndLocal:
                    Asm.MaxStackAndLocal maxStackAndLocal = (Asm.MaxStackAndLocal) asm;
                    mv.visitMaxs(maxStackAndLocal.getNstack(), maxStackAndLocal.getNlocal());
                    break;
                case MethodCodeStart:
                    mv.visitCode();
                    break;
                case MethodCodeEnd:
                    mv.visitEnd();
                    break;
                case New:
                    Asm.New instantiate = (Asm.New) asm;
                    mv.visitTypeInsn(NEW, instantiate.getName());
                    break;
                case Pop:
                    mv.visitInsn(POP);
                    break;
                case Return:
                    mv.visitInsn(RETURN);
                    break;
                case SourceInfo:
                    Asm.SourceInfo sourceInfo = (Asm.SourceInfo) asm;
                    cw.visitSource(sourceInfo.getName(), null);
                    break;
            }
        }

        return new AssemblerResponse(true, "");
    }

    private Handle getAsmHandle(final Asm.Handle handle) {
        return new Handle(
            handle.getTag(),
            handle.getCname(),
            handle.getMname(),
            handle.getDesc(),
            handle.isIntf()
        );
    }
}
