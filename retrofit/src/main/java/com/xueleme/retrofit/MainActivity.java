package com.xueleme.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String baseUrl = "https://interflow.youdao.com/";
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new CommonInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        Api api = retrofit.create(Api.class);
        api.getStrategy().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ResponseBody strategyDataBaseModel) {
                        try {
                            System.out.println(strategyDataBaseModel.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
