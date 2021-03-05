package com.lzw.synchronizedtest;

/**
 * Created by Li Zongwei on 2021/1/4.
 **/
public class Foo {
    private int num;

    public synchronized void test1() {
        int i = 0;
            num = i + 1;
    }
}
