package com.study.yaodh.androidstudy.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

import java.lang.ref.WeakReference;
import java.util.Locale;

public class HandlerActivity extends BaseActivity {

    private TextView tvOutput;
    private TextView tvTiming;
    private int time;

    private static class AvoidLeakyHandler extends Handler {
        private final WeakReference<HandlerActivity> mActivity;

        private AvoidLeakyHandler(HandlerActivity activity) {
            mActivity = new WeakReference<HandlerActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HandlerActivity activity = mActivity.get();
            if (activity != null) {
                System.out.println("not null handle msg: " + msg);
            } else {
                System.out.println("null handle msg: " + msg);
            }
        }
    }

    private Handler mLeakyHandler = new AvoidLeakyHandler(this);
    private static final Runnable sRunnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("run in Runnable.");
        }
    };

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

//        new UpdateThread().start();
        mLeakyHandler.postDelayed(sRunnable, 1000 * 60 * 1);
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
