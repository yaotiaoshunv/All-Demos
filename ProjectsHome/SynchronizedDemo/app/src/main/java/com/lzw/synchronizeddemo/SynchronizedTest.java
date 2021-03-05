package com.lzw.synchronizeddemo;

/**
 * @author Li Zongwei
 * @date 2020/9/27
 **/
class SynchronizedTest implements Runnable {
    private int count;

    public SynchronizedTest() {
        count = 0;
    }

    public void countAdd() {
        synchronized (this) {
            for (int i = 0; i < 5000; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + (count++));
            }
        }
    }

    //非synchronized代码块，未对count进行读写操作，所以可以不用synchronized
    public void printCount() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " count:" + count);
        }
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        if (threadName.equals("A")) {
            countAdd();
        } else if (threadName.equals("B")) {
            printCount();
        }
    }
}
