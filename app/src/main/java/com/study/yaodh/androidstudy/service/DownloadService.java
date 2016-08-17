package com.study.yaodh.androidstudy.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.study.yaodh.androidstudy.model.PackageModel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yaodh on 2016/8/17.
 */
public class DownloadService extends Service {

    public static final String ACTION_START = "start";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_UPDATE = "update";
    public static final String PROGRESS_KEY = "progress";
    public static final String PACKAGE_KEY = "package";
    public static final int MSG_INIT = 289;
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory() + "/download/";
    private DownloadTask mTask;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(ACTION_START.equals(intent.getAction())) {
            PackageModel model = (PackageModel) intent.getSerializableExtra(PACKAGE_KEY);
            Log.d("Download", "Start " + model.toString());
            // start init thread
            new InitThread(model).start();
        } else {
            PackageModel model = (PackageModel) intent.getSerializableExtra(PACKAGE_KEY);
            Log.d("Download", "Start " + model.toString());
            if(mTask != null) {
                mTask.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_INIT:
                    PackageModel model = (PackageModel) msg.obj;
                    Log.d("Download", model.toString());
                    // start download task
                    mTask = new DownloadTask(DownloadService.this, model);
                    mTask.download();
                    break;
            }
        }
    };

    class InitThread extends Thread {
        private PackageModel model;

        public InitThread(PackageModel model) {
            this.model = model;
        }

        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;
            try {
                // get file length
                URL url = new URL(model.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                int length = -1;
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    length = connection.getContentLength();
                }
                if(length < 0) {
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if(!dir.exists()) {
                    dir.mkdir();
                }
                File file = new File(dir, model.getName());
                raf = new RandomAccessFile(file, "rwd");
                raf.setLength(length);
                model.setLength(length);
                mHandler.obtainMessage(MSG_INIT, model).sendToTarget();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
