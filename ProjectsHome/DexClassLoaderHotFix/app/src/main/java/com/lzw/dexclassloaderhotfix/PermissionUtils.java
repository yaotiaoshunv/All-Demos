package com.lzw.dexclassloaderhotfix;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by Li Zongwei on 2020/12/30.
 **/
public class PermissionUtils {
    public static final int REQUEST_CODE = 1111;
    /**
     * 录音需要的权限
     */
    public static String[] permissionsRecord = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 读写权限
     */
    public static String[] permissionsREAD = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    /**
     * 读写权限+相机权限
     */
    public static String[] permissionsREADAndCamera = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    /**
     * 定位权限
     */
    public static String[] permissionsPositioning = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    /**
     * 拨打电话权限
     */
    public static String[] permissionsCallPhone = {
            Manifest.permission.CALL_PHONE,
    };

    private String[] mPermissions;

    private static Activity mActivity;

    public PermissionUtils(Activity activity) {
        mActivity = activity;
    }

    public static PermissionUtils from(Activity activity) {
        return new PermissionUtils(activity);
    }

    public boolean setPermissions(String[] permissions) {
        this.mPermissions = permissions;
        return lacksPermissions();
    }

    /**
     * 判断权限集合
     * permissions 权限数组
     * return true-表示权限已开启  false-表示没有改权限
     */
    public boolean lacksPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : mPermissions) {
                if (lacksPermission(permission)) {
                    ActivityCompat.requestPermissions(mActivity, mPermissions, REQUEST_CODE);
                    forResult();
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     * 判断是否缺少权限
     */
    public boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mActivity, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    public void forResult() {
        ActivityCompat.requestPermissions(mActivity, mPermissions, REQUEST_CODE);
    }

}
