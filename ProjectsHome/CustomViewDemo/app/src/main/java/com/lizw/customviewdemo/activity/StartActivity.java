package com.lizw.customviewdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.lizw.customviewdemo.R;
import com.lizw.customviewdemo.activity.FirstActivity;
import com.lizw.customviewdemo.activity.MainActivity;
import com.lizw.customviewdemo.activity.ViewPagerActivity;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnStartMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnStartMain = findViewById(R.id.btn_start_main);
        Button btnStartViewPager = findViewById(R.id.btn_start_view_pager);
        Button btnStartFirst = findViewById(R.id.btn_start_first);
        Button btnStartLetter = findViewById(R.id.btn_start_letter);

        Log.e("TAG", "height1 - " + btnStartMain.getHeight());

        btnStartMain.setOnClickListener(this);
        btnStartViewPager.setOnClickListener(this);
        btnStartFirst.setOnClickListener(this);
        btnStartLetter.setOnClickListener(this);

        btnStartMain.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "height2 - " + btnStartMain.getMeasuredHeight());
            }
        }, 0L);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "height3" + btnStartMain.getMeasuredHeight());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_main:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_start_view_pager:
                startActivity(new Intent(this, ViewPagerActivity.class));
                break;
            case R.id.btn_start_first:
                startActivity(new Intent(this, FirstActivity.class));
                break;
            case R.id.btn_start_letter:
                startActivity(new Intent(this, LetterActivity.class));
        }
    }
}