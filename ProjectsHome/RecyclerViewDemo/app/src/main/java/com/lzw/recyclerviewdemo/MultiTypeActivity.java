package com.lzw.recyclerviewdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzw.recyclerviewdemo.adapters.MultiTypeAdapter;
import com.lzw.recyclerviewdemo.beans.MultiTypeBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Li Zongwei
 */
public class MultiTypeActivity extends AppCompatActivity {
    private RecyclerView rvMultiType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);

        rvMultiType = findViewById(R.id.rv_multi_type);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMultiType.setLayoutManager(linearLayoutManager);

        Random random = new Random();
        List<MultiTypeBean> data = new ArrayList<>();
        for (int i = 0; i < Datas.icons.length; i++) {
            MultiTypeBean item = new MultiTypeBean();
            item.pic = Datas.icons[i];
            item.type = random.nextInt(3);
            data.add(item);
        }

        MultiTypeAdapter adapter = new MultiTypeAdapter(data);
        rvMultiType.setAdapter(adapter);
    }
}