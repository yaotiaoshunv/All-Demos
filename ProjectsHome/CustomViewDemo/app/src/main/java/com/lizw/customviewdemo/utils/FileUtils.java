
import java.io.File;

/**
 *
 * Created by Li Zongwei on 2021/3/3.
 **/
public class FileUtils {
    public static void main(String[] args) {
        new FileUtils().deleteDir("D:\\ProjectsHome", "build");
    }

    private void deleteDir(String path, String dirName) {
        File file = new File(path);
        if (file.isDirectory()) {
            if (dirName.equals(file.getName())) {
                System.out.println("deleted file:" + file.getName() + "   path:" + file.getPath());
                deleteAllFilesOfDir(new File(file.getPath()));
            } else {
                File[] rootFile = file.listFiles();
                for (File file1 : rootFile) {
                    deleteDir(file1.getPath(), dirName);
                }
            }
        }
    }

    /**
     */
    private void deleteAllFilesOfDir(File path) {
        if (!path.exists()) {
            return;
        }

        File[] files = path.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                file.delete();
                continue;
            }
            deleteAllFilesOfDir(file);
        }
        path.delete();
    }
}