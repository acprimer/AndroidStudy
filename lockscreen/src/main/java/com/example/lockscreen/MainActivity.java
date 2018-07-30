package com.example.lockscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "HandlerTest";
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.anim);

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();

                // handler1和handler2是等价的
                // 构造Handler的时候不传入Looper，默认就是使用当前线程的Looper
                Handler handler1 = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        // 在子线程中执行
                        return false;
                    }
                });
                Handler handler2 = new Handler(Looper.myLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        // 在子线程中执行
                        return false;
                    }
                });
                Handler handler3 = new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        // 在UI线程中执行
                        return false;
                    }
                });

                Looper.loop();
            }
        }.start();
    }

    public void startAnimActivity(View view) {
        Intent intent = new Intent(this, SpringAnimActivity.class);
        startActivity(intent);
    }

    public void startLockActivity(View view) {
        Intent intent = new Intent(this, LockScreenActivity.class);
        startActivity(intent);
    }
}
