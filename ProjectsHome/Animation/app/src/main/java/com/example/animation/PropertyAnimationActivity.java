package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PropertyAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_one;
    private Button btn_two;
    private Button btn_three;
    private Button btn_four;
    private LinearLayout ly_root;
    private ImageView img_babi;

    private int rootWidth;
    private int rootHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
        bindViews();
    }

    private void bindViews() {
        ly_root = (LinearLayout) findViewById(R.id.ly_root);
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_two = (Button) findViewById(R.id.btn_two);
        btn_three = (Button) findViewById(R.id.btn_three);
        btn_four = (Button) findViewById(R.id.btn_four);
        img_babi = (ImageView) findViewById(R.id.img_babi);

        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        img_babi.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                lineAnimator();
                break;
            case R.id.btn_two:
                scaleAnimator();
                break;
            case R.id.btn_three:
                rotateAnimator();
                break;
            case R.id.btn_four:
                circleAnimator();
                break;
            case R.id.img_babi:
                Toast.makeText(PropertyAnimationActivity.this, "不愧是coder-pig~", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }

    /**
     * 位移动画
     */
    private void lineAnimator() {
        rootWidth = ly_root.getWidth();
        rootHeight = ly_root.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(rootHeight, 0, rootHeight / 4, rootHeight / 2, rootHeight / 4, rootHeight);
        valueAnimator.addUpdateListener(animation -> {
            int y = (int) animation.getAnimatedValue();
            int x = rootWidth / 2;
            moveView(img_babi, x, y);
        });
        valueAnimator.setDuration(10000L);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }

    /**
     * 缩放动画
     */
    private void scaleAnimator() {
        ValueAnimator scaleAnimator = ValueAnimator.ofFloat(1f,0f,1.5f);
        scaleAnimator.addUpdateListener(animation -> {
            float scale = (float) animation.getAnimatedValue();
            img_babi.setScaleX(scale);
            img_babi.setScaleY(scale);
        });
        scaleAnimator.setDuration(3000L);
        scaleAnimator.setInterpolator(new LinearInterpolator());

        scaleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        scaleAnimator.start();
    }

    /**
     * 旋转动画、透明度动画
     */
    private void rotateAnimator() {
        ValueAnimator rotateAnimator = ValueAnimator.ofInt(0, 360);
        rotateAnimator.addUpdateListener(animation -> {
            int rotateValue = (int) animation.getAnimatedValue();
            img_babi.setRotation(rotateValue);

            float fractionValue = animation.getAnimatedFraction();
            img_babi.setAlpha(fractionValue);
        });
        rotateAnimator.setDuration(3000L);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.start();
    }

    /**
     * 圆形旋转
     * 圆的参数方程:
     * x = R * sin(t)
     * y = R * cos(t)
     */
    private void circleAnimator() {
        rootWidth = ly_root.getWidth();
        rootHeight = ly_root.getHeight();
        final int R = rootWidth / 4;
        ValueAnimator circleAnimator = ValueAnimator.ofFloat(0f, (float) (2.0f * Math.PI));
        circleAnimator.addUpdateListener(animation -> {
            // 圆的参数方程 x = R * sin(t) y = R * cos(t)
            float t = (float) animation.getAnimatedValue();
            int x = (int) (R * Math.sin(t) + rootWidth / 2);
            int y = (int) (R * Math.cos(t) + rootHeight / 2);
            moveView(img_babi, x, y);
        });
        circleAnimator.setDuration(3000L);
        circleAnimator.setInterpolator(new LinearInterpolator());
        circleAnimator.start();
    }

    private void moveView(View view, int x, int y) {
        int left = x - view.getWidth() / 2;
        int top = y - view.getHeight();
        int right = left + view.getWidth();
        int bottom = top + view.getHeight();
        view.layout(left, top, right, bottom);

        Log.e("moveView", "left:" + left + "  top:" + top + "  right:" + right + "  bottom:" + bottom);
    }

}