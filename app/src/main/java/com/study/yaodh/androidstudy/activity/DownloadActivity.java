package com.study.yaodh.androidstudy.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
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
import com.study.yaodh.androidstudy.utils.FileUtils;
import com.study.yaodh.androidstudy.view.MaterialProgressDrawable;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

public class DownloadActivity extends BaseActivity {
    private ActivityDownloadBinding binding;
    private static final String FILE_NAME = "cloud-music.apk";
    private static final String DOWNLOAD_URL = "http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk";
    private PackageModel model;
    private final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DOWNLOAD_URL));
    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private static final int CIRCLE_DIAMETER = 40;

    @Override
    protected void initContent() {
        super.initContent();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_download);
        binding.setProgress(0);
        binding.setStatus("Status: ");
        binding.progressbar.setMax(100);
        model = new PackageModel(FILE_NAME, DOWNLOAD_URL);
        initProgress();

        MaterialProgressDrawable mProgressDrawable = new MaterialProgressDrawable(this, binding.ivPb);
        mProgressDrawable.setBackgroundColor(CIRCLE_BG_LIGHT);
        mProgressDrawable.setColorSchemeColors(Color.RED);
        mProgressDrawable.start();
//        binding.materialLayout.addView(mCircleView);
        binding.ivPb.setImageDrawable(mProgressDrawable);

        FrameLayout layout = (FrameLayout) findViewById(R.id.pie_btn);


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DownloadActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

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
                return FileUtils.saveFile(data, dir, fileName);
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    Toast.makeText(DownloadActivity.this, "Download success", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    public void downloadManagerStart(View view) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DOWNLOAD_URL));
        request.setDescription("Some description");
        request.setTitle("Download NetEase Cloud Music");
        // in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "mymusic.apk");

        // get download service and enqueue file
        final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//        manager.enqueue(request);
        final long downloadId = manager.enqueue(request);
        binding.theProgressBar.setProgress(0.f);
        binding.theProgressBar.startAnimation();
        //update progress
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isDownloading = true;
                while (isDownloading) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadId);

                    Cursor cursor = manager.query(query);
                    cursor.moveToFirst();
                    int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                            == DownloadManager.STATUS_SUCCESSFUL) {
                        isDownloading = false;
                    }

                    final int progress = bytes_downloaded * 100 / bytes_total;
                    System.out.println("progress " + progress);
                    EventBus.getDefault().post("progress " + progress);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.progressbarManager.setProgress(progress);
                            binding.theProgressBar.setProgress(progress);
                        }
                    });
                    Log.d("download", "percent " + progress);
                    cursor.close();
                }
            }
        }).start();
    }

    public static long downloadPackage(Context context, PackageModel model) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(model.getUrl()));
        request.setTitle(model.getName());
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, model.getName()+".apk");
        final DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        return manager.enqueue(request);
    }

    public void myDownloadManagerStart(View view) {
//        com.example.download.DownloadManager downloadManager = new com.example.download.DownloadManager(getContentResolver(), getPackageName());
//        com.example.download.DownloadManager.Request request = new com.example.download.DownloadManager.Request(Uri.parse(DOWNLOAD_URL));
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "mymusic111.apk");
//        downloadManager.enqueue(request);
    }
}
