package com.lzw.classloadertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClassLoader classLoader = MainActivity.this.getClassLoader();
        Log.v("MainActivity:",classLoader.toString());
    }
}