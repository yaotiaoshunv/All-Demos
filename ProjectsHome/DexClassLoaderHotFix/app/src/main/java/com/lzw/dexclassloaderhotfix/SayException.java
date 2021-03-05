package com.lzw.dexclassloaderhotfix;

/**
 * Created by Li Zongwei on 2020/12/29.
 **/
public class SayException implements ISay{

    @Override
    public String saySomething() {
        return "something wrong here!";
    }
}
