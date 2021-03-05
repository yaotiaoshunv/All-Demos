package asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by Li Zongwei on 2020/12/29.
 **/
public class LifecycleMethodVisitor extends MethodVisitor {
    private String className;
    private String methodName;

    public LifecycleMethodVisitor(int api) {
        super(api);
    }

    public LifecycleMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }


    public LifecycleMethodVisitor(MethodVisitor mv, String className, String methodName) {
        super(Opcodes.ASM5, mv);
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println("MethodVisitor visitCode---");

        mv.visitLdcInsn("TAG");
        mv.visitLdcInsn(className + "--->" + methodName);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC,"android/util/Log","i","(Ljava/lang/String;Ljava/lang/String;)I",false);
        mv.visitInsn(Opcodes.POP);

    }
}
