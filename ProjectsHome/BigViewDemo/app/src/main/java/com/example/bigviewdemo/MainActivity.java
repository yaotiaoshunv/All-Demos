package com.example.bigviewdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {
    MyBigView bigView;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bigView = findViewById(R.id.big_view);
        try {
            InputStream is = getAssets().open("big_image.jpg");
            bigView.setImage(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream is = getAssets().open("big_image.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            Log.e("未处理过的_BitmapInfo",bitmap.getByteCount()+"");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ImageView bigView = findViewById(R.id.iv_big_view);
//        bigView.setImageResource(R.drawable.big_image);
    }

    public void click(View v){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}