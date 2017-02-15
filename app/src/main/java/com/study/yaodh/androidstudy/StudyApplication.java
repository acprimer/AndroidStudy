package com.study.yaodh.androidstudy;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        instance = this;
        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

}
