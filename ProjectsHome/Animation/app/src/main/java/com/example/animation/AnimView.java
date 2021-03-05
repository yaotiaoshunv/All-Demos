package com.example.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Scroller;

import androidx.annotation.Nullable;

/**
 * @author Zongwei Li
 */
public class AnimView extends View implements GestureDetector.OnGestureListener {

    private static final float RADIUS = 50f;

    /**
     * 根据Point对象，来绘制圆形
     */
    private Point currentPoint;

    private int currentColor;

    private Paint paint;

    public AnimView(Context context) {
        this(context, null);
    }

    public AnimView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (currentPoint == null) {
            currentPoint = new Point(getWidth()/2, 0+RADIUS);
            startAnimation();
        }

        drawCircle(canvas);
    }

    private void startAnimation() {
        Point startPoint = new Point(getWidth()/2, 0);
        Point endPoint = new Point(getWidth()/2, getHeight()-RADIUS);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
        valueAnimator.addUpdateListener(animation -> {
            currentPoint = (Point) animation.getAnimatedValue();
            invalidate();
        });

        ObjectAnimator colorAnimator = ObjectAnimator.ofObject(this,"color",new ColorEvaluator(),
                Color.BLUE,Color.RED);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(valueAnimator).with(colorAnimator);
        animatorSet.setStartDelay(1000L);
        animatorSet.setDuration(3000L);
        animatorSet.setInterpolator(new DecelerateAccelerateInterpolator());
        animatorSet.start();
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, paint);
    }

    public int getColor(){
        return currentColor;
    }

    public void setColor(int color){
        currentColor = color;
        paint.setColor(currentColor);
        invalidate();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    private class DecelerateAccelerateInterpolator implements TimeInterpolator {
        @Override
        public float getInterpolation(float input) {
            if (input < 0.5) {
                return (float) (Math.sin(input * Math.PI) / 2);
            } else {
                return 1 - (float) (Math.sin(input * Math.PI) / 2);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);

        velocityTracker.computeCurrentVelocity(1000);
        int xVelocity = (int) velocityTracker.getXVelocity();
        int yVelocity = (int) velocityTracker.getYVelocity();

        velocityTracker.clear();
        velocityTracker.recycle();

        GestureDetector gestureDetector = new GestureDetector(this);
        //解决长按屏幕后无法拖动的现象
        gestureDetector.setIsLongpressEnabled(false);
        boolean consume = gestureDetector.onTouchEvent(event);
        return consume;


//        return super.onTouchEvent(event);
    }

    Scroller scroller = new Scroller(getContext());

    /**
     * 缓慢滚动到指定位置
     */
    private void smoothScrollTo(int destX,int destY){
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        // 1000ms内滑向destX，效果就是慢慢滑动
        scroller.startScroll(scrollX,0,delta,0,1000);
        invalidate();
    }

    @MyAnnotation
    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }

    public @interface MyAnnotation{}
}
