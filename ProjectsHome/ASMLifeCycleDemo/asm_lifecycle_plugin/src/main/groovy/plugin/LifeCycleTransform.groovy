package plugin

import asm.LifecycleClassVisitor
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import groovy.io.FileType
import jdk.internal.org.objectweb.asm.Opcodes
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

public class LifeCycleTransform extends Transform {

    @Override
    String getName() {
        return "LifeCycleTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    public void transform(TransformInvocation transformInvocation)
            throws TransformException, InterruptedException, IOException {
        System.out.println("transform begin")

        //拿到所有的class文件
        Collection<TransformInput> transformInputs = transformInvocation.inputs
        TransformOutputProvider outputProvider = transformInvocation.outputProvider

        transformInputs.each { TransformInput transformInput ->
            // directoryInputs代表着以源码方式参与项目编译的所有目录结构及其目录下的源码文件
            // 比如我没手写的类以及R.class、BuildConfig.class以及MainActivity.class等。
            transformInput.directoryInputs.each { DirectoryInput directoryInput ->
                File dir = directoryInput.file
                if (dir) {
                    dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->
                        System.out.println("find class:" + file.name)
                        ClassReader classReader = new ClassReader(file.bytes)
                        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                        ClassVisitor classVisitor = new LifecycleClassVisitor(Opcodes.ASM5, classWriter)
                        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                        byte[] bytes = classWriter.toByteArray()
                        FileOutputStream outputStream = new FileOutputStream(file.path)
                        outputStream.write(bytes)
                        outputStream.close()
                  }
                    //处理完输入文件后把输出文件传给下一个文件
                    def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes,
                            directoryInput.scopes, Format.DIRECTORY)
                    FileUtils.copyDirectory(directoryInput.file, dest)
                }
            }

//            transformInput.jarInputs.forEach {
//                it.file.copyTo(
//                        info.outputProvider.getContentLocation(it.name, inputTypes, scopes, Format.JAR),
//                        overwrite = true
//                )
//            }
        }
    }
}