package com.lizw.lettersidebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by Li Zongwei on 2021/3/4.
 **/
public class LetterSideBar extends View {
    private int mTextSize = 20;
    private int mTextColor = Color.BLUE;

    private Paint mPaint;

    // 65 -> char A
    private int letter = 65;
    private int currentLetter;

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LetterSideBar);
        mTextColor = array.getColor(R.styleable.LetterSideBar_textColor, mTextColor);
        mTextSize = array.getDimensionPixelSize(R.styleable.LetterSideBar_textSize, mTextSize);

        array.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(sp2px(mTextSize));
        mPaint.setColor(mTextColor);
    }

    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 宽度 = getLeftPadding() + getRightPadding + 字母宽度（取决于画笔）
        int textWidth = (int) mPaint.measureText("W");
        int width = getPaddingLeft() + getPaddingRight() + textWidth;

        // 高度
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / 26;
        for (int i = 0; i < 26; i++) {
            float x = getWidth() / 2 - mPaint.measureText((char) (letter + i) + "") / 2;
//            Log.d("tag", "width()/2 :   " + getWidth() / 2 + "   letterWidth/2 " + mPaint.measureText((char) (letter + i) + "") / 2);
//            Log.d("tag", x + "");
            int letterCenterY = i * itemHeight + itemHeight / 2 + getPaddingTop();
            Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
            int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
            int baseline = letterCenterY + dy;

            mPaint.setColor(mTextColor);
            canvas.drawText((char) (letter + i) + "", x, baseline, mPaint);
            if (currentLetter == letter + i) {
                mPaint.setColor(Color.RED);

                canvas.drawText((char) (currentLetter) + "", x, baseline, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float currentMoveY = event.getY();
                int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / 26;
                int currentPosition = (int) (currentMoveY / itemHeight);
                if (currentPosition < 0) {
                    currentPosition = 0;
                }
                if (currentPosition > 25) {
                    currentPosition = 25;
                }

                if (currentLetter == letter + currentPosition) {
                    Log.d("tag", "相等");
                } else {
                    currentLetter = letter + currentPosition;
                    invalidate();
                }
                if (mOnTouchListener != null) {
                    mOnTouchListener.touch((char) currentLetter, true);
                }
                break;
            case MotionEvent.ACTION_UP:
                mOnTouchListener.touch((char) currentLetter, false);
        }
        return true;
    }

    public interface OnTouchLetterSideBarListener {
        void touch(char letter, boolean isTouching);
    }

    private OnTouchLetterSideBarListener mOnTouchListener;

    public void setOnTouchListener(OnTouchLetterSideBarListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }
}
