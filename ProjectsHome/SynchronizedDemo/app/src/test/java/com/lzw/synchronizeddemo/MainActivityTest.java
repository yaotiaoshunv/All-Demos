package com.lzw.synchronizeddemo;

import org.junit.Test;


/**
 * Created by Li Zongwei on 2020/9/27.
 **/
public class MainActivityTest {

    @Test
    public void onCreate() throws InterruptedException {

        Thread t1 = new Thread(new SynchronizedTest() , "A");
        Thread t2 = new Thread(new SynchronizedTest(), "B");

        t1.start();
        t2.start();

    }


    @Test
    public void test(){
        Account account = new Account("zhang san", 10000.0f);
        AccountOperator accountOperator = new AccountOperator(account);

        final int THREAD_NUM = 5;
        Thread threads[] = new Thread[THREAD_NUM];
        for (int i = 0; i < THREAD_NUM; i ++) {
            threads[i] = new Thread(accountOperator, "Thread" + i);
            threads[i].start();
        }
    }

}