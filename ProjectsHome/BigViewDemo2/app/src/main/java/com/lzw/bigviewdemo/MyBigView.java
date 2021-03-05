package com.lzw.bigviewdemo;

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

import androidx.annotation.LongDef;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Li Zongwei
 * @date 2020/9/16
 **/
public class MyBigView extends View implements GestureDetector.OnGestureListener, View.OnTouchListener {
    private static final String TAG = "MyBigView";

    /**
     * 用于分块加载图片
     */
    private final Rect mRect;

    /**
     * 用于对Bitmap进行配置，比如设置复用
     */
    private final BitmapFactory.Options mOptions;

    /**
     * 用于手势支持，因为大长图的显示需要上下滑动。要和Scroller配合使用
     */
    private final GestureDetector mGestureDetector;

    /**
     * 用于滑动
     */
    private final Scroller mScroller;

    /**
     * 图片原始高度
     */
    private int mImageOriginalHeight;
    /**
     * 图片原始宽度
     */
    private int mImageOriginalWidth;

    /**
     * 用于区域解码
     */
    private BitmapRegionDecoder mDecoder;

    /**
     * 此控件的宽度
     */
    private int mViewWidth;
    /**
     * 此控件的高度
     */
    private int mViewHeight;

    /**
     * 缩放因子
     */
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

        // 第一步：设置bigView需要的成员变量
        // 1、使用Rect进行分块加载
        mRect = new Rect();
        // 2、内存复用
        mOptions = new BitmapFactory.Options();
        // 3、手势支持
        mGestureDetector = new GestureDetector(context, this);
        // 4、滑动类
        mScroller = new Scroller(context);

        setOnTouchListener(this);
    }

    // 第二步：设置图片
    public void setImage(InputStream is) {
        // 获取图片宽高
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, mOptions);
        mImageOriginalWidth = mOptions.outWidth;
        mImageOriginalHeight = mOptions.outHeight;

        // 开启内存复用
        mOptions.inMutable = true;
        // 设置格式RGB565
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        mOptions.inJustDecodeBounds = false;

        // 区域解码器
        try {
            mDecoder = BitmapRegionDecoder.newInstance(is, false);
            Log.d(TAG, "mDecoder width : " + mDecoder.getWidth() + " height : " + mDecoder.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //通知View进行测量/绘制
        requestLayout();
    }

    // 第三步：测量图片
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
        Log.d(TAG, "mViewWidth " + mViewWidth + " \n mViewHeight" + mViewHeight);

        // 计算缩放因子
        mScale = mViewWidth / (float) mImageOriginalWidth;
        Log.d(TAG, "mScale" + mScale);

        calculateRect();

        Log.d(TAG, "mRect.bottom " + mRect.bottom);
    }

    private void calculateRect() {
        // 确定图片加载的区域
        mRect.top = 0;
        mRect.left = 0;
        mRect.right = mImageOriginalWidth;
        mRect.bottom = (int) (mViewHeight / mScale);
    }

    // 第四步：开始画出具体的内容
    private Matrix matrix;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDecoder == null) {
            return;
        }

        //复用内存
        mOptions.inBitmap = mBitmap;

        Log.d(TAG, "mRect top" + mRect.top+"\n"+
                "mRect bottom" + mRect.bottom+"\n"+
                "mRect left" + mRect.left+"\n"+
                "mRect right" + mRect.right);

        mBitmap = mDecoder.decodeRegion(mRect, mOptions);

        Log.d(TAG, "bitmap size" + mBitmap.getByteCount());
        Log.d(TAG, "bitmap width" + mBitmap.getWidth() + "bitmap height" + mBitmap.getHeight());

        // 得到一个矩阵进行缩放
        matrix = new Matrix();
        matrix.setScale(mScale, mScale);

        canvas.drawBitmap(mBitmap, matrix, null);
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
        // 如果滑动没有停止，强制停止
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
        //接收后续事件
        return true;
    }

    /**
     * 第7步：处理滑动事件
     *
     * @param e1  开始事件
     * @param e2  即时事件
     * @param distanceX
     * @param distanceY
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //上下移动的时候，直接改变Rect显示区域
        mRect.offset(0, (int) distanceY);
        // 判断到达顶部/底部的情况
        if (mRect.bottom > mImageOriginalHeight){
            mRect.bottom = mImageOriginalHeight;
            mRect.top = mImageOriginalHeight- (int) ((mViewHeight/mScale));
        }
        if (mRect.top < 0){
            mRect.top = 0;
            Log.d(TAG, "new mViewHeight" + mViewHeight);
            mRect.bottom =  (int) (mViewHeight/mScale);
            Log.d(TAG, "mRect.bottom" + mRect.bottom);
        }
        invalidate();
        return false;
    }

    /**
     * 第八步：处理惯性问题
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("velocityY","velocityY"+velocityY+"");
        mScroller.fling(0,mRect.top,0, (int) velocityY,0,0,0,
                mImageOriginalHeight - (int) (mViewHeight/mScale));
        return false;
    }

    /**
     * 第九步：处理计算结果
     */
    @Override
    public void computeScroll() {
        if (mScroller.isFinished()){
            return;
        }
        if (mScroller.computeScrollOffset()){
            mRect.top = mScroller.getCurrY();
            mRect.bottom = (int) (mRect.top + mViewHeight/mScale);
            invalidate();
        }
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

}
