package com.study.yaodh.androidstudy.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityDownloadBinding;
import com.study.yaodh.androidstudy.db.ThreadDaoImpl;
import com.study.yaodh.androidstudy.model.PackageModel;
import com.study.yaodh.androidstudy.model.ThreadInfo;
import com.study.yaodh.androidstudy.network.ByteRequest;
import com.study.yaodh.androidstudy.service.DownloadService;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class DownloadActivity extends BaseActivity {
    private ActivityDownloadBinding binding;
    private static final String FILE_NAME = "cloud-music.apk";
    private static final String DOWNLOAD_URL = "http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk";
    private PackageModel model;

    @Override
    protected void initContent() {
        super.initContent();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_download);
        binding.setProgress(0);
        binding.setStatus("Status: ");
        binding.progressbar.setMax(100);
        model = new PackageModel(FILE_NAME, DOWNLOAD_URL);
        initProgress();

        // register broadcast receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(mReceiver, filter);
    }

    private void initProgress() {
        // get thread info from database
        ThreadDaoImpl mDao = new ThreadDaoImpl(this);
        List<ThreadInfo> infos = mDao.query(model.getUrl());
        if (infos != null && infos.size() > 0) {
            ThreadInfo info = infos.get(0);
            binding.setProgress(info.getProgress() * 100 / info.getEnd());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public void startDownload(View view) {
        binding.setStatus("Status: started");
        startDownloadService(DownloadService.ACTION_START, model);
    }

    public void pauseDownload(View view) {
        binding.setStatus("Status: paused");
        startDownloadService(DownloadService.ACTION_PAUSE, model);
    }

    private void startDownloadService(String actionStart, PackageModel model) {
        Intent intent = new Intent(this, DownloadService.class);
        intent.setAction(actionStart);
        intent.putExtra(DownloadService.PACKAGE_KEY, model);
        startService(intent);
    }

    /**
     * update progress
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                int progress = intent.getIntExtra(DownloadService.PROGRESS_KEY, 0);
                binding.progressbar.setProgress(progress);
            }
        }
    };

    public void startVolleyDownload(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        ByteRequest byteRequest = new ByteRequest(Request.Method.GET,
                DOWNLOAD_URL, new Response.Listener<byte[]>() {
            @Override
            public void onResponse(byte[] response) {
                asyncSaveFile(response, new File(Environment.getExternalStorageDirectory() + "/download/"), "music.apk");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DownloadActivity.this, "Download error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(byteRequest);
    }

    private void asyncSaveFile(final byte[] data, final File dir, final String fileName) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                return saveFile(data, dir, fileName);
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if(success) {
                    Toast.makeText(DownloadActivity.this, "Download success", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    /**
     * 获取缓存目录
     * 有SD卡 -> getExternalCacheDir -> SDCard/Android/data/package/cache API<19需要申请权限
     * 无SD卡 -> getCacheDir -> data/data/package/cache 不需要申请权限
     *
     * @return
     */
    public File getDiskCacheDir() {
        Context context = getApplicationContext();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir();
        } else {
            return context.getCacheDir();
        }
    }

    /**
     * 保存内容到指定目录
     *
     * @param bytes
     * @param dir 目录
     * @param fileName
     * @return
     */
    public static boolean saveFile(byte[] bytes, File dir, String fileName) {
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return false;
            }
        }
        return saveFile(bytes, new File(dir, fileName));
    }

    /**
     * 保存内容到指定文件，必须保证目录存在
     *
     * @param bytes 二进制数据
     * @param file 文件
     * @return
     */
    public static boolean saveFile(byte[] bytes, File file) {
        if (bytes == null) {
            return false;
        }
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return false;
                }
            }
            fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        file.delete();
        return false;
    }
}
