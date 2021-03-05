package com.lzw.bigviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyBigView2 myBigView = findViewById(R.id.mbv_1);
        try {
            InputStream is = getAssets().open("big_image2.jpg");
            myBigView.setImage(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}