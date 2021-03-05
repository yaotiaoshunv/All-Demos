package com.lizw.customviewdemo;

import java.io.File;

/**
 * 工具类，不需要指定package，不然在cmd运行会报错。
 *
 * Created by Li Zongwei on 2021/3/3.
 **/
public class FileUtilsCopy {
    public static void main(String[] args) {
        new FileUtils().deleteDir("D:\\ProjectsHome", "build");
    }

    /**
     * 删除指定目录下的指定文件夹
     *
     * @param path    指定目录
     * @param dirName 指定文件夹
     */
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
     * 删除指定文件夹及其子文件/文件夹
     *
     * @param path 文件夹路径
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