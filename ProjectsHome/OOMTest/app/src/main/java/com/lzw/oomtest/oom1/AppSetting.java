package com.lzw.oomtest.oom1;

import android.content.Context;

/**
 * Created by Li Zongwei on 2020/11/30.
 **/
public class AppSetting {
    private static AppSetting mInstance;
    private Context mContext;

    private AppSetting(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static AppSetting getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppSetting(context);
        }
        return mInstance;
    }
}
