package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityFileBinding;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileActivity extends BaseActivity {
    ActivityFileBinding binding;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_file;
    }

    @Override
    protected void initContent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file);
        File file = FileUtils.getFile(getCacheDir(), "image");
        try {
            FileUtils.forceMkdir(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
