package com.example.lib;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yaodh on 2017/11/14.
 */

public class HelloOkHttp {
    public static void main(String[] args) {
        // 创建client对象
        OkHttpClient client = new OkHttpClient();
        // 创建Request对象
        Request request = new Request.Builder()
                .url("http://www.imooc.com").build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // 注意是string()方法不是toString()方法
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
