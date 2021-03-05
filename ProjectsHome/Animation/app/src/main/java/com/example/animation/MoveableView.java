package com.example.animation;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * 点击后跟随手指移动
 */
public class MoveableView extends View {
    public MoveableView(Context context) {
        super(context);
    }

    public MoveableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float mLastX;
    private float mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();

        float deltaX = x - mLastX;
        float deltaY = y - mLastY;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(getContext(),"onAnimationStart",Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_MOVE:

                setTranslationX(getTranslationX() + deltaX);
                setTranslationY(getTranslationY() + deltaY);
                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;

        Log.e("llllllllll", mLastX + "    " + x + "   " + mLastY + "    " + y + "");
        return true;
    }

}
