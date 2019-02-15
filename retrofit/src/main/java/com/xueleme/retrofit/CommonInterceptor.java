package com.xueleme.retrofit;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/*
 * Created by yaodh on 2019/2/15.
 */
public class CommonInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equals("POST")) {
            FormBody.Builder builder = new FormBody.Builder();
            FormBody formBody;
            if (request.body() instanceof FormBody) {
                formBody = (FormBody) request.body();
                for (int i = 0; i < formBody.size(); i++) {
                    builder.add(formBody.encodedName(i), formBody.encodedValue(i));
                }
            }
            formBody = builder
                    .addEncoded("model", "EML-AL00")
                    .addEncoded("mid", "9")
                    .addEncoded("vendor", "googleplay")
                    .addEncoded("imei", "CQk1NmE4OTJhMmIzOGJhNjVlCUNMQjAyMTg4MjkwMDU5ODY%253D")
                    .addEncoded("screen", "1080x2159")
                    .addEncoded("version", "4.0.8")
                    .addEncoded("keyfrom", "hindi.4.0.8.android")
                    .build();
            request = request.newBuilder().post(formBody).build();
        }
        return chain.proceed(request);
    }
}
