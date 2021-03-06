package com.mmhelloworld.jvmassembler.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.ToString;

import java.util.List;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
    visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Asm.Aaload.class, name = "Aaload"),
    @JsonSubTypes.Type(value = Asm.Aastore.class, name = "Aastore"),
    @JsonSubTypes.Type(value = Asm.Aconstnull.class, name = "Aconstnull"),
    @JsonSubTypes.Type(value = Asm.Aload.class, name = "Aload"),
    @JsonSubTypes.Type(value = Asm.Anewarray.class, name = "Anewarray"),
    @JsonSubTypes.Type(value = Asm.Annotation.class, name = "Annotation"),
    @JsonSubTypes.Type(value = Asm.Astore.class, name = "Astore"),
    @JsonSubTypes.Type(value = Asm.Areturn.class, name = "Areturn"),
    @JsonSubTypes.Type(value = Asm.Checkcast.class, name = "Checkcast"),
    @JsonSubTypes.Type(value = Asm.ClassCodeStart.class, name = "ClassCodeStart"),
    @JsonSubTypes.Type(value = Asm.ClassCodeEnd.class, name = "ClassCodeEnd"),
    @JsonSubTypes.Type(value = Asm.CreateClass.class, name = "CreateClass"),
    @JsonSubTypes.Type(value = Asm.CreateField.class, name = "CreateField"),
    @JsonSubTypes.Type(value = Asm.CreateLabel.class, name = "CreateLabel"),
    @JsonSubTypes.Type(value = Asm.CreateMethod.class, name = "CreateMethod"),
    @JsonSubTypes.Type(value = Asm.Dadd.class, name = "Dadd"),
    @JsonSubTypes.Type(value = Asm.Ddiv.class, name = "Ddiv"),
    @JsonSubTypes.Type(value = Asm.Dload.class, name = "Dload"),
    @JsonSubTypes.Type(value = Asm.Dmul.class, name = "Dmul"),
    @JsonSubTypes.Type(value = Asm.Drem.class, name = "Drem"),
    @JsonSubTypes.Type(value = Asm.Dreturn.class, name = "Dreturn"),
    @JsonSubTypes.Type(value = Asm.Dsub.class, name = "Dsub"),
    @JsonSubTypes.Type(value = Asm.Dup.class, name = "Dup"),
    @JsonSubTypes.Type(value = Asm.F2d.class, name = "F2d"),
    @JsonSubTypes.Type(value = Asm.Field.class, name = "Field"),
    @JsonSubTypes.Type(value = Asm.FieldEnd.class, name = "FieldEnd"),
    @JsonSubTypes.Type(value = Asm.Fload.class, name = "Fload"),
    @JsonSubTypes.Type(value = Asm.Frame.class, name = "Frame"),
    @JsonSubTypes.Type(value = Asm.Freturn.class, name = "Freturn"),
    @JsonSubTypes.Type(value = Asm.Goto.class, name = "Goto"),
    @JsonSubTypes.Type(value = Asm.I2c.class, name = "I2c"),
    @JsonSubTypes.Type(value = Asm.I2l.class, name = "I2l"),
    @JsonSubTypes.Type(value = Asm.Iadd.class, name = "Iadd"),
    @JsonSubTypes.Type(value = Asm.Iand.class, name = "Iand"),
    @JsonSubTypes.Type(value = Asm.Iconst.class, name = "Iconst"),
    @JsonSubTypes.Type(value = Asm.Idiv.class, name = "Idiv"),
    @JsonSubTypes.Type(value = Asm.Ifeq.class, name = "Ifeq"),
    @JsonSubTypes.Type(value = Asm.Ificmpge.class, name = "Ificmpge"),
    @JsonSubTypes.Type(value = Asm.Ificmpgt.class, name = "Ificmpgt"),
    @JsonSubTypes.Type(value = Asm.Ificmple.class, name = "Ificmple"),
    @JsonSubTypes.Type(value = Asm.Ificmplt.class, name = "Ificmplt"),
    @JsonSubTypes.Type(value = Asm.Iload.class, name = "Iload"),
    @JsonSubTypes.Type(value = Asm.Imul.class, name = "Imul"),
    @JsonSubTypes.Type(value = Asm.InvokeMethod.class, name = "InvokeMethod"),
    @JsonSubTypes.Type(value = Asm.InvokeDynamic.class, name = "InvokeDynamic"),
    @JsonSubTypes.Type(value = Asm.Irem.class, name = "Irem"),
    @JsonSubTypes.Type(value = Asm.Ireturn.class, name = "Ireturn"),
    @JsonSubTypes.Type(value = Asm.Ishl.class, name = "Ishl"),
    @JsonSubTypes.Type(value = Asm.Ishr.class, name = "Ishr"),
    @JsonSubTypes.Type(value = Asm.Istore.class, name = "Istore"),
    @JsonSubTypes.Type(value = Asm.Isub.class, name = "Isub"),
    @JsonSubTypes.Type(value = Asm.Iushr.class, name = "Iushr"),
    @JsonSubTypes.Type(value = Asm.L2i.class, name = "L2i"),
    @JsonSubTypes.Type(value = Asm.LabelStart.class, name = "LabelStart"),
    @JsonSubTypes.Type(value = Asm.Ladd.class, name = "Ladd"),
    @JsonSubTypes.Type(value = Asm.Land.class, name = "Land"),
    @JsonSubTypes.Type(value = Asm.LdcDouble.class, name = "LdcDouble"),
    @JsonSubTypes.Type(value = Asm.LdcInteger.class, name = "LdcInteger"),
    @JsonSubTypes.Type(value = Asm.LdcString.class, name = "LdcString"),
    @JsonSubTypes.Type(value = Asm.LdcLong.class, name = "LdcLong"),
    @JsonSubTypes.Type(value = Asm.LdcType.class, name = "LdcType"),
    @JsonSubTypes.Type(value = Asm.Ldiv.class, name = "Ldiv"),
    @JsonSubTypes.Type(value = Asm.Lload.class, name = "Lload"),
    @JsonSubTypes.Type(value = Asm.Lmul.class, name = "Lmul"),
    @JsonSubTypes.Type(value = Asm.Lreturn.class, name = "Lreturn"),
    @JsonSubTypes.Type(value = Asm.Lshl.class, name = "Lshl"),
    @JsonSubTypes.Type(value = Asm.Lshr.class, name = "Lshr"),
    @JsonSubTypes.Type(value = Asm.LookupSwitch.class, name = "LookupSwitch"),
    @JsonSubTypes.Type(value = Asm.Lrem.class, name = "Lrem"),
    @JsonSubTypes.Type(value = Asm.Lsub.class, name = "Lsub"),
    @JsonSubTypes.Type(value = Asm.Lushr.class, name = "Lushr"),
    @JsonSubTypes.Type(value = Asm.MaxStackAndLocal.class, name = "MaxStackAndLocal"),
    @JsonSubTypes.Type(value = Asm.MethodCodeStart.class, name = "MethodCodeStart"),
    @JsonSubTypes.Type(value = Asm.MethodCodeEnd.class, name = "MethodCodeEnd"),
    @JsonSubTypes.Type(value = Asm.New.class, name = "New"),
    @JsonSubTypes.Type(value = Asm.Pop.class, name = "Pop"),
    @JsonSubTypes.Type(value = Asm.Return.class, name = "Return"),
    @JsonSubTypes.Type(value = Asm.SourceInfo.class, name = "SourceInfo")

})
public abstract class Asm {
    private AsmType type;

