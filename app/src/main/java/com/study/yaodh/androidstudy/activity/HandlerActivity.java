package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

import java.lang.ref.WeakReference;
import java.util.Locale;

public class HandlerActivity extends BaseActivity {

    private TextView tvOutput;
    private TextView tvTiming;
    private int time;

    private Handler mHandler = new Handler();

    private static final class ClockRunnable implements Runnable {
        private final WeakReference<HandlerActivity> mActivityRef;

        private ClockRunnable(HandlerActivity activity) {
            mActivityRef = new WeakReference<HandlerActivity>(activity);
        }

        @Override
        public void run() {
            System.out.println("time: " + System.currentTimeMillis());
            final HandlerActivity activity = mActivityRef.get();
            if (activity != null) {
                activity.updateView();
            }
        }
    }

    private ClockRunnable clockRunnable = new ClockRunnable(this);

    private void updateView() {
        time++;
        int minute = time / 60;
        int second = time % 60;
        System.out.println("time: " + time +  "\n");
        tvOutput.append("time: " + time +  "\n");
        tvTiming.setText(String.format(Locale.ENGLISH, "%02d:%02d", minute, second));
//        mHandler.postDelayed(clockRunnable, 1000);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_handler;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("time", time);
    }

    @Override
    protected void readIntent(Bundle savedInstanceState) {
        super.readIntent(savedInstanceState);
        if (savedInstanceState == null) {
            return;
        }
        time = savedInstanceState.getInt("time");
    }

    @Override
    protected void initContent() {
        super.initContent();
        System.out.println("thread " + Thread.currentThread().getId());
        System.out.println(mHandler);
        System.out.println(mHandler.getLooper());
        System.out.println(mHandler.getLooper().getQueue());
        tvOutput = (TextView) findViewById(R.id.tv_output);
        tvTiming = (TextView) findViewById(R.id.tv_timing);

//        new UpdateThread().start();
        mHandler.postDelayed(clockRunnable, 1000 * 20);
    }

    public void startTiming(View view) {
        tvOutput.append("started\n");
//        mHandler.postDelayed(updateRunnable, 1000);
    }

    public void stopTiming(View view) {
        tvOutput.append("stopped\n");
//        mHandler.removeCallbacks(updateRunnable);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
//        mHandler.removeCallbacksAndMessages(null);
    }
}
