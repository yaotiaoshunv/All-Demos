package com.lzw.oomtest.oom2;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lzw.oomtest.R;

/**
 * Created by Li Zongwei on 2020/11/30.
 **/
public class MainActivity extends AppCompatActivity {
    private static Info sInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sInfo = new Info(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sInfo != null) {
            sInfo = null;
        }
    }
}

class Info {
    Activity mActivity;

    public Info(Activity activity) {
        mActivity = activity;
    }
}