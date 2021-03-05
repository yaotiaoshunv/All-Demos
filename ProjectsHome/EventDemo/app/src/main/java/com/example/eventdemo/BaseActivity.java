package com.example.eventdemo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private final String  LIEF_RECYCLE_TAG = "life recycle" + TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG,"onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.v(LIEF_RECYCLE_TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.v(LIEF_RECYCLE_TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.v(LIEF_RECYCLE_TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.v(LIEF_RECYCLE_TAG,"onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.v(LIEF_RECYCLE_TAG,"onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.v(LIEF_RECYCLE_TAG,"onDestroy");
    }
}
