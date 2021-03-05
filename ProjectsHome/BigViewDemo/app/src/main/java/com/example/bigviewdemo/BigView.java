package com.example.bigviewdemo;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.InputStream;

/**
 * 需求：大长图加载，要保证性能、内存占用要低，可以上下滑动、并且有惯性效果
 * <p>
 * 再此需求下，选择自定义一个大图显示View：BigView。
 */
public class BigView extends View {
    /**
     * 用来对传入的Bitmap进行配置
     */
    private final BitmapFactory.Options mOptions;

    public BigView(Context context) {
        this(context, null);
    }

    public BigView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //第一步：初始化需要用到的成员变量

        mOptions = new BitmapFactory.Options();

    }

    /**
     * 第二步：获取并设置要显示的图片
     */
    public void setImage(InputStream is){
        BitmapFactory.decodeStream(is,null,mOptions);

        mOptions.inJustDecodeBounds = true;
    }
}
