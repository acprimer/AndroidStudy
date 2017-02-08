package com.study.yaodh.androidstudy.activity;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

import java.util.Locale;

public class HandlerActivity extends BaseActivity {

    private TextView tvOutput;
    private TextView tvTiming;
    private int time;

    private Handler mHandler = new Handler();

    Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            time++;
            int minute = time / 60;
            int second = time % 60;
            tvOutput.append("time: " + time +  "\n");
            tvTiming.setText(String.format(Locale.ENGLISH, "%02d:%02d", minute, second));
            mHandler.postDelayed(updateRunnable, 1000);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_handler;
    }

    @Override
    protected void initContent() {
        super.initContent();
        tvOutput = (TextView) findViewById(R.id.tv_output);
        tvTiming = (TextView) findViewById(R.id.tv_timing);

        new UpdateThread().start();
    }

    public void startTiming(View view) {
        tvOutput.append("started\n");
        mHandler.postDelayed(updateRunnable, 1000);
    }

    public void stopTiming(View view) {
        tvOutput.append("stopped\n");
        mHandler.removeCallbacks(updateRunnable);
    }

    class UpdateThread extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                // can't do like this
                // tvOutput.append("update textview in update thread.");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvOutput.append("update thread with handler.\n\n");
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
