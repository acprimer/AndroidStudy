package com.xueleme.retrofit;

import android.app.Application;

import com.facebook.stetho.Stetho;

/*
 * Created by yaodh on 2019/2/15.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
