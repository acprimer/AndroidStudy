package com.study.yaodh.androidstudy.service;

import android.content.Context;
import android.content.Intent;

import com.study.yaodh.androidstudy.db.ThreadDaoImpl;
import com.study.yaodh.androidstudy.model.PackageModel;
import com.study.yaodh.androidstudy.model.ThreadInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by yaodh on 2016/8/17.
 */
public class DownloadTask {
    private Context mContext;
    private PackageModel model;
    private ThreadDaoImpl mDao;
    public boolean isPause;

    public DownloadTask(Context context, PackageModel model) {
        this.mContext = context;
        this.model = model;
        mDao = new ThreadDaoImpl(context);
    }

    public void download() {
        // get thread info from database
        List<ThreadInfo> infos = mDao.query(model.getUrl());
        ThreadInfo info;
        if (infos == null || infos.size() <= 0) {
            // init an empty thread info
            info = new ThreadInfo(model.getUrl(), 0, model.getLength());
        } else {
            info = infos.get(0);
        }
        new DownloadThread(info).start();
    }

    class DownloadThread extends Thread {
        private ThreadInfo mThreadInfo;

        public DownloadThread(ThreadInfo mThreadInfo) {
            this.mThreadInfo = mThreadInfo;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;
            InputStream is = null;
            if (!mDao.exists(mThreadInfo.getUrl(), mThreadInfo.getId())) {
                mDao.insert(mThreadInfo);
            }
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
                File file = new File(DownloadService.DOWNLOAD_PATH, model.getName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                Intent intent = new Intent(DownloadService.ACTION_UPDATE);
                int progress = mThreadInfo.getProgress();
                long time = System.currentTimeMillis();
                // start download
                if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                    is = connection.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        // write to local file
                        raf.write(buffer, 0, len);
                        // send download progress broadcast
                        progress += len;
                        if (System.currentTimeMillis() - time > 500) {
                            time = System.currentTimeMillis();
                            int percent = progress * 100 / model.getLength();
                            if (percent > 100) {
                                percent = 100;
                            }
                            intent.putExtra(DownloadService.PROGRESS_KEY, percent);
                            mContext.sendBroadcast(intent);
                        }
                        // pause download
                        if (isPause) {
                            mDao.update(mThreadInfo.getUrl(), mThreadInfo.getId(), progress);
                            return;
                        }
                    }
                    // delete thread info
                    mDao.delete(mThreadInfo.getUrl(), mThreadInfo.getId());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
                try {
                    is.close();
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
