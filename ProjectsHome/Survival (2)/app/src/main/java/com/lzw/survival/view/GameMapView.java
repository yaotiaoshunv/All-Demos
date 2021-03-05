package com.lzw.survival.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author Li Zongwei
 * @date 2020/11/6
 **/
public class GameMapView extends View {

    private static final String TAG = "GameMapView";
    private Context mContext;

    /**
     * 每个格子（正方形）的大小
     */
    private int areaSize = 100;

    /**
     * 建筑物数量
     */
    private int buildingNum = 30;

    private Paint mPaint;

    private Rect mRect;

    /**
     * 地图高
     */
    private int mapHeight;
    /**
     * 地图宽
     */
    private int mapWidth;

    /**
     * 垂直格子（方块）数量
     */
    private static final int vBlockCount = 15;
    /**
     * 水平格子数量
     */
    private static final int hBlockCount = 10;

    /**
     * 建筑物数组
     * {0，5} ： 对应地图的第0行 ，第5列
     * {1,1} : 对应地图的第1行 ，第1列
     */
    private int[][] buildings = {
            {0, 4}, {0, 5},
            {1, 1}, {1, 2}, {1, 7}, {1, 8},
            {2, 4}, {2, 5},
            {3, 0}, {3, 1}, {3, 8}, {3, 9},
            {4, 3}, {4, 6},
            {5, 1}, {5, 2}, {5, 7}, {5, 8},
            {6, 4}, {6, 5},
            {7, 0}, {7, 2}, {7, 7}, {7, 9},
            {8, 4}, {8, 5},
            {9, 1}, {9, 3}, {9, 6}, {9, 8},
            {10, 1}, {10, 8},
            {11, 3}, {11, 6},
            {12, 0}, {12, 2}, {12, 4}, {12, 5}, {12, 7}, {12, 9},
            {13, 0}, {13, 9},
            {14, 2}, {14, 3}, {14, 6}, {14, 7}
    };

    /**
     * 建筑物（目前都是障碍物），存储每个建筑物对应的像素点
     * {0，0}：第0行，第0列
     * {100，300}：第1行，第3列
     */
    private int[][] buildingsLocation = new int[2][buildings.length];

    /**
     * 所有格子对应的数组，以后新建地图的时候，把不要的删了就行
     */
    private int[][] buildingsDemo = {
            {0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}, {0, 8}, {0, 9},
            {1, 0}, {1, 1}, {1, 2}, {1, 3}, {1, 4}, {1, 5}, {1, 6}, {1, 7}, {1, 8}, {1, 9},
            {2, 0}, {2, 1}, {2, 2}, {2, 3}, {2, 4}, {2, 5}, {2, 6}, {2, 7}, {2, 8}, {2, 9},
            {3, 0}, {3, 1}, {3, 2}, {3, 3}, {3, 4}, {3, 5}, {3, 6}, {3, 7}, {3, 8}, {3, 9},
            {4, 0}, {4, 1}, {4, 2}, {4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8}, {4, 9},
            {5, 0}, {5, 1}, {5, 2}, {5, 3}, {5, 4}, {5, 5}, {5, 6}, {5, 7}, {5, 8}, {5, 9},
            {6, 0}, {6, 1}, {6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {6, 7}, {6, 8}, {6, 9},
            {7, 0}, {7, 1}, {7, 2}, {7, 3}, {7, 4}, {7, 5}, {7, 6}, {7, 7}, {7, 8}, {7, 9},
            {8, 0}, {8, 1}, {8, 2}, {8, 3}, {8, 4}, {8, 5}, {8, 6}, {8, 7}, {8, 8}, {8, 9},
            {9, 0}, {9, 1}, {9, 2}, {9, 3}, {9, 4}, {9, 5}, {9, 6}, {9, 7}, {9, 8}, {9, 9},
            {10, 0}, {10, 1}, {10, 2}, {10, 3}, {10, 4}, {10, 5}, {10, 6}, {10, 7}, {10, 8}, {10, 9},
            {11, 0}, {11, 1}, {11, 2}, {11, 3}, {11, 4}, {11, 5}, {11, 6}, {11, 7}, {11, 8}, {11, 9},
            {12, 0}, {12, 1}, {12, 2}, {12, 3}, {12, 4}, {12, 5}, {12, 6}, {12, 7}, {12, 8}, {12, 9},
            {13, 0}, {13, 1}, {13, 2}, {13, 3}, {13, 4}, {13, 5}, {13, 6}, {13, 7}, {13, 8}, {13, 9},
            {14, 0}, {14, 1}, {14, 2}, {14, 3}, {14, 4}, {14, 5}, {14, 6}, {14, 7}, {14, 8}, {14, 9},
    };

    public GameMapView(Context context) {
        this(context, null);
    }

    public GameMapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);

        mRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mapHeight = getMeasuredHeight();
        mapWidth = getMeasuredWidth();

        mapHeight = areaSize * vBlockCount;
        mapWidth = areaSize * hBlockCount;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawMapLine(canvas);

        drawBuilding(canvas, buildings);
    }

    /**
     * 绘制地图线
     *
     * @param canvas
     */
    private void drawMapLine(Canvas canvas) {
        // 画地图线，后面可以考虑去掉，然后用更美观的效果代替
        for (int i = 0; i < vBlockCount + 1; i++) {
            //水平线
            canvas.drawLine(0, i * areaSize, mapWidth, i * areaSize, mPaint);
        }

        for (int i = 0; i < hBlockCount + 1; i++) {
            //垂直线
            canvas.drawLine(i * areaSize, 0, i * areaSize, mapHeight, mPaint);
        }
    }

    /**
     * 绘制随机建筑物
     *
     * @param canvas
     */
    private void drawRandomBuilding(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
//        canvas.drawCircle((int) resultX * 100 - 50, (int) resultY * 100 + 50, 50, mPaint);

        //生成一组随机数，对应像素点，然后在像素点落在的位置，画建筑物
        for (int i = 0; i < buildingNum; i++) {
            //竖格
            double resultY = Math.random() * vBlockCount;
            Log.v(TAG, "随机数：" + resultY);

            //横格
            double resultX = Math.random() * hBlockCount;
            Log.v(TAG, "随机数：" + resultX);

            mRect.top = (int) resultX * areaSize;
            mRect.bottom = (int) (resultX + 1) * areaSize;
            mRect.left = (int) resultY * areaSize;
            mRect.right = (int) (resultY + 1) * areaSize;

            canvas.drawRect(mRect, mPaint);
        }

    }

    /**
     * 绘制特定地图
     *
     * @param canvas
     */
    private void drawBuilding(Canvas canvas, int[][] buildings) {
        mPaint.setColor(Color.BLUE);

        for (int i = 0; i < buildings.length; i++) {
            int[] buildingsLocation = buildings[i];

            int x = buildingsLocation[0];
            int y = buildingsLocation[1];

            mRect.top = x * areaSize;
            mRect.bottom = (x + 1) * areaSize;

            mRect.left = y * areaSize;
            mRect.right = (y + 1) * areaSize;

            canvas.drawRect(mRect, mPaint);
        }
    }

    /**
     * 用于获取建筑物的位置
     * @return
     */
    public int[][] getBuildings() {
        return buildings;
    }
}
