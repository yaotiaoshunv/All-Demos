package com.example.asynctaskdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * @author Lzw
 */
public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1,btn2;
    private ProgressBar pro1,pro2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btn1 = findViewById(R.id.bt1);
        btn2 = findViewById(R.id.bt2);
        pro1 = findViewById(R.id.pr1);
        pro2 = findViewById(R.id.pr2);
        btn1 .setOnClickListener(this);
        btn2 .setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt1:
                DownloadTask d1 = new DownloadTask();
//                d1.execute(2);
                d1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,1);
                btn1.setText("正在下载");
                btn1.setEnabled(false);
                break;
            case R.id.bt2:
                DownloadTask d2 = new DownloadTask();
//                d2.execute(2);
                d2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,2);
                btn2.setText("正在下载");
                btn2.setEnabled(false);
                break;
            default:
                break;
        }
    }

    private class DownloadTask extends AsyncTask<Integer,Integer,Integer> {
        @Override
        protected Integer doInBackground(Integer... integers) {
            for (int i = 1; i <= 10; i++){
                try {
                    Thread.sleep(1000);
                    publishProgress(i * 10,integers[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return integers[0];
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            switch (values[1]){
                case 1:
                    pro1.setProgress(values[0]);
                    break;
                case 2:
                    pro2.setProgress(values[0]);
                    break;
                default:
                    break;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            switch (integer){
                case 1:
                    btn1.setText("下载完成");
                    btn1.setEnabled(false);
                    break;
                case 2:
                    btn2.setText("下载完成");
                    btn2.setEnabled(false);
                    break;
                default:
                    break;
            }
        }
    }
}