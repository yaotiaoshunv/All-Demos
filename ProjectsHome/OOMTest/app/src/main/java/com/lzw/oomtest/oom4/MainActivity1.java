package com.lzw.oomtest.oom4;

import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import com.lzw.oomtest.R;

/**
 * Created by Li Zongwei on 2020/12/1.
 **/
public class MainActivity1 extends AppCompatActivity {
    private MyThread mMyThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMyThread = new MyThread();
        mMyThread.start();
    }

    private static class MyThread extends Thread {
        private boolean mIsRunning = false;

        @Override
        public void run() {
            mIsRunning = true;
            while (mIsRunning) {
                //处理任务
                SystemClock.sleep(1000L);
            }
        }

        public void close() {
            mIsRunning = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyThread.close();
    }
}
