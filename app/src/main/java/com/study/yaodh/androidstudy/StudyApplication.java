package com.study.yaodh.androidstudy;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by yaodh on 2016/8/17.
 */
public class StudyApplication extends Application {
    private static StudyApplication instance = null;

    public static StudyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

}
