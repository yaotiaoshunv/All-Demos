package com.lzw.survival;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lzw.survival.bean.BeanEater;
import com.lzw.survival.net.ConnectServer;

/**
 * 游戏开始界面
 */
public class MainActivity extends BaseActivity {

    private Button btn_single_player_game, btn_multi_player_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        connectServer();
    }

    private void initViews() {
        btn_single_player_game = findViewById(R.id.btn_single_player_game);
        btn_single_player_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        btn_multi_player_game = findViewById(R.id.btn_multi_player_game);
        btn_multi_player_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                connection.sendDataToServer("你好，现在的时间是" + new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()));
            }
        });
    }

    private ConnectServer connection;

    /**
     * 连接服务器
     */
    private void connectServer() {
        connection = new ConnectServer();
        connection.getConn();
    }

    /**
     * @param beanEater
     */
    private void onEvent(BeanEater beanEater) {

    }
}