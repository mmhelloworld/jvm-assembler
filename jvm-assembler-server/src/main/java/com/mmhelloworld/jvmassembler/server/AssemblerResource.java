package com.mmhelloworld.jvmassembler.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static org.objectweb.asm.Opcodes.*;

@Path("/assembler")
public class AssemblerResource {
    @GET
    @Path("/health")
    public Response checkHealth() {
        return Response.ok().build();
    }

    @POST
    @Path("/assemble")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AssemblerResponse assemble(CreateBytecode request) throws JsonProcessingException {
        Map<String, ClassWriter> cws = new HashMap<>();
        ClassWriter cw = null;
        MethodVisitor mv = null;
        Map<String, Object> env = new HashMap<>();
        String error = "";
        boolean isSuccess = true;

        try {
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
                        cws.put(classCodeStart.getName(), cw);
                        break;
                    case ClassCodeEnd:
                        cw.visitEnd();
                        Asm.ClassCodeEnd classCodeEnd = (Asm.ClassCodeEnd) asm;
                        cws.entrySet().parallelStream().forEach(e -> {
                            String className = e.getKey();
                            ClassWriter classWriter = e.getValue();
                            File outFile = new File(classCodeEnd.getOut(), className + ".class");
                            new File(outFile.getParent()).mkdirs();
                            try (OutputStream out = new FileOutputStream(outFile)) {
                                out.write(classWriter.toByteArray());
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        });

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
                        final String className = createMethod.getCname();
                        cw = cws.computeIfAbsent(className, cname -> {
                            final ClassWriter classWriter = new ClassWriter(COMPUTE_MAXS);
                            classWriter.visit(52, ACC_PUBLIC, className, null, "java/lang/Object", null);
                            createDefaultConstructor(classWriter);
                            return classWriter;
                        });
                        mv = cw.visitMethod(
                            createMethod.getAcc(),
                            createMethod.getFname(),
                            createMethod.getDesc(),
                            createMethod.getSig(),
                            createMethod.getExcs());
                        break;
                    case Dadd:
                        mv.visitInsn(DADD);
                        break;
                    case Ddiv:
                        mv.visitInsn(DDIV);
                        break;
                    case Dmul:
                        mv.visitInsn(DMUL);
                        break;
                    case Drem:
                        mv.visitInsn(DREM);
                        break;
                    case Dsub:
                        mv.visitInsn(DSUB);
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
                    case Iand:
                        mv.visitInsn(IAND);
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
                    case Idiv:
                        mv.visitInsn(IDIV);
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
                        mv.visitVarInsn(ILOAD, ((Asm.Iload) asm).getN());
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
                    case Irem:
                        mv.visitInsn(IREM);
                        break;
                    case Ireturn:
                        mv.visitInsn(IRETURN);
                        break;
                    case Ishl:
                        mv.visitInsn(ISHL);
                        break;
                    case Ishr:
                        mv.visitInsn(ISHR);
                        break;
                    case Istore:
                        mv.visitVarInsn(ISTORE, ((Asm.Istore) asm).getN());
                        break;
                    case Isub:
                        mv.visitInsn(ISUB);
                        break;
                    case Iushr:
                        mv.visitInsn(IUSHR);
                        break;
                    case L2i:
                        mv.visitInsn(L2I);
                        break;
                    case LabelStart: {
                        String label = ((Asm.LabelStart) asm).getLabel();
                        mv.visitLabel((Label) (env.get(label)));
                        break;
                    }
                    case Ladd:
                        mv.visitInsn(LADD);
                        break;
                    case Land:
                        mv.visitInsn(LAND);
                        break;
                    case LdcDouble:
                        mv.visitLdcInsn(((Asm.LdcDouble) asm).getVal());
                        break;
                    case LdcInteger:
                        mv.visitLdcInsn(((Asm.LdcInteger) asm).getVal());
                        break;
                    case LdcLong:
                        mv.visitLdcInsn(((Asm.LdcLong) asm).getVal());
                        break;
                    case LdcString:
                        mv.visitLdcInsn(((Asm.LdcString) asm).getVal());
                        break;
                    case Ldiv:
                        mv.visitInsn(LDIV);
                        break;
                    case Lload:
                        mv.visitVarInsn(LLOAD, ((Asm.Lload) asm).getN());
                        break;
                    case Lmul:
                        mv.visitInsn(LMUL);
                        break;
                    case LookupSwitch:
                        Asm.LookupSwitch lookupSwitch = (Asm.LookupSwitch) asm;
                        mv.visitLookupSwitchInsn(
                            (Label) env.get(lookupSwitch.getDlabel()),
                            lookupSwitch.getVals(),
                            Arrays.stream(lookupSwitch.getClabels())
                                .map(s -> (Label) env.get(s))
                                .toArray(Label[]::new)
                        );
                        break;
                    case Lshl:
                        mv.visitInsn(LSHL);
                        break;
                    case Lrem:
                        mv.visitInsn(LREM);
                        break;
                    case Lreturn:
                        mv.visitInsn(LRETURN);
                        break;
                    case Lshr:
                        mv.visitInsn(LSHR);
                        break;
                    case Lsub:
                        mv.visitInsn(LSUB);
                        break;
                    case Lushr:
                        mv.visitInsn(LUSHR);
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
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
            error = e.getMessage();
        }
        return new AssemblerResponse(isSuccess, error);
    }

    private MethodVisitor createDefaultConstructor(final ClassWriter cw) {
        final MethodVisitor mv;
        mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        return mv;
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
