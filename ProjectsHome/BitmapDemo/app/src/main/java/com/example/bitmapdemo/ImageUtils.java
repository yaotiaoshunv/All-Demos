package com.example.bitmapdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtils {

    public static Bitmap resizeBitmap(Context context, int id, int maxW, int maxH, boolean hasAlpha) {
        Resources resources = context.getResources();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //只解码出相关的参数
        BitmapFactory.decodeResource(resources, id, options);
        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;
        //设置缩放系数，只能取2的n次方
        options.inSampleSize = calculateInSampleSize(originalWidth, originalHeight, maxW, maxH);
        if (!hasAlpha) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, id, options);
    }

    /**
     * 计算 inSampleSize：缩放系数，只能取2的n次方
     *
     * @param originalWidth     Bitmap初始宽度
     * @param originalHeight    Bitmap初始高度
     * @param maxW              目标宽度
     * @param maxH              目标高度
     * @return
     */
    private static int calculateInSampleSize(int originalWidth, int originalHeight, int maxW, int maxH) {
        int inSampleSize = 1;
        if (originalWidth > maxW && originalHeight > maxH) {
            inSampleSize = 2;
            while (originalWidth / inSampleSize > maxW && originalHeight / inSampleSize > maxH) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
