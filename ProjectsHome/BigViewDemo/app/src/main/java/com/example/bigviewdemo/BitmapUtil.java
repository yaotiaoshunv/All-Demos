package com.example.bigviewdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

public class BitmapUtil {

    public static Bitmap ratio(InputStream is,int pixelW,int pixelH) {
        BitmapFactory.Options newOptions = new BitmapFactory.Options();
        newOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeStream(is, null, newOptions);

        int originalW = newOptions.outWidth;
        int originalH = newOptions.outHeight;

        newOptions.inSampleSize = getSampleSize(originalW, originalH, pixelW, pixelH);
        newOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, newOptions);
    }

    private static int getSampleSize(int originalW, int originalH, int pixelW, int pixelH) {
        int simpleSize = 1;
        if (originalW > originalH && originalW > pixelW) {
            simpleSize = originalW / pixelW;
        } else if (originalW < originalH && originalH > pixelH) {
            simpleSize = originalH / pixelH;
        }
        if (simpleSize <= 0) {
            simpleSize = 1;
        }
        return simpleSize;
    }
}
