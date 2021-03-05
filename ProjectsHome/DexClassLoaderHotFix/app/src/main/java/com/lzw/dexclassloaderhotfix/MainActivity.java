package com.lzw.dexclassloaderhotfix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    ISay mSay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSay = findViewById(R.id.btn_say);
        btnSay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 获取hotfix的jar包文件
                File jarFile = new File(Environment.getExternalStorageDirectory().getPath()
                        + File.separator + "hotfix.jar");

                if (!jarFile.exists()) {
                    mSay = new SayException();
                    Toast.makeText(MainActivity.this, mSay.saySomething(), Toast.LENGTH_LONG).show();
                } else {
                    //只要有读写权限的路径均可
                    DexClassLoader dexClassLoader = new DexClassLoader(jarFile.getAbsolutePath(),
                            getExternalCacheDir().getAbsolutePath(), null, getClassLoader());
                    try {
                        Class clazz = dexClassLoader.loadClass("com.lzw.dexclassloaderhotfix.SayHotFix");
                        // 强转成 ISay 注意 ISay 的包名需要和hotfix jar包中的一致
                        ISay iSay = (ISay) clazz.newInstance();
                        Toast.makeText(MainActivity.this, iSay.saySomething(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}