package com.example.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;

public class LoadingView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static final String TAG = "LoadingView";

    /**
     * 动画拆分
     */
    private enum LoadingState {
        /**
         * 1）小球在线上向下移动
         */
        DOWN,
        /**
         * 2）小球在线上向上移动
         */
        UP,
        /**
         * 3）自由落体运动
         */
        FREE
    }

    private LoadingState loadingState = LoadingState.DOWN;
    /**
     * 小球颜色
     */
    private int ballColor;
    /**
     * 小球半径
     */
    private int ballRadius;
    /**
     * 连线颜色
     */
    private int lineColor;
    /**
     * 连线长度
     */
    private int lineWidth;
    /**
     * 绘制线宽
     */
    private int strokeWidth;
    /**
     * 水平位置下降的距离
     */
    private float downDistance = 0;
    /**
     * 水平位置下降的距离（最低点）
     */
    private float maxDownDistance;
    /**
     * 从底部上弹的距离
     */
    private float upDistance;
    /**
     * 自由落体的距离
     */
    private float freeDownDistance;
    /**
     * 自由落体的距离（最高点）
     */
    private float maxFreeDownDistance;
    private ValueAnimator downControl;
    private ValueAnimator upControl;
    private ValueAnimator freeDownControl;
    private AnimatorSet animatorSet;
    private boolean isAnimationShowing;
    private SurfaceHolder holder;
    private Canvas canvas;
    private Paint paint;
    private Path path;
    /**
     * 标识新线程是否在运行
     */
    private boolean isRunning;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        initAttrs(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        path = new Path();
        holder = getHolder();
        //给SurfaceHolder添加回调
        holder.addCallback(this);
        //初始化动作控制动画
        initControl();
    }

    /**
     * 初始化动作控制动画
     */
    private void initControl() {
        downControl = ValueAnimator.ofFloat(0,maxDownDistance);
        downControl.setDuration(500);
        downControl.setInterpolator(new ShockInterpolator());
        downControl.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                downDistance = (float) valueAnimator.getAnimatedValue();
            }
        });
        downControl.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                loadingState = LoadingState.DOWN;
                isAnimationShowing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        upControl = ValueAnimator.ofFloat(0,maxDownDistance);
        upControl.setDuration(500);
        upControl.setInterpolator(new AccelerateInterpolator());
        upControl.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                upDistance = (float) valueAnimator.getAnimatedValue();
                if (upDistance >= maxDownDistance && freeDownControl != null && !freeDownControl.isRunning()
                        &&!freeDownControl.isStarted()){
                    freeDownControl.start();
                }
            }
        });
        upControl.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                loadingState = LoadingState.UP;
                isAnimationShowing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        freeDownControl = ValueAnimator.ofFloat(0, (float) (2 * Math.sqrt(maxFreeDownDistance/5)));
        freeDownControl.setDuration(500);
        freeDownControl.setInterpolator(new AccelerateInterpolator());
        freeDownControl.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float t = (float) valueAnimator.getAnimatedValue();
                freeDownDistance = (float) ( 10 * Math.sqrt(maxFreeDownDistance/5)*t-5*t*t);
            }
        });
        freeDownControl.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                loadingState = LoadingState.FREE;
                isAnimationShowing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimationShowing = false;
                //重新开启动画
                startAllAnimator();
            }
        });
        animatorSet = new AnimatorSet();
        animatorSet.play(downControl).before(upControl);
    }

    public void startAllAnimator() {
        if (isAnimationShowing){
            return;
        }
        if (animatorSet.isRunning()){
            animatorSet.end();
            animatorSet.cancel();
        }
        loadingState = LoadingState.DOWN;
        //绘制线程开启
        new Thread(this).start();
        //动画开启
        animatorSet.start();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        ballColor = typedArray.getColor(R.styleable.LoadingView_ball_color, Color.BLUE);
        lineColor = typedArray.getColor(R.styleable.LoadingView_line_color, Color.BLUE);
        lineWidth = typedArray.getDimensionPixelOffset(R.styleable.LoadingView_line_width, 200);
        strokeWidth = typedArray.getDimensionPixelOffset(R.styleable.LoadingView_stroke_width, 4);
        ballRadius = typedArray.getDimensionPixelOffset(R.styleable.LoadingView_ball_radius, 10);
        maxDownDistance = typedArray.getDimensionPixelOffset(R.styleable.LoadingView_max_down, 50);
        maxFreeDownDistance = typedArray.getDimensionPixelOffset(R.styleable.LoadingView_max_up, 50);

        typedArray.recycle();
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        //在此绘制动画的第一帧（此时为静止的）
        isRunning = true;
        drawView();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void run() {
        //TODO:绘制动画（死循环）
        while (isRunning) {
            drawView();
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawView() {
        //创建画布
        try {
            if (holder != null) {
                canvas = holder.lockCanvas();
                //清空屏幕两种方式
                //canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                canvas.drawColor(0xFFFFFFFF);
                paint.setColor(lineColor);
                path.reset();
                path.moveTo(getWidth() / 2f - lineWidth / 2f, getHeight() / 2f);
                if (loadingState == LoadingState.DOWN) {
                    //小球在绳子上下降

                    //求控制点的 y 坐标(P1)，公式：B(t) = (1-t)^2 P0 + 2t(1-t)P1 + t^2 P2,t : [0,1]
                    // B(t) 对应 downDistance（下落的最低点）
                    // t : 相对 p1 和 p2 之间的位置， 0.5 就是在中间
                    float controlPoint = getControlPoint(0.5f, downDistance, 0, 0);
                    Log.e(TAG, "controlPoint:" + controlPoint);

                    path.rQuadTo(lineWidth / 2f, controlPoint, lineWidth, 0);
                    paint.setColor(lineColor);
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawPath(path, paint);

                    //绘制小球
                    paint.setColor(ballColor);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(getWidth() / 2f, getHeight() / 2 + downDistance - ballRadius - strokeWidth / 2,
                            ballRadius, paint);

                } else {
                    //上升，或自由落体
                    path.rQuadTo(lineWidth / 2f, 2 * (maxDownDistance - upDistance),
                            lineWidth, 0);
                    paint.setColor(lineColor);
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawPath(path, paint);

                    //绘制小球
                    paint.setColor(ballColor);
                    paint.setStyle(Paint.Style.FILL);

                    if (loadingState == LoadingState.FREE){
                        //自由落体运动
                        canvas.drawCircle(getWidth() / 2f, getHeight() / 2 - freeDownDistance - ballRadius - strokeWidth / 2,
                                ballRadius, paint);
                    }else {
                        //上升
                        canvas.drawCircle(getWidth() / 2f, getHeight() / 2 + (maxDownDistance-upDistance) - ballRadius - strokeWidth / 2,
                                ballRadius, paint);
                    }
                }
                paint.setColor(ballColor);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(getWidth() / 2f - lineWidth / 2f, getHeight() / 2f, ballRadius, paint);
                canvas.drawCircle(getWidth() / 2f + lineWidth / 2f, getHeight() / 2f, ballRadius, paint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    /**
     * 获取贝塞尔曲线的控制点
     */
    private float getControlPoint(float t, float bt, float p0, float p2) {
        float p1 = (bt - (1 - t) * (1 - t) * p0 - t * t * p2) / (2 * t * (1 - t));
        return p1;
    }

    class ShockInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float v) {
            float value = (float) (1-Math.exp(-3*v)*Math.cos(10*v));
            return value;
        }
    }
}
