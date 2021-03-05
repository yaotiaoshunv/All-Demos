package com.lzw.classloadertest;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Li Zongwei on 2020/12/29.
 **/
public class DiskClassLoader extends ClassLoader {
    private String filePath;

    public DiskClassLoader(String path) {
        filePath = path;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Class<?> findClass(String name) {
        String newPath = filePath + name + ".class";
        System.out.println(newPath);
        byte[] classBytes = null;
        Path path = null;
            try {
                path = Paths.get(new URI(newPath));
                classBytes = Files.readAllBytes(path);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return defineClass(name, classBytes, 0, classBytes.length);
    }
}
