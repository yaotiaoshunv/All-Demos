package com.example.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 帧动画
 *
 * @author Zongwei Li
 */
public class FrameAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStart, btnStop;
    private ImageView imgShowFrame;
    private AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);

        initViews();
        anim = (AnimationDrawable) imgShowFrame.getBackground();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private void initViews() {
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        imgShowFrame = findViewById(R.id.img_show_frame);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                anim.start();
                break;
            case R.id.btn_stop:
                anim.stop();
                break;
            default:
        }
    }
}
