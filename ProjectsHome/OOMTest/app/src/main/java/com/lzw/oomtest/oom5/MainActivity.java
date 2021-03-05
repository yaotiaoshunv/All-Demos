package com.lzw.oomtest.oom5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lzw.oomtest.R;

/**
 * Created by Li Zongwei on 2020/12/1.
 **/
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.registerReceiver(mReceiver, new IntentFilter());
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //收到广播时的处理
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mReceiver);
    }
}
