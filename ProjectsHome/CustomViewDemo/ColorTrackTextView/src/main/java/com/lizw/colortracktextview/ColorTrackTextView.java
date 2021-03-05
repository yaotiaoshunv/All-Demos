package com.lizw.colortracktextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * Created by Li Zongwei on 2021/3/1.
 **/
public class ColorTrackTextView extends androidx.appcompat.widget.AppCompatTextView {
    private int mOriginColor = Color.BLACK;
    private int mChangeColor = Color.RED;

    // 1、实现一个文字两种颜色，绘制不变色字体的画笔
    private Paint mOriginPaint;
    // 2、实现一个文字两种颜色，绘制变色字体的画笔
    private Paint mChangePaint;

    private float mCurrentProgress = 0.0f;

    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        mOriginColor = array.getColor(R.styleable.ColorTrackTextView_originColor, mOriginColor);
        mChangeColor = array.getColor(R.styleable.ColorTrackTextView_changeColor, mChangeColor);

        array.recycle();

        mOriginPaint = getPaintColor(mOriginColor);
        mChangePaint = getPaintColor(mChangeColor);
    }

    private Paint getPaintColor(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        // 防抖
        paint.setDither(true);
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 根据进度，算出中间值
        int middle = (int) (getWidth() * mCurrentProgress);

        // 不用父类的draw。
        drawText(canvas, mChangePaint, middle);
    }

    private void drawText(Canvas canvas, Paint paint, int middle) {
        String text = getText().toString();

        // 先绘制整个不变色（原始颜色）的部分，在绘制变色部分
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, mOriginPaint);

        Rect changeRect;
        if (mDirection == Direction.LEFT_TO_RIGHT) {
            changeRect = new Rect(0, 0, middle, getHeight());
        } else {
            changeRect = new Rect(getWidth() - middle, 0, getWidth(), getHeight());
        }
        canvas.clipRect(changeRect);
        canvas.drawText(text, x, baseLine, paint);
    }

    public void setDirection(Direction direction) {
        mDirection = direction;
    }

    public void setCurrentProgress(float currentProgress) {
        mCurrentProgress = currentProgress;
        invalidate();
    }

    public void setChangeColor(int changeColor) {
        mChangeColor = changeColor;
        mChangePaint.setColor(mChangeColor);
    }

    public void setOriginColor(int originColor) {
        mOriginColor = originColor;
        mOriginPaint.setColor(mOriginColor);
    }
}