    public AsmType getType() {
        return type;
    }

    public void setType(final AsmType type) {
        this.type = type;
    }

    public enum AsmType {
        Aaload,
        Aastore,
        Aconstnull,
        Aload,
        Anewarray,
        Annotation,
        Astore,
        Areturn,
        Checkcast,
        ClassCodeStart,
        ClassCodeEnd,
        CreateClass,
        CreateField,
        CreateLabel,
        CreateMethod,
        Dadd,
        Ddiv,
        Dload,
        Dmul,
        Drem,
        Dreturn,
        Dsub,
        Dup,
        F2d,
        Field,
        FieldEnd,
        Fload,
        Frame,
        Freturn,
        Goto,
        I2c,
        I2l,
        Iadd,
        Iand,
        Iconst,
        Idiv,
        Ifeq,
        Ificmpge,
        Ificmpgt,
        Ificmple,
        Ificmplt,
        Iload,
        Imul,
        InvokeMethod,
        InvokeDynamic,
        Irem,
        Ireturn,
        Ishl,
        Ishr,
        Istore,
        Isub,
        Iushr,
        L2i,
        LabelStart,
        Ladd,
        Land,
        LdcDouble,
        LdcInteger,
        LdcLong,
        LdcString,
        LdcType,
        Ldiv,
        Lload,
        Lmul,
        Lreturn,
        Lshl,
        Lshr,
        LookupSwitch,
        Lrem,
        Lsub,
        Lushr,
        MaxStackAndLocal,
        MethodCodeStart,
        MethodCodeEnd,
        New,
        Pop,
        Return,
        SourceInfo
    }

