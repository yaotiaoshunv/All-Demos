package com.lizw.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by Li Zongwei on 2021/3/2.
 **/
public class LoadingView extends View {
    private static final String TAG = "LoadingView";

    private Paint mPaint;

    private final int SHAPE_CIRCLE = 1;
    private final int SHAPE_RECT = 2;
    private final int SHAPE_TRIANGLE = 3;

    private int currentShape = 1;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setStrokeWidth(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int min = Math.min(width, height);
        setMeasuredDimension(min, min);
    }

    private Path mPath;

    @Override
    protected void onDraw(Canvas canvas) {
        switch (currentShape) {
            case SHAPE_CIRCLE:
                mPaint.setColor(Color.BLUE);
//                canvas.drawArc(rectF, 0f, 360f, true, mPaint);
                int center = getWidth() / 2;
                canvas.drawCircle(center, center, center, mPaint);
                break;
            case SHAPE_RECT:
                mPaint.setColor(Color.RED);
                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
                break;
            case SHAPE_TRIANGLE:
                mPaint.setColor(Color.BLACK);
                if (mPath == null) {
                    mPath = new Path();
                    mPath.moveTo(getWidth() / 2, 0);
                    // 等边三角形，计算边长
                    float sqrt = (float) Math.sqrt(getWidth() * getWidth() - (getWidth() / 2) * (getWidth() / 2));
                    mPath.lineTo(0,  sqrt);
                    mPath.lineTo(getWidth(),  sqrt);
                    // 使路径闭合
                    mPath.close();
                }
                canvas.drawPath(mPath, mPaint);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 思考：去除同步锁会发生异常？
                synchronized (LoadingView.class) {
                    while (true) {
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        setCurrentShape(currentShape == 3 ? 1 : currentShape + 1);
                        invalidate();
                        Log.d(TAG, currentShape + "");
                    }
                }
            }
        }).start();
        return true;
    }

    public synchronized void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }
}
