package com.lzw.oomtest.oom3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.print.PrinterId;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.lzw.oomtest.R;
import com.lzw.oomtest.oom1.AppSetting;

import java.lang.ref.WeakReference;

/**
 * Created by Li Zongwei on 2020/12/1.
 **/
public class MainActivity1 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
    }

    private void start() {
        Message msg = Message.obtain();
        msg.what = 1;
        mHandler.sendMessage(msg);
    }

    private Handler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private WeakReference<MainActivity1> mActivity1WeakReference;

        public MyHandler(MainActivity1 activity1) {
            mActivity1WeakReference = new WeakReference<>(activity1);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            MainActivity1 activity1 = mActivity1WeakReference.get();
            switch (msg.what) {
                //处理逻辑
            }
        }
    }

}
