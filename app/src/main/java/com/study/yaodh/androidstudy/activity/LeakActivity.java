package com.study.yaodh.androidstudy.activity;

import android.os.Debug;

import com.study.yaodh.androidstudy.R;

import java.io.File;
import java.io.IOException;

/*
 * Created by yaodh on 2019/2/14.
 */
public class LeakActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leak;
    }

    @Override
    protected void initContent() {
        try {
            File file = new File(getCacheDir(), "leak");
            System.out.println("exits: " + file.exists());
            boolean success = file.createNewFile();
            System.out.println("success " + success);
            System.out.println(file.getAbsolutePath());
            Debug.dumpHprofData(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
