package com.lzw.synchronizedtest;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Li Zongwei on 2021/1/5.
 **/
public class FairLockTest implements Runnable {
    private int sharedNumber = 0;
    private static ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        while (sharedNumber < 5) {
            lock.lock();
            try {
                sharedNumber++;
                System.out.println(Thread.currentThread().getName() +
                        "获得锁，sharedNumber is " + sharedNumber);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLockTest flt = new FairLockTest();
        Thread t1 = new Thread(flt);
        Thread t2 = new Thread(flt);
        t1.start();
        t2.start();
    }
}
