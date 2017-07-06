package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.LiulishuoTaskItemAdapter;
import com.study.yaodh.androidstudy.download.TaskManager;

import java.io.File;
import java.lang.ref.WeakReference;

public class LiulishuoDownloadActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button startBtn1;
    private Button pauseBtn1;
    private Button deleteBtn1;
    private TextView filenameTv1;
    private TextView speedTv1;
    private ProgressBar progressBar1;
    private int downloadId = 0;
    private String url, path;
    private LiulishuoTaskItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liulishuo_download);

        startBtn1 = (Button) findViewById(R.id.start_btn_1);
        pauseBtn1 = (Button) findViewById(R.id.pause_btn_1);
        deleteBtn1 = (Button) findViewById(R.id.delete_btn_1);
        filenameTv1 = (TextView) findViewById(R.id.filename_tv_1);
        speedTv1 = (TextView) findViewById(R.id.speed_tv_1);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar_1);
        url = "https://d1e0dtlz2jooy2.cloudfront.net/interdict-offlinedicts/simple-dict/20170510/enfr.dat";
        path = FileDownloadUtils.getDefaultSaveFilePath(url);
        startBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadId = FileDownloader.getImpl().create(url)
                        .setPath(path)
                        .setListener(new FileDownloadListener() {
                            @Override
                            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                speedTv1.setText("Pending");
                            }

                            @Override
                            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                progressBar1.setMax(totalBytes);
                                progressBar1.setProgress(soFarBytes);
                                speedTv1.setText(String.format("sofar: %d total: %d speed %d", soFarBytes, totalBytes, task.getSpeed()));
                            }

                            @Override
                            protected void completed(BaseDownloadTask task) {

                            }

                            @Override
                            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                            }

                            @Override
                            protected void error(BaseDownloadTask task, Throwable e) {

                            }

                            @Override
                            protected void warn(BaseDownloadTask task) {

                            }
                        }).start();
            }
        });

        pauseBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileDownloader.getImpl().pause(downloadId);
            }
        });
        deleteBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new File(path).delete();
                new File(FileDownloadUtils.getTempPath(path)).delete();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new LiulishuoTaskItemAdapter());

        TaskManager.getImpl().onCreate(new WeakReference<>(this));
    }

    public void postNotifyDataChanged() {
        if (adapter != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        TaskManager.getImpl().onDestroy();
        adapter = null;
        FileDownloader.getImpl().pauseAll();
        super.onDestroy();
    }
}
