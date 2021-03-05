package com.lizw.qqstepview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by Li Zongwei on 2021/2/26.
 **/
public class QQStepView extends View {
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

    private int mCurrentStep;
    private int mMaxStep;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterBorderWidth = array.getDimensionPixelSize(R.styleable.QQStepView_outerBorderWidth, mOuterBorderWidth);
        mInnerBorderWidth = array.getDimensionPixelSize(R.styleable.QQStepView_innerBorderWidth, mInnerBorderWidth);
        mOuterBorderColor = array.getColor(R.styleable.QQStepView_outerBorderColor, mOuterBorderColor);
        mInnerBorderColor = array.getColor(R.styleable.QQStepView_innerBorderColor, mInnerBorderColor);
        mText = array.getString(R.styleable.QQStepView_mText);
        mTextColor = array.getColor(R.styleable.QQStepView_mTextColor, mTextColor);
        mTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_mTextSize, mTextSize);

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
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 调用者布局中可能使用 wrap_content / 宽高不一致 如何处理？
        // 1、wrap_content 让其不能使用wrap_content / 设置默认值30dp
        // 2、宽高不一致  使用最小值，确保是个正方形

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {

        }

        int result = Math.min(width, height);
        setMeasuredDimension(result, result);
    }

    /**
     * 画外圆弧，内圆弧，文字
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画外圆弧
        float borderWidth = mOuterBorderWidth / 2;
        RectF rectF = new RectF(borderWidth, borderWidth,
                getWidth() - borderWidth, getHeight() - borderWidth);  //?参数没看懂
        canvas.drawArc(rectF, 135f, 270f, false, mOutPaint);

        // 画内圆弧
        float sweepAnglePercent = (float) mCurrentStep / mMaxStep;
        canvas.drawArc(rectF, 135, sweepAnglePercent * 270, false, mInnerPaint);

        // 画文字
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseline = getHeight() / 2 + dy;
        canvas.drawText(mText, getWidth() / 2 - bounds.width() / 2, baseline, mTextPaint);
    }

    public synchronized void setMaxStep(int maxStep) {
        mMaxStep = maxStep;
    }

    public synchronized void setCurrentStep(int currentStep) {
        mCurrentStep = currentStep;

        mText = mCurrentStep + "";
        invalidate();
    }
}
