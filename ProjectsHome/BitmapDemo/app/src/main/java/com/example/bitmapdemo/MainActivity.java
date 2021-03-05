package com.example.bitmapdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.iv_big_view);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.big_image);
        bitmapInfo(bitmap);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Bitmap bitmap1 = ImageUtils.resizeBitmap(this,R.mipmap.big_image,imageView.getWidth(),imageView.getHeight(),true);
        bitmapInfo(bitmap1);
        imageView.setImageBitmap(bitmap1);
    }

    private void bitmapInfo(Bitmap bitmap) {
        Log.e("bitmap", "x : " + bitmap.getWidth() + " y : " + bitmap.getHeight() +
                " 内存大小 ： " + bitmap.getByteCount());
    }
}