package com.study.yaodh.androidstudy.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.study.yaodh.androidstudy.db.ThreadDaoImpl;
import com.study.yaodh.androidstudy.model.PackageModel;
import com.study.yaodh.androidstudy.model.ThreadInfo;
import com.study.yaodh.androidstudy.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaodh on 2016/8/17.
 */
public class DownloadTask {
    private Context mContext;
    private PackageModel model;
    private ThreadDaoImpl mDao;
    public boolean isPause;
    private int nThread;
    private List<DownloadThread> mThreads;

    public DownloadTask(Context context, PackageModel model) {
        this(context, model, 1);
    }

    public DownloadTask(Context context, PackageModel model, int nThread) {
        this.mContext = context;
        this.model = model;
        this.nThread = nThread;
        mDao = new ThreadDaoImpl(context);
        mThreads = new ArrayList<>(nThread);
    }

    public void download() {
        // get thread info from database
        List<ThreadInfo> threads = mDao.query(model.getUrl());
        if (threads.size() == 0) {
            int length = model.getLength() / nThread;
            for (int i = 0; i < nThread; i++) {
                ThreadInfo info = new ThreadInfo(i, model.getUrl(), length * i, length * (i + 1) - 1);
                if (i == nThread - 1) {
                    info.setEnd(model.getLength());
                }
                threads.add(info);
                mDao.insert(info);
            }
        }

        for(ThreadInfo info : threads) {
            DownloadThread thread = new DownloadThread(info);
            thread.start();
            mThreads.add(thread);
        }
//        ThreadInfo info;
//        if (threads == null || threads.size() <= 0) {
//            // init an empty thread info
//            info = new ThreadInfo(model.getUrl(), 0, model.getLength());
//        } else {
//            info = threads.get(0);
//        }
//        new DownloadThread(info).start();
    }

    private synchronized boolean checkAllFinished() {
        for(DownloadThread thread : mThreads) {
            if(!thread.isFinished) {
                return false;
            }
        }
        Intent intent = new Intent(DownloadService.ACTION_FINISH);
        intent.putExtra(DownloadService.ID_KEY, model.getId());
        mContext.sendBroadcast(intent);
        // delete thread info
        mDao.delete(model.getUrl());
        return true;
    }

    class DownloadThread extends Thread {
        private ThreadInfo mThreadInfo;
        public boolean isFinished;

        public DownloadThread(ThreadInfo mThreadInfo) {
            this.mThreadInfo = mThreadInfo;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;
            InputStream is = null;

            try {
                URL url = new URL(mThreadInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                // set download range
                int start = mThreadInfo.getStart() + mThreadInfo.getProgress();
                int end = mThreadInfo.getEnd();
                connection.setRequestProperty("Range", "bytes=" + start + "-" + end);
                // set file write position
                File file = new File(FileUtils.getDownloadDir(mContext), model.getName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                Intent intent = new Intent(DownloadService.ACTION_UPDATE);
                int progress = mThreadInfo.getProgress();
                long time = System.currentTimeMillis();
                // start download
                Log.d("download", "code: " + connection.getResponseCode());
                if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                    is = connection.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        // write to local file
                        raf.write(buffer, 0, len);
                        // send download progress broadcast
                        progress += len;
                        if (System.currentTimeMillis() - time > 1000) {
                            time = System.currentTimeMillis();
                            sendUpdateBroadcast(intent);
                        }
                        setDownloadSize(len);
                        // pause download
                        if (isPause) {
                            mDao.update(mThreadInfo.getUrl(), mThreadInfo.getId(), progress);
                            return;
                        }
                    }
                    // set finish flag
                    isFinished = true;
                    checkAllFinished();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (raf != null) {
                        raf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendUpdateBroadcast(Intent intent) {
            Log.d("download", "id " + mThreadInfo.getId() + " progress: " + model.getProgress() + " len: " + model.getLength());
            intent.putExtra(DownloadService.PROGRESS_KEY, model.getProgress());
            intent.putExtra(DownloadService.ID_KEY, model.getId());
            intent.putExtra(DownloadService.SIZE_KEY, model.getLength());
            mContext.sendBroadcast(intent);
        }

        private synchronized void setDownloadSize(int len) {
            model.setProgress(model.getProgress() + len);
        }

    }
}
