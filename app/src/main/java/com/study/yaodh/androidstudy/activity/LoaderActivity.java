package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityLoaderBinding;
import com.study.yaodh.androidstudy.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoaderActivity extends AppCompatActivity {
    private ActivityLoaderBinding binding;
    public static final String TAG = "LoaderActivity";
    private TestAsyncTask task1, task2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        System.out.println("thread " + Thread.currentThread().getId());
        System.out.println(mHandler);
        System.out.println(mHandler.getLooper());
        System.out.println(mHandler.getLooper().getQueue());

        binding = DataBindingUtil.setContentView(this, R.layout.activity_loader);
        binding.result2.setMovementMethod(new ScrollingMovementMethod());

        mHandler.sendEmptyMessage(0);

        int cpu = Runtime.getRuntime().availableProcessors();
        binding.result.append("cpu " + cpu);

        task1 = new TestAsyncTask(1);
//        task1.execute();

//        getSupportLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<String>() {
//            @Override
//            public Loader<String> onCreateLoader(int id, Bundle args) {
//                return new MyAsyncTaskLoader(LoaderActivity.this);
//            }
//
//            @Override
//            public void onLoadFinished(Loader<String> loader, String data) {
//                Log.d(TAG, "AsyncTaskLoader onLoadFinished: 1");
//                binding.result.append(data);
//            }
//
//            @Override
//            public void onLoaderReset(Loader<String> loader) {
//
//            }
//        });

//        getSupportLoaderManager().initLoader(1, null, new LoaderManager.LoaderCallbacks<String>() {
//            @Override
//            public Loader<String> onCreateLoader(int id, Bundle args) {
//                return new MyAsyncTaskLoader(LoaderActivity.this);
//            }
//
//            @Override
//            public void onLoadFinished(Loader<String> loader, String data) {
//                Log.d(TAG, "AsyncTaskLoader onLoadFinished: 2");
//                binding.result.append(data);
//            }
//
//            @Override
//            public void onLoaderReset(Loader<String> loader) {
//
//            }
//        });



//        task2 = new TestAsyncTask(2);
//        task2.execute();

//        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
//            @Override
//            public boolean queueIdle() {
//                System.out.println("idle");
//                return false;
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (task1 != null && task1.getStatus() == AsyncTask.Status.RUNNING) {
            task1.cancel(true);
        }
        if (task2 != null && task2.getStatus() == AsyncTask.Status.RUNNING) {
            task2.cancel(true);
        }
    }

    private class TestAsyncTask extends AsyncTask<Void, Integer, String> {

        private int id;

        public TestAsyncTask(int id) {
            this.id = id;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
//                String fileUrl = "http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk";
                String fileUrl = "http://o7rnhttbe.bkt.clouddn.com/splash_1.png";
                URL url = new URL(fileUrl);
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                int responseCode = httpConn.getResponseCode();

                // always check HTTP response code first
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String fileName = "";
                    String disposition = httpConn.getHeaderField("Content-Disposition");
                    String contentType = httpConn.getContentType();
                    int contentLength = httpConn.getContentLength();

                    if (disposition != null) {
                        // extracts file name from header field
                        int index = disposition.indexOf("filename=");
                        if (index > 0) {
                            fileName = disposition.substring(index + 10,
                                    disposition.length() - 1);
                        }
                    } else {
                        // extracts file name from URL
                        fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1,
                                fileUrl.length());
                    }

                    System.out.println("Content-Type = " + contentType);
                    System.out.println("Content-Disposition = " + disposition);
                    System.out.println("Content-Length = " + contentLength);
                    System.out.println("fileName = " + fileName);

                    // opens input stream from the HTTP connection
                    InputStream inputStream = httpConn.getInputStream();
                    String saveFilePath = File.separator + fileName;

                    // opens an output stream to save into file
                    File file = new File(FileUtils.getDownloadDir(LoaderActivity.this), saveFilePath);
                    FileOutputStream outputStream = new FileOutputStream(file);

                    int bytesRead = -1;
                    byte[] buffer = new byte[4096];

                    int downloadedSize = 0;
                    while (!isCancelled() && (bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        downloadedSize += bytesRead;
                        int progress = 100 * downloadedSize / contentLength;
                        System.out.println("progress " + progress);
                        publishProgress(progress);
                    }

                    outputStream.close();
                    inputStream.close();

                    System.out.println("File downloaded");
                } else {
                    System.out.println("No file to download. Server replied HTTP code: " + responseCode);
                }
                httpConn.disconnect();
            } catch (Exception e) {

            }
            return "TestAsyncTask " + id;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            binding.result2.append("TestAsyncTask " + id + " " + progress[0] + "\n");
            Log.d(TAG, "onProgressUpdate: " + id + " " + progress[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            binding.result2.append("TestAsyncTask " + id + " onPostExecute\n");
            Log.d(TAG, "onPostExecute: " + id);
        }

        @Override
        protected void onCancelled(String s) {
            Log.d(TAG, "onCancelled: " + s);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
        mHandler.removeCallbacksAndMessages(null);
        mHandler.getLooper().quit();
    }
}
