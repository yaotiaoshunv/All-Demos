package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PropertyAnimationActivity2 extends AppCompatActivity implements View.OnClickListener {

    private Button btn_one;
    private Button btn_two;
    private Button btn_three;
    private Button btn_four;
    private Button btn_five;
    private LinearLayout ly_root;
    private TextView tv_pig;
    private int height;
    private ObjectAnimator animator1;
    private ObjectAnimator animator2;
    private ObjectAnimator animator3;
    private ObjectAnimator animator4;
    private AnimatorSet animSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation2);
        bindViews();
        initAnimator();
    }

    private void bindViews() {
        ly_root = (LinearLayout) findViewById(R.id.ly_root);
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_two = (Button) findViewById(R.id.btn_two);
        btn_three = (Button) findViewById(R.id.btn_three);
        btn_four = (Button) findViewById(R.id.btn_four);
        btn_five = (Button) findViewById(R.id.btn_five);
        tv_pig = (TextView) findViewById(R.id.tv_pig);

        height = ly_root.getHeight();
        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        btn_five.setOnClickListener(this);
        tv_pig.setOnClickListener(this);
    }

    //初始化动画
    private void initAnimator() {
        animator1 = ObjectAnimator.ofFloat(tv_pig, "alpha", 1f, 0f, 1f, 0f, 1f);
        animator2 = ObjectAnimator.ofFloat(tv_pig, "rotation", 0f, 360f, 0f);
        animator3 = ObjectAnimator.ofFloat(tv_pig, "scaleX", 2f, 4f, 1f, 0.5f, 1f);
        animator4 = ObjectAnimator.ofFloat(tv_pig, "translationY", height / 8, -100, height / 2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                animator1.setDuration(3000L);
                animator1.start();
                break;
            case R.id.btn_two:
                animator2.setDuration(3000L);
                animator2.start();
                break;
            case R.id.btn_three:
                animator3.setDuration(3000L);
                animator3.start();
                break;
            case R.id.btn_four:
                animator4.setDuration(3000L);
                animator4.start();
                break;
            case R.id.btn_five:
                //将前面的动画集合到一起~
                animSet = new AnimatorSet();
                animSet.play(animator4).with(animator3).with(animator2).after(animator1);
                animSet.setDuration(5000L);
                animSet.start();
                break;
            case R.id.tv_pig:
                Toast.makeText(PropertyAnimationActivity2.this, "ObjectAnimator简单使用", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }
}