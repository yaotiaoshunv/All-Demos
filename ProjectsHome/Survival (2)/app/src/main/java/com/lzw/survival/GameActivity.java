package com.lzw.survival;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lzw.survival.bean.BeanEater;
import com.lzw.survival.view.GameMapView;

/**
 * 游戏界面
 */
public class GameActivity extends BaseActivity {
    private static final String TAG = "GameActivity";

    /**
     * 游戏模式：
     * 1、GAME_MODE_SINGLE：单人模式
     * 2、GAME_MODE_Multi：多人模式
     * <p>
     * 然后再分为：经典战场和迷雾战场，先做经典
     */
    private static final String KEY_GAME_MODE = "GameMode";
    private static final String GAME_MODE_SINGLE = "SingleGame";
    private static final String GAME_MODE_Multi = "MultiGame";

    private RelativeLayout rl_root;

    private GameMapView gmv;

    private BeanEater mBeanEater_1;
    private BeanEater mBeanEater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        rl_root = findViewById(R.id.rl_root);

        gmv = findViewById(R.id.gmv_main);

        mBeanEater_1 = new BeanEater(createBeanEaterImage(BeanEater.ROLE_TYPE_PLAYER, new BeanEater.Location(0, 0)),BeanEater.ROLE_TYPE_PLAYER, gmv);

        mBeanEater = new BeanEater(createBeanEaterImage(BeanEater.ROLE_TYPE_GHOST, new BeanEater.Location(900, 0)), BeanEater.ROLE_TYPE_GHOST,gmv);
        mBeanEater.autoMove();
    }

    /**
     * 在地图上创建吃豆人
     */
    private ImageView createBeanEaterImage(int roleType, BeanEater.Location location) {
        ImageView iv_bean_eater = new ImageView(this);
        iv_bean_eater.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        iv_bean_eater.setImageResource(roleType);
        iv_bean_eater.getLayoutParams().width = 100;
        iv_bean_eater.getLayoutParams().height = 100;

        iv_bean_eater.setX(location.x);
        iv_bean_eater.setY(location.y);

        rl_root.addView(iv_bean_eater);

        return iv_bean_eater;
    }

    /**
     * 手指按下按下时的 x
     */
    private float downX;
    /**
     * 手指按下按下时的 y
     */
    private float downY;

    /**
     * 手指移动距离 X
     */
    private float moveDistanceX;
    /**
     * 手指移动距离 Y
     */
    private float moveDistanceY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                moveDistanceX = event.getX() - downX;
                moveDistanceY = event.getY() - downY;

                downX = event.getX();
                downY = event.getY();

                int direction = mBeanEater_1.getMoveDirection(moveDistanceX, moveDistanceY);

                mBeanEater_1.moveBeanEater(direction);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }
}