package com.lzw.annotationtest;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lzw.annotations.BindView;


public class MainActivity extends AppCompatActivity {

    //编译时注解
    @BindView(value = 111)
    TextView tv_show_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //运行时注解
        AnnotationProcessor.initAnnotation();
    }
}