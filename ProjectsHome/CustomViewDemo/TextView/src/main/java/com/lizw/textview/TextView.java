package com.lizw.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 问题：
 * 1、这个TextView在手机上显示的效果和原生的不一样。
 * 2、如果继承了LinearLayout，还会不会显示？
 * 如果有background会显示，否则不显示。
 * Created by Li Zongwei on 2021/2/24.
 **/
public class TextView extends View {
    private String mText;
    private int mTextSize = 15;
    private int mTextColor = Color.BLACK;

    private Paint mPaint;

    /**
     * new的时候调用。
     * <p>
     * TextView textView = new TextView(this);
     */
    public TextView(Context context) {
        this(context, null);
    }

    /**
     * 在布局（layout）中使用时调用。
     * <p>
     * <com.lizw.customview_01.TextView
     * android:layout_width="wrap_content"
     * android:layout_height="wrap_content"
     * android:text="Hello World" />
     */
    public TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 在布局layout中使用，并且使用了style时调用。
     * <p>
     * <com.lizw.customview_01.TextView
     * style="@style/Default"
     * android:text="Hello World" />
     */
    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextView);
        mText = array.getString(R.styleable.TextView_text);
        // 15  15px  15sp
        mTextSize = array.getDimensionPixelSize(R.styleable.TextView_textSize, sp2px(mTextSize));
        mTextColor = array.getColor(R.styleable.TextView_textColor, mTextColor);

        array.recycle();

        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }

    /**
     * 自定义View的测量方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 指定布局的宽高
        // 指定控件的宽高，需要测量
        // 获取宽高的模式（wrap_content/match_parent/100dp）
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);  //获取前两位
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            // 计算的宽高 与 字体的长度有关 与字体的大小有关 用画笔来测量
            Rect bounds = new Rect();
            // 获取文本的Rect
            mPaint.getTextBounds(mText, 0, mText.length(), bounds);
            width = bounds.width() + getPaddingLeft() + getPaddingRight();
        }

        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            Rect bounds = new Rect();
            mPaint.getTextBounds(mText, 0, mText.length(), bounds);
            height = bounds.height() + getPaddingTop() + getPaddingBottom();
        }

        // 设置控件的宽高
        setMeasuredDimension(width, height);
    }

    /**
     * 用于绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // x ：开始的位置
        // y ： 基线（baseline）
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        Log.d("TAG", fontMetrics.bottom + "\n" + fontMetrics.top + "\n" + dy);
        int baseline = getHeight() / 2 + dy;
        Log.d("TAG", getHeight() / 2 + "");

        Rect bounds = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2 + getPaddingLeft();
        canvas.drawText(mText, x, baseline, mPaint);
    }

    /**
     * 处理事件（触摸事件）、跟用户的交互
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("tag", "手指按下");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("tag", "手指移动");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("tag", "手指抬起");
                break;
        }

//        invalidate();
        return super.onTouchEvent(event);
    }
}
