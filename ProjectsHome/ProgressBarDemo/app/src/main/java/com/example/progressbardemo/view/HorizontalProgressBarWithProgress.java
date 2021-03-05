package com.example.progressbardemo.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

public class HorizontalProgressBarWithProgress extends ProgressBar {
    /**
     *
     */
    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final int DEFAULT_TEXT_COLOR = 0xFFFC00D1;
    private static final int DEFAULT_TEXT_OFFSET = 10; //dp
    private static final int DEFAULT_COLOR_UNREACH= 0xFFD9799D;
    private static final int DEFAULT_COLOR_REACH = DEFAULT_TEXT_COLOR;
    private static final int DEFAULT_HEIGHT_UNREACH = 2;
    private static final int DEFAULT_HEIGHT_REACH = 2;

    private int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mUnReachColor = DEFAULT_COLOR_UNREACH;
    private int mUnReachHeight = DEFAULT_HEIGHT_UNREACH;
    private int mReachColor = DEFAULT_COLOR_REACH;
    private int mReachHeight = DEFAULT_HEIGHT_REACH;
    private int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

    private Paint mPaint = new Paint();

    private int mRealWidth;

    public HorizontalProgressBarWithProgress(Context context) {
        this(context,null);
    }

    public HorizontalProgressBarWithProgress(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalProgressBarWithProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        obtain
    }

    private int dp2px(int dpVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal,getResources().getDisplayMetrics());
    }

    private int sp2px(int spVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spVal,getResources().getDisplayMetrics());
    }
}
