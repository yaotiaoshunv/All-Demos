package com.lzw.annotationtest;

import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by Li Zongwei on 2020/12/21.
 **/
public class AnnotationProcessor {
    private static final String TAG = "AnnotationProcessor";

    public static void initAnnotation() {
        Method[] methods = AnnotationTest.class.getDeclaredMethods();
        for (Method m : methods) {
            GET get = m.getAnnotation(GET.class);
            Log.v(TAG, get.value());
        }
    }
}
