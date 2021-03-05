package com.lzw.synchronizeddemo;

/**
 * Created by Li Zongwei on 2020/9/28.
 **/

import android.util.Log;

/**
 * 同步线程
 */
class SyncThread implements Runnable {
    private static int count;

    public SyncThread() {
        count = 0;
    }

    public static void method() {
        synchronized (SyncThread.class){
            for (int i = 0; i < 5; i ++) {
                try {
                    Log.d("thread1",Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public synchronized void run() {
        method();
    }
}
