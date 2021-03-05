package com.lizw.customviewdemo.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.lizw.circleprogressbar.CircleProgressBar;
import com.lizw.colortracktextview.ColorTrackTextView;
import com.lizw.customviewdemo.R;
import com.lizw.qqstepview.QQStepView;

public class MainActivity extends AppCompatActivity {
    private ColorTrackTextView mColorTrackTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initQQStepView();

        mColorTrackTextView = findViewById(R.id.color_track_tv);

//        initCircleProgressBar();
    }

    private void initCircleProgressBar() {
        final CircleProgressBar mCircleProgressBar = findViewById(R.id.circle_progress_bar);
        mCircleProgressBar.test();
    }

    private void initQQStepView() {
        final QQStepView qqStepView = findViewById(R.id.qqStep);
        qqStepView.setMaxStep(10000);
        qqStepView.setCurrentStep(2000);

        Button button = findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                qqStepView.setCurrentStep(4000);
                ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 6000);
                valueAnimator.setDuration(3000L);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentStep = (float) animation.getAnimatedValue();
                        qqStepView.setCurrentStep((int) currentStep);
                    }
                });
                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.start();
            }
        });
    }

    public void leftToRight(View view) {
        mColorTrackTextView.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(3000L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentProgress = (float) animation.getAnimatedValue();
                mColorTrackTextView.setCurrentProgress(currentProgress);
            }
        });
        valueAnimator.start();
    }

    public void rightToLeft(View view) {
        mColorTrackTextView.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(3000L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentProgress = (float) animation.getAnimatedValue();
                mColorTrackTextView.setCurrentProgress(currentProgress);
            }
        });
        valueAnimator.start();
    }
}