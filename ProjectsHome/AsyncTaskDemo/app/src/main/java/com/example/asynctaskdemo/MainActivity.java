package com.example.asynctaskdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et;
    private TextView tv;
    private Button btnStartCountdown,btnStartSecondActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);
        btnStartCountdown = findViewById(R.id.btnStartCountdown);

        btnStartCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = 0;
                try {
                    time = Integer.parseInt(et.getText().toString());
                }catch (Exception e){
                    Toast.makeText(MainActivity.this,"请输入正确的数字",Toast.LENGTH_SHORT).show();
                    return;
                }
                new MyTask().execute(time);
            }
        });

        btnStartSecondActivity = findViewById(R.id.btn_start_second_activity);
        btnStartSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
    }

    class MyTask extends AsyncTask<Integer,Integer,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for (int i = integers[0]; i > 0; i--){
                try {
                    publishProgress(i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "计时结束";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tv.setText(values[0]+"");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv.setText(s);
        }
    }
}