package com.lzw.idiomsdictionaryproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etSearch = findViewById(R.id.et_search);
        tvDisplay = findViewById(R.id.tv_display);

        Button btnSearch = findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchContent = etSearch.getText().toString();
                //TODO 通过api获取数据
                getIdiomInfo(searchContent);
            }
        });
    }

    /**
     * 极速 成语大全api
     */
    String jiSuAppKey = "5c651ac908fd604d";
    String searchDetailUrl = "https://api.jisuapi.com/chengyu/detail?appkey=" + jiSuAppKey + "&chengyu=";

    private void getIdiomInfo(String idiom) {
        HttpUtil.sendOkHttpRequest(searchDetailUrl + idiom, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String json = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvDisplay.setText(json);
                    }
                });
            }
        });
    }
}