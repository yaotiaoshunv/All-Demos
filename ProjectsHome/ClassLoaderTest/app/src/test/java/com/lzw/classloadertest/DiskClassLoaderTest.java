package com.lzw.classloadertest;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by Li Zongwei on 2020/12/29.
 **/
public class DiskClassLoaderTest {

    @Test
    public void testClassLoader() {
        DiskClassLoader diskClassLoader = new DiskClassLoader("file:///D:/论文/OneDrive/桌面/testClassLoader/");
        try {
            Class c = diskClassLoader.loadClass("Secret");
            if (c != null) {
                Object obj = c.newInstance();
                System.out.println(obj.toString());
                Method method = c.getDeclaredMethod("printSecret", null);
                method.invoke(obj, null);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}