package com.study.yaodh.androidstudy;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by yaodh on 2016/8/17.
 */
public class StudyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
