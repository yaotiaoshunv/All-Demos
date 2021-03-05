package com.example.eventdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eventdemo.event.MessageEvent;
import com.example.eventdemo.event.StickyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusActivity extends AppCompatActivity {

    private Button btn_start_send_activity,btn_send_sticky;
    private TextView tv_main_activity_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1、注册
        EventBus.getDefault().register(this);

        btn_start_send_activity = findViewById(R.id.btn_start_send_activity);
        btn_send_sticky = findViewById(R.id.btn_send_sticky);

        //跳转到发送页面
        btn_start_send_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventBusActivity.this,EventBusSendActivity.class);
                startActivity(intent);
            }
        });

        //发送粘性事件到发送页面
        btn_send_sticky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送粘性事件
                EventBus.getDefault().postSticky(new StickyEvent("我是粘性事件"));
                Intent intent = new Intent(EventBusActivity.this,EventBusSendActivity.class);
                startActivity(intent);
            }
        });

        //显示结果
        tv_main_activity_result = findViewById(R.id.tv_main_activity_result);
    }

    //5、接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent){
        //显示接收的消息
        tv_main_activity_result.setText(messageEvent.name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //2、解注册
        EventBus.getDefault().unregister(EventBusSendActivity.class);
    }
}