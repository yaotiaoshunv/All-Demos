package com.lzw.disklrucachedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvDisplayValue = findViewById(R.id.tv_display_value);

        Button btnAddValue = findViewById(R.id.add_value);
        btnAddValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDiskCache("data1", "我是第一条缓存数据");
            }
        });

        Button btnGetValue = findViewById(R.id.get_value);
        btnGetValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = getDiskCache("data1");
                tvDisplayValue.setText(value);
            }
        });
    }

    /**
     * 添加一条缓存
     *
     * @param key
     * @param value
     */
    public void addDiskCache(String key, String value) {
        try {
            DiskLruCache diskLruCache = DiskLruCache.open(getCacheDir(), 1, 1, 5 * 1024 * 1024);
            DiskLruCache.Editor editor = diskLruCache.edit(key);
            editor.newOutputStream(0).write(value.getBytes());
            editor.commit();
            diskLruCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public String getDiskCache(String key) {
        try {
            DiskLruCache diskLruCache = DiskLruCache.open(getCacheDir(), 1, 1, 5 * 1024 * 1024);
            String value = diskLruCache.get(key).getString(0);
            diskLruCache.close();

            return value;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "获取缓存失败";
    }
}