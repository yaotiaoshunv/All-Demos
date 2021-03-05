package com.lizw.circleprogressbar;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by Li Zongwei on 2021/3/2.
 **/
public class CircleProgressBar extends View {
    private int mOuterBorderWidth = 10;
    private int mInnerBorderWidth = 10;
    private int mOuterBorderColor = Color.red(100);
    private int mInnerBorderColor = Color.BLUE;
    private String mText;
    private int mTextColor = Color.RED;
    private int mTextSize = 20;

    private Paint mOutPaint;
    private Paint mInnerPaint;
    private Paint mTextPaint;

    private int mCurrentPercent = 0;
    private int mMaxPercent = 100;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        mOuterBorderWidth = array.getDimensionPixelSize(R.styleable.CircleProgressBar_outerBorderWidth, mOuterBorderWidth);
        mInnerBorderWidth = array.getDimensionPixelSize(R.styleable.CircleProgressBar_innerBorderWidth, mInnerBorderWidth);
        mOuterBorderColor = array.getColor(R.styleable.CircleProgressBar_outerBorderColor, mOuterBorderColor);
        mInnerBorderColor = array.getColor(R.styleable.CircleProgressBar_innerBorderColor, mInnerBorderColor);
        mText = array.getString(R.styleable.CircleProgressBar_mText);
        mTextColor = array.getColor(R.styleable.CircleProgressBar_mTextColor, mTextColor);
        mTextSize = array.getDimensionPixelSize(R.styleable.CircleProgressBar_mTextSize, mTextSize);

        array.recycle();

        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setColor(mOuterBorderColor);
        mOutPaint.setStrokeWidth(mOuterBorderWidth);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutPaint.setStyle(Paint.Style.STROKE);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerBorderColor);
        mInnerPaint.setStrokeWidth(mOuterBorderWidth);
//        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int min = Math.min(width, height);
        setMeasuredDimension(min, min);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int borderWidth = mOuterBorderWidth / 2;
        RectF rectF = new RectF(borderWidth, borderWidth, getWidth() - borderWidth, getHeight() - borderWidth);
        // 绘制外弧
        canvas.drawArc(rectF, 0f, 360f, false, mOutPaint);
        // 绘制内弧
        canvas.drawArc(rectF, 0f, (float) mCurrentPercent / mMaxPercent * 360f, false, mInnerPaint);
        // 绘制文字
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseline = getHeight() / 2 + dy;
        canvas.drawText(mText, x, baseline, mTextPaint);
    }

    public synchronized void setCurrentPercent(int currentPercent) {
        mCurrentPercent = currentPercent;
        mText = mCurrentPercent + "%";
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        test();
        return true;
    }

    /**
     * 用于测试
     */
    public void test() {
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(0, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentPercent = (int) animation.getAnimatedValue();
                setCurrentPercent(currentPercent);
            }
        });
        valueAnimator.setDuration(2000L);
        valueAnimator.start();
    }
}
