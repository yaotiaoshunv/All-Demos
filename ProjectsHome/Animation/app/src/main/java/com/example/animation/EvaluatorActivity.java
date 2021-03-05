package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EvaluatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new AnimView(this));
    }
}