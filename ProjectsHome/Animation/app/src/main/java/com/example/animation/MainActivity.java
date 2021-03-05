package com.example.animation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnFrameAnim, btnTweenAnim, btnPropertyAnim, btnPropertyAnim2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        btnFrameAnim = findViewById(R.id.btn_frame_animation);
        btnTweenAnim = findViewById(R.id.btn_tween_animation);
        btnPropertyAnim = findViewById(R.id.btn_property_animation);
        btnPropertyAnim2 = findViewById(R.id.btn_property_animation2);

        btnFrameAnim.setOnClickListener(this);
        btnTweenAnim.setOnClickListener(this);
        btnPropertyAnim.setOnClickListener(this);
        btnPropertyAnim2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_frame_animation:
                startActivity(new Intent(MainActivity.this, FrameAnimationActivity.class));
                break;
            case R.id.btn_tween_animation:
                startActivity(new Intent(MainActivity.this, TweenAnimActivity.class));
                break;
            case R.id.btn_property_animation:
                startActivity(new Intent(MainActivity.this, PropertyAnimationActivity.class));
                break;
            case R.id.btn_property_animation2:
                startActivity(new Intent(MainActivity.this, PropertyAnimationActivity2.class));
                break;
            default:
        }
    }
}