    public static final class CreateClass extends Asm {
        private final int flags;

        @JsonCreator
        public CreateClass(@JsonProperty("flags") int flags) {
            this.flags = flags;
        }

        public int getFlags() {
            return flags;
        }
    }

    public static final class Aaload extends Asm {

    }

    public static final class Aastore extends Asm {
    }

    public static final class CreateLabel extends Asm {
        private final String label;

        public CreateLabel(@JsonProperty("name") String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public static final class Goto extends Asm {
        private final String label;

        public Goto(@JsonProperty("name") String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public static class Aconstnull extends Asm {
    }

    public static class Aload extends Asm {
        private final int index;

        public Aload(@JsonProperty("index") final int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    public static class Anewarray extends Asm {
        private final String desc;

        public Anewarray(@JsonProperty("desc") final String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    public static class Astore extends Asm {
        private final int index;

        public Astore(@JsonProperty("index") final int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    public static class Areturn extends Asm {
    }

    public static class Ireturn extends Asm {
    }

    public static class Dreturn extends Asm {
    }

    public static class Freturn extends Asm {
    }

    public static class F2d extends Asm {
    }

    public static class Lreturn extends Asm {
    }

    public static class Checkcast extends Asm {
        private final String desc;

        public Checkcast(@JsonProperty("desc") final String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    public static class ClassCodeStart extends Asm {
        private final int version;
        private final int acc;
        private final String name;
        private final String sig;
        private final String parent;
        private final String[] interfaces;
        private final List<Annotation> annotations;

        public ClassCodeStart(@JsonProperty("version") final int version,
                              @JsonProperty("acc") final int acc,
                              @JsonProperty("name") final String name,
                              @JsonProperty("sig") final String sig,
                              @JsonProperty("parent") final String parent,
                              @JsonProperty("interfaces") final String[] interfaces,
                              @JsonProperty("annotations") final List<Annotation> annotations) {
            this.version = version;
            this.acc = acc;
            this.name = name;
            this.sig = sig;
            this.parent = parent;
            this.interfaces = interfaces;
            this.annotations = annotations;
        }

        public int getVersion() {
            return version;
        }

        public int getAcc() {
            return acc;
        }

        public String getName() {
            return name;
        }

        public String getSig() {
            return sig;
        }

        public String getParent() {
            return parent;
        }

        public String[] getInterfaces() {
            return interfaces;
        }

        public List<Annotation> getAnnotations() {
            return annotations;
        }
    }

    public static class ClassCodeEnd extends Asm {
        private final String out;

        public ClassCodeEnd(@JsonProperty("out") final String out) {
            this.out = out;
        }

        public String getOut() {
            return out;
        }
    }

    public static class CreateMethod extends Asm {
        private final int acc;
        private final String cname;
        private final String fname;
        private final String desc;
        private final String sig;
        private final String[] excs;
        private final List<Annotation> anns;
        private final List<List<Annotation>> paramAnns;

        public CreateMethod(@JsonProperty("acc") final int acc,
                            @JsonProperty("cname") final String cname,
                            @JsonProperty("fname") final String fname,
                            @JsonProperty("desc") final String desc,
                            @JsonProperty("sig") final String sig,
                            @JsonProperty("excs") final String[] excs,
                            @JsonProperty("anns") final List<Annotation> anns,
                            @JsonProperty("paramAnns") final List<List<Annotation>> paramAnns) {
            this.acc = acc;
            this.cname = cname;
            this.fname = fname;
            this.desc = desc;
            this.sig = sig;
            this.excs = excs;
            this.anns = anns;
            this.paramAnns = paramAnns;
        }

        public int getAcc() {
            return acc;
        }

        public String getFname() {
            return fname;
        }

        public String getCname() {
            return cname;
        }

        public String getDesc() {
            return desc;
        }

        public String getSig() {
            return sig;
        }

        public String[] getExcs() {
            return excs;
        }

        public List<Annotation> getAnns() {
            return anns;
        }

        public List<List<Annotation>> getParamAnns() {
            return paramAnns;
        }
    }

    public static class CreateField extends Asm {
        private final int acc;
        private final String cname;
        private final String name;
        private final String desc;
        private final String sig;
        private final Object value;

        public CreateField(@JsonProperty("acc") final int acc,
                           @JsonProperty("cname") final String cname,
                           @JsonProperty("name") final String name,
                           @JsonProperty("desc") final String desc,
                           @JsonProperty("sig") final String sig,
                           @JsonProperty("initVal") final Object value) {
            this.acc = acc;
            this.cname = cname;
            this.name = name;
            this.desc = desc;
            this.sig = sig;
            this.value = value;
        }

        public int getAcc() {
            return acc;
        }

        public String getName() {
            return name;
        }

        public String getDesc() {
            return desc;
        }

        public String getSig() {
            return sig;
        }

        public Object getValue() {
            return value;
        }

        public String getCname() {
            return cname;
        }
    }

    public static class Dup extends Asm {
    }

    public static class Field extends Asm {
        private final int ftype;
        private final String cname;
        private final String fname;
        private final String desc;

        public Field(@JsonProperty("ftype") final int ftype,
                     @JsonProperty("cname") final String cname,
                     @JsonProperty("fname") final String fname,
                     @JsonProperty("desc") final String desc) {
            this.ftype = ftype;
            this.cname = cname;
            this.fname = fname;
            this.desc = desc;
        }

        public int getFtype() {
            return ftype;
        }

        public String getCname() {
            return cname;
        }

        public String getFname() {
            return fname;
        }

        public String getDesc() {
            return desc;
        }
    }

    public static class FieldEnd extends Asm {

    }

    public static class Frame extends Asm {
        private final int ftype;
        private final int nlocal;
        private final String[] local;
        private final int nstack;
        private final String[] stack;

        public Frame(@JsonProperty("ftype") final int ftype,
                     @JsonProperty("nlocal") final int nlocal,
                     @JsonProperty("local") final String[] local,
                     @JsonProperty("nstack") final int nstack,
                     @JsonProperty("stack") final String[] stack) {
            this.ftype = ftype;
            this.nlocal = nlocal;
            this.local = local;
            this.nstack = nstack;
            this.stack = stack;
        }

        public int getFtype() {
            return ftype;
        }

        public int getNlocal() {
            return nlocal;
        }

        public String[] getLocal() {
            return local;
        }

        public int getNstack() {
            return nstack;
        }

        public String[] getStack() {
            return stack;
        }
    }

    public static class I2c extends Asm {
    }

    public static class I2l extends Asm {
    }

    public static class Iadd extends Asm {
    }

    public static class Iand extends Asm {
    }

    public static class Iconst extends Asm {
        private final int n;

        public Iconst(@JsonProperty("n") final int n) {
            this.n = n;
        }

        public int getN() {
            return n;
        }
    }

    public static class Idiv extends Asm {
    }

    public static class Ifeq extends Asm {
        private final String label;

        public Ifeq(@JsonProperty("label") String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

    }

    public static class Ificmpge extends Asm {
        private final String label;

        public Ificmpge(@JsonProperty("label") String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public static class Ificmpgt extends Asm {
        private final String label;

        public Ificmpgt(@JsonProperty("label") String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public static class Ificmple extends Asm {
        private final String label;

        public Ificmple(@JsonProperty("label") String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public static class Ificmplt extends Asm {
        private final String label;

        public Ificmplt(@JsonProperty("label") String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public static class Iload extends Asm {
        private final int n;

        public Iload(@JsonProperty("n") final int n) {
            this.n = n;
        }

        public int getN() {
            return n;
        }
    }

    public static class Imul extends Asm {
    }

    public static class InvokeMethod extends Asm {
        private final int invocType;
        private final String cname;
        private final String mname;
        private final String desc;
        private final boolean isIntf;

        public InvokeMethod(@JsonProperty("invType") final int invocType,
                            @JsonProperty("cname") final String cname,
                            @JsonProperty("mname") final String mname,
                            @JsonProperty("desc") final String desc,
                            @JsonProperty("isIntf") final boolean isIntf) {
            this.invocType = invocType;
            this.cname = cname;
            this.mname = mname;
            this.desc = desc;
            this.isIntf = isIntf;
        }

        public int getInvocType() {
            return invocType;
        }

        public String getCname() {
            return cname;
        }

        public String getMname() {
            return mname;
        }

        public String getDesc() {
            return desc;
        }

        public boolean isIntf() {
            return isIntf;
        }
    }

    public static class InvokeDynamic extends Asm {
        private final String name;
        private final String desc;
        private final Handle handle;
        private final BsmArg[] args;

        public InvokeDynamic(@JsonProperty("name") final String name,
                             @JsonProperty("desc") final String desc,
                             @JsonProperty("handle") final Handle handle,
                             @JsonProperty("args") final BsmArg[] args) {
            this.name = name;
            this.desc = desc;
            this.handle = handle;
            this.args = args;
        }

        public String getName() {
            return name;
        }

        public String getDesc() {
            return desc;
        }

        public Handle getHandle() {
            return handle;
        }

        public BsmArg[] getArgs() {
            return args;
        }
    }

    public static class Istore extends Asm {
        private final int n;

        public Istore(@JsonProperty("n") final int n) {
            this.n = n;
        }

        public int getN() {
            return n;
        }
    }

    public static class Isub extends Asm {
    }

    public static class LabelStart extends Asm {
        private final String label;

        public LabelStart(@JsonProperty("label") String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public static final class LdcDouble extends Asm {
        private final double val;

        public LdcDouble(@JsonProperty("val") final double val) {
            this.val = val;
        }

        public double getVal() {
            return val;
        }
    }

    public static final class LdcInteger extends Asm {
        private final int val;

        public LdcInteger(@JsonProperty("val") final int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    public static final class LdcString extends Asm {
        private final String val;

        public LdcString(@JsonProperty("val") final String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }

    public static final class LdcLong extends Asm {
        private final long val;

        public LdcLong(@JsonProperty("val") final long val) {
            this.val = val;
        }

        public long getVal() {
            return val;
        }
    }

    public static final class LdcType extends Asm {
        private final String val;

        public LdcType(@JsonProperty("val") final String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }

    public static class LookupSwitch extends Asm {
        private final String dlabel;
        private final String[] clabels;
        private final int[] vals;

        public LookupSwitch(@JsonProperty("dlabel") final String dlabel,
                            @JsonProperty("clabels") final String[] clabels,
                            @JsonProperty("vals") final int[] vals) {
            this.dlabel = dlabel;
            this.clabels = clabels;
            this.vals = vals;
        }

        public String getDlabel() {
            return dlabel;
        }

        public String[] getClabels() {
            return clabels;
        }

        public int[] getVals() {
            return vals;
        }
    }

    public static class MaxStackAndLocal extends Asm {
        private final int nstack;
        private final int nlocal;

        public MaxStackAndLocal(@JsonProperty("nstack") final int nstack,
                                @JsonProperty("nlocal") final int nlocal) {
            this.nstack = nstack;
            this.nlocal = nlocal;
        }

        public int getNstack() {
            return nstack;
        }

        public int getNlocal() {
            return nlocal;
        }
    }

    public static class MethodCodeStart extends Asm {
    }

    public static class MethodCodeEnd extends Asm {
    }

    public static class New extends Asm {
        private final String name;

        public New(@JsonProperty("name") final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class Pop extends Asm {
    }

    public static class Return extends Asm {
    }

    public static class SourceInfo extends Asm {
        private final String name;

        public SourceInfo(@JsonProperty("name") final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static final class Handle {
        private final int tag;
        private final String cname;
        private final String mname;
        private final String desc;
        private final boolean isIntf;

        public Handle(@JsonProperty("tag") final int tag,
                      @JsonProperty("cname") final String cname,
                      @JsonProperty("mname") final String mname,
                      @JsonProperty("desc") final String desc,
                      @JsonProperty("isIntf") final boolean isIntf) {
            this.tag = tag;
            this.cname = cname;
            this.mname = mname;
            this.desc = desc;
            this.isIntf = isIntf;
        }

        public int getTag() {
            return tag;
        }

        public String getCname() {
            return cname;
        }

        public String getMname() {
            return mname;
        }

        public String getDesc() {
            return desc;
        }

        public boolean isIntf() {
            return isIntf;
        }
    }

    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true)
    @JsonSubTypes({
        @JsonSubTypes.Type(value = Asm.BsmArg.BsmArgGetType.class, name = "BsmArgGetType"),
        @JsonSubTypes.Type(value = Asm.BsmArg.BsmArgHandle.class, name = "BsmArgHandle")
    })
    public static abstract class BsmArg {
        private BsmArgType type;

        private BsmArg() {
        }

        public BsmArgType getType() {
            return type;
        }

        public void setType(final BsmArgType type) {
            this.type = type;
        }

        public enum BsmArgType {
            BsmArgGetType,
            BsmArgHandle
        }

        public static final class BsmArgGetType extends BsmArg {
            private final String desc;

            public BsmArgGetType(@JsonProperty("desc") final String desc) {
                this.desc = desc;
            }

            public String getDesc() {
                return desc;
            }
        }

        public static final class BsmArgHandle extends BsmArg {
            private final Handle handle;

            public BsmArgHandle(@JsonProperty("handle") final Handle handle) {
                this.handle = handle;
            }

            public Handle getHandle() {
                return handle;
            }
        }

    }

    public static class Ladd extends Asm {
    }

    public static class Land extends Asm {
    }

    public static class Ldiv extends Asm {
    }

    public static class Lmul extends Asm {
    }

    public static class Lsub extends Asm {
    }

    public static class Ishl extends Asm {
    }

    public static class Ishr extends Asm {
    }

    public static class Iushr extends Asm {
    }

    public static class Lshl extends Asm {
    }

    public static class Lshr extends Asm {
    }

    public static class Lushr extends Asm {
    }

    public static class L2i extends Asm {
    }

    public static class Irem extends Asm {
    }

    public static class Lrem extends Asm {
    }

    public static class Dadd extends Asm {
    }

    public static class Ddiv extends Asm {
    }

    public static class Dmul extends Asm {
    }

    public static class Drem extends Asm {
    }

    public static class Dsub extends Asm {
    }

    public static class Lload extends Asm {
        private final int n;

        public Lload(@JsonProperty("n") final int n) {
            this.n = n;
        }

        public int getN() {
            return n;
        }
    }

    public static class Fload extends Asm {
        private final int n;

        public Fload(@JsonProperty("n") final int n) {
            this.n = n;
        }

        public int getN() {
            return n;
        }
    }

    public static class Dload extends Asm {
        private final int n;

        public Dload(@JsonProperty("n") final int n) {
            this.n = n;
        }

        public int getN() {
            return n;
        }
    }

    @ToString
    public static class Annotation extends Asm {
        private final String name;
        private final List<AnnotationProperty> properties;

        public Annotation(@JsonProperty("name") final String name,
                          @JsonProperty("props") final List<AnnotationProperty> properties) {
            this.name = name;
            this.properties = properties;
        }

        public String getName() {
            return name;
        }

        public List<AnnotationProperty> getProperties() {
            return properties;
        }
    }

    @ToString
    public static class AnnotationProperty {
        private final String name;
        private final AnnotationValue value;

        public AnnotationProperty(@JsonProperty("name") final String name,
                                  @JsonProperty("value") final AnnotationValue value) {
            this.name = name;
            this.value = value;
        }

        public AnnotationValue getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true)
    @JsonSubTypes({
        @JsonSubTypes.Type(value = AnnotationValue.AnnString.class, name = "AnnString"),
        @JsonSubTypes.Type(value = AnnotationValue.AnnInt.class, name = "AnnInt"),
        @JsonSubTypes.Type(value = AnnotationValue.AnnArray.class, name = "AnnArray")
    })
    public static abstract class AnnotationValue {
        private AnnotationValueType type;

        private AnnotationValue() {}

        public AnnotationValueType getType() {
            return type;
        }

        public void setType(final AnnotationValueType type) {
            this.type = type;
        }

        public enum AnnotationValueType {
            AnnString,
            AnnInt,
            AnnArray
        }

        @ToString
        public static class AnnString extends AnnotationValue {
            private final String value;

            public AnnString(@JsonProperty("value") final String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }
        }

        @ToString
        public static class AnnInt extends AnnotationValue {
            private final int value;

            public AnnInt(@JsonProperty("value") final int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }
        }

        @ToString
        public static class AnnArray extends AnnotationValue {
            private final List<AnnotationValue> values;

            public AnnArray(@JsonProperty("values") final List<AnnotationValue> values) {
                this.values = values;
            }

            public List<AnnotationValue> getValues() {
                return values;
            }
        }
    }
}
