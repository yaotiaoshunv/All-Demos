package com.lzw.synchronizedtest;

import java.awt.SystemColor;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Li Zongwei on 2021/1/5.
 **/
public class ReadWriteTest {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static String number = "0";

    public static void main(String[] args) {

    }

    static class Reader implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i <= 10) {
                lock.readLock().lock();
                System.out.println(Thread.currentThread().getName() + " ---> Number is " + number);
                lock.readLock().unlock();
            }
        }
    }
}
