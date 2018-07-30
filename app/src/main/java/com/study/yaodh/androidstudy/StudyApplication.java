package com.study.yaodh.androidstudy;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.liulishuo.filedownloader.FileDownloader;
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
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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

        FileDownloader.init(this);
    }

}
