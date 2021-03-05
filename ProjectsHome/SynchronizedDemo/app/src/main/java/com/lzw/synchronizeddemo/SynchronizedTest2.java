package com.lzw.synchronizeddemo;

/**
 * Created by Li Zongwei on 2020/9/27.
 **/
public class SynchronizedTest2 {

    public synchronized void method1(){
        System.out.println("Method 1 start");
        try {
            System.out.println("Method 1 execute");
            System.out.println(Thread.currentThread());
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1 end");
    }

    public synchronized void method2() {
        System.out.println("Method 2 start");
        try {
            System.out.println("Method 2 execute");
            System.out.println(Thread.currentThread());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 end");
    }

}
