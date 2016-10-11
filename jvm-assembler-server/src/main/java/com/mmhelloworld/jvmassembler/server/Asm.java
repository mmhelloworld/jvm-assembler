package com.mmhelloworld.jvmassembler.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
    @JsonSubTypes.Type(value = Asm.Astore.class, name = "Astore"),
    @JsonSubTypes.Type(value = Asm.Areturn.class, name = "Areturn"),
    @JsonSubTypes.Type(value = Asm.Checkcast.class, name = "Checkcast"),
    @JsonSubTypes.Type(value = Asm.ClassCodeStart.class, name = "ClassCodeStart"),
    @JsonSubTypes.Type(value = Asm.ClassCodeEnd.class, name = "ClassCodeEnd"),
    @JsonSubTypes.Type(value = Asm.CreateClass.class, name = "CreateClass"),
    @JsonSubTypes.Type(value = Asm.CreateLabel.class, name = "CreateLabel"),
    @JsonSubTypes.Type(value = Asm.CreateMethod.class, name = "CreateMethod"),
    @JsonSubTypes.Type(value = Asm.Dup.class, name = "Dup"),
    @JsonSubTypes.Type(value = Asm.Field.class, name = "Field"),
    @JsonSubTypes.Type(value = Asm.Frame.class, name = "Frame"),
    @JsonSubTypes.Type(value = Asm.Goto.class, name = "Goto"),
    @JsonSubTypes.Type(value = Asm.I2c.class, name = "I2c"),
    @JsonSubTypes.Type(value = Asm.I2l.class, name = "I2l"),
    @JsonSubTypes.Type(value = Asm.Iadd.class, name = "Iadd"),
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
    @JsonSubTypes.Type(value = Asm.Istore.class, name = "Istore"),
    @JsonSubTypes.Type(value = Asm.Isub.class, name = "Isub"),
    @JsonSubTypes.Type(value = Asm.LabelStart.class, name = "LabelStart"),
    @JsonSubTypes.Type(value = Asm.Ldc.DoubleConst.class, name = "Ldc"),
    @JsonSubTypes.Type(value = Asm.Ldc.IntegerConst.class, name = "Ldc"),
    @JsonSubTypes.Type(value = Asm.Ldc.StringConst.class, name = "Ldc"),
    @JsonSubTypes.Type(value = Asm.LookupSwitch.class, name = "LookupSwitch"),
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
        Astore,
        Areturn,
        Checkcast,
        ClassCodeStart,
        ClassCodeEnd,
        CreateClass,
        CreateLabel,
        CreateMethod,
        Dup,
        Field,
        Frame,
        Goto,
        I2c,
        I2l,
        Iadd,
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
        Istore,
        Isub,
        LabelStart,
        Ldc,
        LookupSwitch,
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

        public ClassCodeStart(@JsonProperty("version") final int version,
                              @JsonProperty("acc") final int acc,
                              @JsonProperty("name") final String name,
                              @JsonProperty("sig") final String sig,
                              @JsonProperty("parent") final String parent,
                              @JsonProperty("interfaces") final String[] interfaces) {
            this.version = version;
            this.acc = acc;
            this.name = name;
            this.sig = sig;
            this.parent = parent;
            this.interfaces = interfaces;
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
        private final String name;
        private final String desc;
        private final String sig;
        private final String[] excs;

        public CreateMethod(@JsonProperty("acc") final int acc,
                            @JsonProperty("name") final String name,
                            @JsonProperty("desc") final String desc,
                            @JsonProperty("sig") final String sig,
                            @JsonProperty("excs") final String[] excs) {
            this.acc = acc;
            this.name = name;
            this.desc = desc;
            this.sig = sig;
            this.excs = excs;
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

        public String[] getExcs() {
            return excs;
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

    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "constType",
        visible = true)
    @JsonSubTypes({
        @JsonSubTypes.Type(value = Asm.Ldc.IntegerConst.class, name = "IntegerConst"),
        @JsonSubTypes.Type(value = Asm.Ldc.StringConst.class, name = "StringConst")
    })
    public static abstract class Ldc extends Asm {
        private LdcType constType;

        private Ldc() {}

        public LdcType getConstType() {
            return constType;
        }

        public void setConstType(final LdcType constType) {
            this.constType = constType;
        }


        public enum LdcType { DoubleConst, IntegerConst, StringConst }

        public static final class DoubleConst extends Ldc {
            private final double val;

            public DoubleConst(@JsonProperty("val") final double val) {
                this.val = val;
            }

            public double getVal() {
                return val;
            }
        }

        public static final class IntegerConst extends Ldc {
            private final int val;

            public IntegerConst(@JsonProperty("val") final int val) {
                this.val = val;
            }

            public int getVal() {
                return val;
            }
        }

        public static final class StringConst extends Ldc {
            private final String val;

            public StringConst(@JsonProperty("val") final String val) {
                this.val = val;
            }

            public String getVal() {
                return val;
            }
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

        private BsmArg() {}

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
}
