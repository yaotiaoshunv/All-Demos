package com.lzw.synchronizedtest;

public class SynchronizedMethods {
    private Object lock = new Object();

    public static void main(String[] args) {
        final SynchronizedMethods s1 = new SynchronizedMethods();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                s1.printBlockLog();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                s1.printBlockLog();
            }
        });

        t1.start();
        t2.start();
    }

    /**
     * block
     */
    public void printBlockLog() {
        synchronized (lock) {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " is printing " + i);
            }
        }
    }
}