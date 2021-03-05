package com.lizw.ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by Li Zongwei on 2021/3/3.
 **/
public class RatingBar extends View {
    private static final String TAG = "RatingBar";

    private Bitmap mNormalBitmap;
    private Bitmap mFocusBitmap;
    private int mGradeNumber = 0;

    private int mCurrentGrade = 0;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        int starNormalId = array.getResourceId(R.styleable.RatingBar_starNormal, 0);
        if (starNormalId == 0) {
            throw new RuntimeException("请设置属性 starNormal");
        }
        mNormalBitmap = BitmapFactory.decodeResource(getResources(), starNormalId);

        int starFocusId = array.getResourceId(R.styleable.RatingBar_starFocus, 0);
        if (starFocusId == 0) {
            throw new RuntimeException("请设置属性 starFocusId");
        }
        mFocusBitmap = BitmapFactory.decodeResource(getResources(), starFocusId);

        mGradeNumber = array.getInteger(R.styleable.RatingBar_gradeNumber, mGradeNumber);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
        int width = mNormalBitmap.getWidth() * mGradeNumber;
        int height = mNormalBitmap.getHeight();
//        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mGradeNumber; i++) {
            int x = i * mNormalBitmap.getWidth();
            if (mCurrentGrade > i) {
                canvas.drawBitmap(mFocusBitmap, x, 0, null);
            } else {
                canvas.drawBitmap(mNormalBitmap, x, 0, null);
            }
        }
    }

    float x;
    float y;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
//            case MotionEvent.ACTION_UP:
                float moveX = event.getX();
                Log.d(TAG, moveX + "");
                int currentGrade = (int) (moveX / mFocusBitmap.getWidth() + 1);
                if (currentGrade > mGradeNumber) {
                    currentGrade = mGradeNumber;
                }

                // 优化，减少onDraw()的调用
                if (currentGrade != mCurrentGrade) {
                    mCurrentGrade = currentGrade;
                    invalidate();
                }
        }
        // 不能使用super，不然只会处理DOWN事件
        return super.onTouchEvent(event);
    }
}
