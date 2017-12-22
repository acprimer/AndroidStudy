package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
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
        System.out.println("time: " + time + "\n");
        tvOutput.append("time: " + time + "\n");
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
        tvOutput = findViewById(R.id.tv_output);
        tvTiming = findViewById(R.id.tv_timing);

        testPostRunnable();

//        System.out.println("thread " + Thread.currentThread().getId());
//        System.out.println(mHandler);
//        System.out.println(mHandler.getLooper());
//
////        new UpdateThread().start();
//        mHandler.postDelayed(clockRunnable, 1000 * 20);
//
//        HandlerThread handlerThread = new HandlerThread("my handler thread");
//        handlerThread.start();
//        Handler mHandler2 = new Handler(handlerThread.getLooper()) {
//            @Override
//            public void handleMessage(Message msg) {
//                System.out.println("handlerthread");
//            }
//        };
//        mHandler2.sendEmptyMessage(0);

        final LooperThread test = new LooperThread();
        test.start();
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.myHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    // https://mp.weixin.qq.com/s?__biz=MjM5OTE4ODgzMw==&mid=2247483722&idx=1&sn=7290f6eefc0ef19d933c0b4039865bcb&chksm=a73e01449049885220395c4906f6293900a7bb51fc2f509643839f3c41440a51ddae01d9eb56&mpshare=1&scene=23&srcid=1213PI1J8VurqCMgoIYpgQQS#rd
    // 对比runOnUiThread 、Handler.post、View.post
    private void testPostRunnable() {
        View view = new View(this);
        view.post(() -> Log.d(TAG, "run: view.post"));
        tvOutput.post(() -> Log.d(TAG, "run: tvOutput.post"));
        new Handler().post(() -> Log.d(TAG, "run: handler.post"));
        runOnUiThread(() -> Log.d(TAG, "run: runOnUiThread"));
        new Thread(() -> runOnUiThread(() -> Log.d(TAG, "run: new Thread"))).start();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            view.post(() -> Log.d(TAG, "run: view.post delay 1s"));
            tvOutput.post(() -> Log.d(TAG, "run: tvOutput.post delay 1s"));
        }).start();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow: ");
    }

    class LooperThread extends Thread {
        public Handler myHandler;

        @Override
        public void run() {
            Looper.prepare();
            System.out.println("looper start.");
            System.out.println("looper " + Looper.myLooper());

            myHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    System.out.println("handleMessage " + msg);
                    Looper.myLooper().quit();
                }
            };
            Looper.loop();
            System.out.println("looper end.");
        }
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
