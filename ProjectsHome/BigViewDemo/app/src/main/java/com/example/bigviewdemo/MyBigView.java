package com.example.bigviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

public class MyBigView extends View implements View.OnTouchListener, GestureDetector.OnGestureListener {
    /**
     * 使用Rect，实现分块加载
     */
    private final Rect mRect;
    /**
     * 内存复用
     */
    private final BitmapFactory.Options mOptions;
    /**
     * 要支持上下滑动，需要手势支持，滚动类
     */
    private final GestureDetector mGestureDetector;
    /**
     * 要支持上下滑动，需要手势支持，滚动类
     */
    private final Scroller mScroller;

    private int mImageHeight;
    private int mImageWidth;

    private BitmapRegionDecoder mDecoder;

    private int mViewWidth;
    private int mViewHeight;
    private float mScale;
    private Bitmap mBitmap;

    public MyBigView(Context context) {
        this(context, null);
    }

    public MyBigView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBigView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //第一步：设置bigView需要的成员变量
        mRect = new Rect();
        mOptions = new BitmapFactory.Options();
        //手势支持
        mGestureDetector = new GestureDetector(context, this);
        //滚动类
        mScroller = new Scroller(context);

        setOnTouchListener(this);


    }

    /**
     * 第二步：设置图片
     *
     * 编码格式：
     * ARGB_8888:A/R/G/B各占8位，共4个字节(每个像素点的大小)
     * RGB_565:没有透明通道A，R/G/B分别占5、6、5位，共16位，2字节
     * <p>
     * 拓展:怎么计算一张图片的大小。像素点，编码格式
     * 一张大小位50*100的图片，编码格式位ARGB_8888，则大小为：
     * 像素点个数：50*100
     * 大小：50*100*4
     *
     * @param is
     */
    public void setImage(InputStream is) {
        //获取图片的宽高,而不载入到内存中
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, mOptions);
        mImageWidth = mOptions.outWidth;
        mImageHeight = mOptions.outHeight;

        //开启内存复用
        mOptions.inMutable = true;
        //设置图片格式
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        //和前面获取宽高配套使用，不要忘记
        mOptions.inJustDecodeBounds = false;

        //区域解码器，分块加载，只加载屏幕上的部分
        try {
            mDecoder = BitmapRegionDecoder.newInstance(is, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestLayout();
    }

    /**
     * 第三步：测量图片
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();

        //确定图片加载的区域
        mRect.top = 0;
        mRect.left = 0;
        mRect.right = mImageWidth;

        //计算缩放因子
        mScale = mViewWidth / (float) mImageWidth;
        mRect.bottom = (int) (mViewHeight / mScale);
    }

    private Matrix matrix;
    /**
     * 第四步：画出具体的内容
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDecoder == null) {
            return;
        }
        //复用内存
        mOptions.inBitmap = mBitmap;
        mBitmap = mDecoder.decodeRegion(mRect, mOptions);

        //得到一个矩阵进行缩放
        matrix = new Matrix();
        matrix.setScale(mScale, mScale);
        canvas.drawBitmap(mBitmap, matrix, null);
        Log.e("处理过的_BitmapInfo",mBitmap.getByteCount()+"");
    }

    public void setImage(String path) {

    }

    public void setImage(int resId) {

    }

    /**
     * 第五步：处理点击事件
     *
     * @param view
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGestureDetector.onTouchEvent(motionEvent);
    }

    /**
     * 第六步：处理手势按下去事件
     *
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        //如果滑动还没有停止，强制停止
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
        //接收后续事件
        return true;
    }

    /**
     * 第七步：处理滑动事件
     *
     * @param e1        开始事件
     * @param e2        即时事件
     * @param distanceX x移动距离
     * @param distanceY y移动距离
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //上下移动的时候，直接改变Rect显示区域
        mRect.offset(0, (int) distanceY);
        //判断到达顶部和底部的情况
        if (mRect.bottom > mImageHeight) {
            mRect.bottom = mImageHeight;
            mRect.top = mImageHeight - (int) (mViewHeight / mScale);
        }
        if (mRect.top < 0) {
            mRect.top = 0;
            mRect.bottom = (int) (mViewHeight / mScale);
        }
        invalidate();
        return false;
    }

    /**
     * 第八步：处理惯性事件
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(0, mRect.top, 0, (int) - velocityY, 0, 0, 0,
                mImageHeight - (int) (mViewHeight / mScale));
        return false;
    }

    /**
     * 第九步：处理计算结果
     */
    @Override
    public void computeScroll() {
        if (mScroller.isFinished()) {
            return;
        }
        if (mScroller.computeScrollOffset()) {
            mRect.top = mScroller.getCurrY();
            mRect.bottom = mRect.top + (int) (mViewHeight / mScale);
            invalidate();
        }
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }
}
