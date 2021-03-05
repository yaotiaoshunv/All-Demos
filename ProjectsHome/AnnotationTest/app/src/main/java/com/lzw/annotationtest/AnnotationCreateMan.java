package com.lzw.annotationtest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Li Zongwei on 2020/12/21.
 **/
@Retention(RetentionPolicy.CLASS)
public @interface AnnotationCreateMan {
    String name() default "Jack";

    int age() default 25;
}
