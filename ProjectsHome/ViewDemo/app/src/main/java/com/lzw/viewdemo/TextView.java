package com.lzw.viewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class TextView extends View {

    private String mText;
    private int mTextColor;
    private int mTextSize;
    private int mMaxLength;
    private Color mBackground;

    private Paint mPaint;

    /**
     * new的时候调用
     */
    public TextView(Context context) {
        this(context,null);
    }

    /**
     * 在布局中使用时调用
     */
    public TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    /**
     * 在布局中使用，并且用到style时调用
     */
    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //这个方法可以不用attrs参数，不要忘了传，会导致xml中属性加载不出来
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.TextView);
        mText = typedArray.getString(R.styleable.TextView_myText);
        mTextColor = typedArray.getColor(R.styleable.TextView_textColor, mTextColor);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.TextView_textSize, mTextSize);

        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        //抗锯齿
        mPaint.setAntiAlias(true);
    }

    /**
     * 将sp转为px
     * @param sp
     * @return
     */
    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
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

        //布局的宽高都是通过这个方法指定

        //指定控件的宽高，需要测量

        //获取宽高的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //1、确定的值，直接获取，不需要计算
        int width = MeasureSpec.getSize(widthMeasureSpec);

        //2、wrap_content，需要计算
        if (widthMode == MeasureSpec.AT_MOST){
            //计算宽度，与字体长度、大小有关，用画笔来测量
            Rect bounds = new Rect();
            //获取文本的Rect
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            width = bounds.width();
        }

        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.AT_MOST){
            Rect bounds = new Rect();
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            height = bounds.height();
        }

        //设置控件的宽高
        setMeasuredDimension(width,height);
    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // y : 基线
        canvas.drawText(mText,0,getHeight(),mPaint);
    }

    /**
     * 处理跟用户交互的，手指触摸等
     * @param event 事件分发/事件拦截
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
