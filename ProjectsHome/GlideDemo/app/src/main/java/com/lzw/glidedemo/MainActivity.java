package com.lzw.glidedemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
    private String url2 = "http://p1.pstatp.com/large/166200019850062839d3";
    private String url3 = "https://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&hd=undefined&latest=undefined&copyright=undefined&cs=2202780618,895893289&os=1055196534,347775746&simid=3407360983,148160930&pn=5&rn=1&di=20240&ln=1538&fr=&fmq=1606897990841_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fa3.att.hudong.com%2F45%2F36%2F14300000491308128107360639165.jpg&rpstart=0&rpnum=0&adpicid=0&force=undefined";
    private String url1 = "https://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&hd=undefined&latest=undefined&copyright=undefined&cs=121352583,3553479540&os=1329632943,471929181&simid=2545311548,2175359802&pn=7&rn=1&di=117810&ln=1538&fr=&fmq=1606897990841_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fa1.att.hudong.com%2F24%2F78%2F20300000291746133783784887147.jpg&rpstart=0&rpnum=0&adpicid=0&force=undefined";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

//        reflectDemo();
    }

    private void reflectDemo() {
        try {
//            Class clazz = Class.forName("androidx.appcompat.app.AppCompatActivity");

            Class<GeneratedAppGlideModule> clazz =
                    (Class<GeneratedAppGlideModule>)
                            Class.forName("com.lzw.glidedemo.GeneratedAppGlideModuleImpl");
                        Log.d(TAG, "reflectDemo: " + clazz.getName());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        final ImageView ivBg = findViewById(R.id.iv_background);
        Button btnUpdateImg = findViewById(R.id.btn_set_img);
//        Glide.with(this)
//                .load(url2)
//                .placeholder(R.mipmap.ic_launcher)
//                .into(ivBg);
        Button btnUpdateImg1 = findViewById(R.id.btn_set_img1);
        btnUpdateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(MainActivity.this)
                                .load(url)
                                .placeholder(R.mipmap.ic_launcher)
                                .into(ivBg);
                    }
                },1000L);
            }
        });
        btnUpdateImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(MainActivity.this)
                        .load(url3)
                        .into(ivBg);
            }
        });
    }
}