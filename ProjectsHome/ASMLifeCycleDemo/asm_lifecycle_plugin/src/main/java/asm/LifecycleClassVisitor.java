package asm;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * Created by Li Zongwei on 2020/12/29.
 **/
public class LifecycleClassVisitor extends ClassVisitor {

    private String className;
    private String superName;

    public LifecycleClassVisitor(int api) {
        super(api);
    }

    public LifecycleClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
        this.superName = superName;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("ClassVisitor visitMethod name---" + name + ",superName is " + superName);
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);

//        if (superName.equals("android/support/v7/app/AppCompatActivity")) {
//            if (name.startsWith("onCreate")) {
//                //处理onCreate()方法
//                return new LifecycleMethodVisitor(mv, className, name);
//            }
//        }
        if(superName.equals("androidx/appcompat/app/AppCompatActivity")){
            if(name.startsWith("onCreate")){
                return new LifecycleMethodVisitor(mv,className,name);
            }
        }
        return mv;
    }
}
