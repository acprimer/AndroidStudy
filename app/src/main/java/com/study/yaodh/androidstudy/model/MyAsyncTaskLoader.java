package com.study.yaodh.androidstudy.model;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.study.yaodh.androidstudy.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;

/**
 * Created by yaodh on 2017/2/6.
 */

public class MyAsyncTaskLoader extends AsyncTaskLoader<String> {
    private Context mContext;
    private boolean canceled;

    public MyAsyncTaskLoader(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public String loadInBackground() {
        OkHttpClient client = new OkHttpClient();
        String response1 = null;
        try {
            response1 = getString(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response1 != null) return response1;
        return null;
    }

    @Nullable
    private String getString(OkHttpClient client) throws IOException {
//        String url = "http://inter.youdao.com/intersearch?q=great&from=en&to=hi";
//        Request request = new Request.Builder().url(url).build();
//        Response response = client.newCall(request).execute();
//        if(response.isSuccessful()) {
//            System.out.println("successful");
//            return response.body().string();
//        }
        try {
            String fileUrl = "http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk";
//            String fileUrl = "http://o7rnhttbe.bkt.clouddn.com/splash_1.png";
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
                File file = new File(FileUtils.getDownloadDir(mContext), saveFilePath);
                FileOutputStream outputStream = new FileOutputStream(file);

                int bytesRead = -1;
                byte[] buffer = new byte[4096];

                int downloadedSize = 0;
                while (!canceled && (bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    downloadedSize += bytesRead;
                    int progress = 100 * downloadedSize / contentLength;
                    System.out.println("progress " + progress);
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
        return "AsyncTaskLoader\n";
    }

    @Override
    protected void onStartLoading() {
        System.out.println("onStartLoading");
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        canceled = cancelLoad();
        System.out.println("onStopLoading " + canceled);
    }
}