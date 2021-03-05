package com.lzw.oomtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lzw.oomtest.oom1.AppSetting;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppSetting.getInstance(this);
    }
}