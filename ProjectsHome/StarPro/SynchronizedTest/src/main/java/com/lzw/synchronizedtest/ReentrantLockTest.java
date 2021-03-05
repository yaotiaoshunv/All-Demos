package com.lzw.synchronizedtest;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Li Zongwei on 2021/1/5.
 **/
public class ReentrantLockTest {
    private ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        final ReentrantLockTest s1 = new ReentrantLockTest();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                s1.printReentrantLockLog();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                s1.printReentrantLockLog();
            }
        });

        t1.start();
        t2.start();
    }

    /**
     * Basic use of ReentrantLock.
     */
    public void printReentrantLockLog() {
        try {
            lock.lock();
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " is printing " + i);
            }
        }finally {
            lock.unlock();
        }
    }
}
