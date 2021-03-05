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

public class EventBusSendActivity extends AppCompatActivity {

    private Button btn_send_main_activity,btn_receive_sticky;
    private TextView tv_send_activity_result;

    private boolean isFirst = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_send);

        btn_send_main_activity = findViewById(R.id.btn_send_main_activity);
        btn_receive_sticky = findViewById(R.id.btn_receive_sticky);

        //主线程发送数据
        btn_send_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //4、发送消息
                EventBus.getDefault().post(new MessageEvent("主线程发送过来的数据"));

                finish();
            }
        });

        btn_receive_sticky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFirst){
                    EventBus.getDefault().register(EventBusSendActivity.this);
                    isFirst = false;
                }
            }
        });

        tv_send_activity_result = findViewById(R.id.tv_send_activity_result);
    }

    //接收粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEvent(StickyEvent stickyEvent){
        tv_send_activity_result.setText(stickyEvent.msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(EventBusSendActivity.class);
    }
}