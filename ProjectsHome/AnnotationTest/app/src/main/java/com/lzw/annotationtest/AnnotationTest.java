package com.lzw.annotationtest;

/**
 * Created by Li Zongwei on 2020/12/21.
 **/
public class AnnotationTest {
    @GET(value = "http：//ip.taobao.com/59.108.54.3")
    public String getIpMsg() {
        return "";
    }

    @GET(value = "http：//ip.taobao.com/")
    public String getIp() {
        return "";
    }
}
