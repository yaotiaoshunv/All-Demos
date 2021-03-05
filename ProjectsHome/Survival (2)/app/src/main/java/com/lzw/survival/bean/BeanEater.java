package com.lzw.survival.bean;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.lzw.survival.R;
import com.lzw.survival.view.GameMapView;

/**
 * 吃豆人实体类
 */
public class BeanEater {
    private static final String TAG = "BeanEater";

    /**
     * 位置
     */
    private Location mLocation;

    /**
     * 运动方向
     */
    private int direction = 5;
    public static final int DIRECTION_TOP = 0;
    public static final int DIRECTION_BOTTOM = 1;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_RIGHT = 3;

    /**
     * 吃豆人对应的图片
     */
    private ImageView iv_bean_eater;

    private Animator animator;

    /**
     * 建筑物（障碍物）坐标
     */
    private int[][] buildings;

    /**
     * 是否被阻碍（前方有障碍）
     */
    private boolean isBlocked;

    /**
     * beanEater的角色类型，有玩家，和电脑（幽灵ghost）
     */
    private int roleType;
    public static final int ROLE_TYPE_PLAYER = R.mipmap.bean_eater;
    public static final int ROLE_TYPE_GHOST = R.mipmap.ic_launcher;

    public BeanEater(ImageView iv_bean_eater, int roleType, GameMapView gmv) {
        this.iv_bean_eater = iv_bean_eater;
        this.roleType = roleType;

        buildings = gmv.getBuildings();
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public ImageView getIv_bean_eater() {
        return iv_bean_eater;
    }

    public void setIv_bean_eater(ImageView iv_bean_eater) {
        this.iv_bean_eater = iv_bean_eater;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location mLocation) {
        this.mLocation = mLocation;
    }

    public static class Location {
        public float x;
        public float y;

        public Location(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }


    /**
     * 计算吃豆人的移动方向
     *
     * @param moveDistanceX
     * @param moveDistanceY
     * @return
     */
    public int getMoveDirection(float moveDistanceX, float moveDistanceY) {
        int direction;
        //首先判断是上下移动还是左右移动，根据移动的距离确定

        if (Math.abs(moveDistanceX) >= Math.abs(moveDistanceY)) {
            //左右移动
            direction = moveDistanceX > 0 ? BeanEater.DIRECTION_RIGHT : BeanEater.DIRECTION_LEFT;
        } else {
            //上下移动
            direction = moveDistanceY > 0 ? BeanEater.DIRECTION_BOTTOM : BeanEater.DIRECTION_TOP;
        }

        return direction;
    }

    /**
     * 移动BeanEater。
     *
     * @param beanNewDirection
     */
    public void moveBeanEater(final int beanNewDirection) {
        //限制移动范围，只能在格子中移动，在格子中间是不能移动的。
        if ((beanNewDirection == BeanEater.DIRECTION_LEFT || beanNewDirection == BeanEater.DIRECTION_RIGHT) && iv_bean_eater.getY() % 100 != 0) {
            return;
        } else if ((beanNewDirection == BeanEater.DIRECTION_TOP || beanNewDirection == BeanEater.DIRECTION_BOTTOM) && iv_bean_eater.getX() % 100 != 0) {
            return;
        }

        //判断新的方向，如果和当前的一致，那么不做处理
        if (getDirection() == beanNewDirection) {
            return;
        } else {
            //方向不同，改变方向
            setDirection(beanNewDirection);
            if (animator != null && animator.isRunning()) {
                animator.cancel();
            }
        }

        //要移动到的位置，根据移动方向去计算
        float moveDistance;
        moveDistance = calculateBeanTargetXY(getDirection());

        switch (getDirection()) {
            case BeanEater.DIRECTION_TOP:
            case BeanEater.DIRECTION_BOTTOM:
                animator = ObjectAnimator.ofFloat(iv_bean_eater, "Y",
                        iv_bean_eater.getY(), moveDistance);
                break;
            case BeanEater.DIRECTION_LEFT:
            case BeanEater.DIRECTION_RIGHT:
                animator = ObjectAnimator.ofFloat(iv_bean_eater, "X",
                        iv_bean_eater.getX(), moveDistance);
                break;
            default:
                break;
        }
        animator.setDuration(1000L);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.v(TAG, "onAnimationEnd");
                //结束的时候，判断是不是电脑，是电脑的话继续运行
                if (roleType == ROLE_TYPE_GHOST) {
                    Log.v(TAG, "direction" + direction);

                    int newDirection = getARandomDirection(direction);
                    Log.v(TAG, "new_direction" + newDirection);

                    moveBeanEater(newDirection);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.v(TAG, "onAnimationEnd");
                //结束的时候，判断是不是电脑，是电脑的话继续运行
                if (roleType == ROLE_TYPE_GHOST) {
                    Log.v(TAG, "direction" + direction);

                    int newDirection = getARandomDirection(direction);
                    Log.v(TAG, "new_direction" + newDirection);

                    moveBeanEater(newDirection);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 计算吃豆人可以运行到的位置，即到这个位置就要停下来
     */
    private float calculateBeanTargetXY(int beanDirection) {
        ImageView iv_bean_eater = this.getIv_bean_eater();
        float beanTargetX = 0;
//        Log.v(TAG, "iv_bean_eater.getX()1" + iv_bean_eater.getX() + "    iv_bean_eater.getY()1" + iv_bean_eater.getY());

        float beanY = iv_bean_eater.getY();
        int row = (int) beanY / 100;

        //先计算左右移动遇到障碍物
        if (beanDirection == BeanEater.DIRECTION_RIGHT) {
            float distance = 0;

            for (int i = 0; i < buildings.length; i++) {
                int[] building = buildings[i];
                int x = building[0];
                int y = building[1];

//                Log.v(TAG, "row" + row + "   x  " + x + "   y  " + y);
                //找到在指定行的障碍物
                if (x == row && y * 100 > iv_bean_eater.getX()) {
                    //同一行的，比较与吃豆人的距离，取距离最小的
//                    Log.v(TAG, "row" + row + "   x  " + x + "   y  " + y);

                    distance = y * 100;
                    break;
                }
            }
            if (distance == 0) {
                isBlocked = true;
                beanTargetX = 1000 - iv_bean_eater.getWidth();
            } else {
                beanTargetX = distance - iv_bean_eater.getWidth();
            }
            return beanTargetX;
        } else if (beanDirection == BeanEater.DIRECTION_LEFT) {
            //TODO 计算左移停止点
            float distance = 0;
            for (int i = 0; i < buildings.length; i++) {
                int[] building = buildings[i];
                int x = building[0];
                int y = building[1];

                //找到在指定行,并且在吃豆人左边的所有障碍物
                if (x == row && y * 100 < iv_bean_eater.getX()) {
                    //同一行的，比较与吃豆人的距离，取距离最小的
//                    Log.v(TAG, "row" + row + "   x  " + x + "   y  " + y);

                    distance = y * 100;
                }
            }
            if (distance != 0) {
                beanTargetX = distance + 100;
            } else {
                isBlocked = true;
            }
            return beanTargetX;
        }

        float beanX = iv_bean_eater.getX();
        int cul = (int) beanX / 100;

        //先计算上下移动遇到障碍物
        if (beanDirection == BeanEater.DIRECTION_TOP) {
            //TODO 计算上移停止点
            float distance = 0;

            for (int i = 0; i < buildings.length; i++) {
                int[] building = buildings[i];
                int x = building[0];
                int y = building[1];

//                Log.v(TAG, "cul" + cul + "   x  " + x + "   y  " + y);
                //找到在指定行的障碍物
                if (y == cul && x * 100 < iv_bean_eater.getY()) {
                    distance = x * 100;
                }
            }
            if (distance != 0) {
                beanTargetX = distance + 100;
            } else {
                isBlocked = true;
            }
            return beanTargetX;
        } else if (beanDirection == BeanEater.DIRECTION_BOTTOM) {
            float distance = 0;

            for (int i = 0; i < buildings.length; i++) {
                int[] building = buildings[i];
                int x = building[0];
                int y = building[1];

//                Log.v(TAG, "cul" + cul + "   x  " + x + "   y  " + y);
                //找到在指定行的障碍物
                if (y == cul && x * 100 > iv_bean_eater.getY()) {
                    distance = x * 100;
                    break;
                }
            }
            if (distance == 0) {
                isBlocked = true;
                beanTargetX = 1500 - iv_bean_eater.getHeight();
            } else {
                beanTargetX = distance - iv_bean_eater.getWidth();
            }
            return beanTargetX;
        }

        return beanTargetX;
    }

    /**
     * 让BeanEater自动移动
     */
    public void autoMove() {
        moveBeanEater(getARandomDirection(direction));
    }

    /**
     * 获取一个随机的方向，如果新的方向和当前一样，重新获取
     *
     * @param direction 当前的方向
     * @return 新的方向
     */
    private int getARandomDirection(int direction) {
        int newDirection = (int) (Math.random() * 4);
        Log.v(TAG, "getARandomDirection" + newDirection);

        return newDirection == direction ? getARandomDirection(direction) : newDirection;
    }
}
