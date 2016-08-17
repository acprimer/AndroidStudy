package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.os.Environment;
import android.view.View;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityFileBinding;
import com.study.yaodh.androidstudy.utils.FileUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileActivity extends BaseActivity implements View.OnClickListener{
    ActivityFileBinding binding;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_file;
    }

    @Override
    protected void initContent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file);
        String desc = "getFilesDir() \n" + getFilesDir().toString() + "\n"
                + "getCacheDir() \n" + getCacheDir() + "\n"
                + "getDir(\"yao\", MODE_APPEND) \n" + getDir("yao", MODE_APPEND) + "\n"
                + "getExternalFilesDir(null) \n" + getExternalFilesDir(null) + "\n"
                + "getExternalFilesDir(\"yao\") \n" + getExternalFilesDir("yao") + "\n"
                + "getExternalFilesDir(Environment.DIRECTORY_DCIM) \n" + getExternalFilesDir(Environment.DIRECTORY_DCIM) + "\n"
                + "getExternalCacheDir() \n" + getExternalCacheDir() + "\n"
                + "isSDCardAvailable() \n" + FileUtils.isSDCardAvailable() + "\n"
                + "isExternalStorageRemovable() \n" + Environment.isExternalStorageRemovable() + "\n"
                + "getDataDirectory() \n" + Environment.getDataDirectory().toString() + "\n"
                + "getDownloadCacheDirectory() \n" + Environment.getDownloadCacheDirectory().toString() + "\n"
                + "getExternalStorageDirectory() \n" + Environment.getExternalStorageDirectory().toString() + "\n"
                + "getRootDirectory() \n" + Environment.getRootDirectory().toString() + "\n"
                + "getExternalStoragePublicDirectory() \n" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "\n";
        binding.fileDesc.setText(desc);

        binding.write.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.write:
                writeToFile(binding.etInput.getText().toString());
                break;
        }
    }

    private void writeToFile(String content) {
        try {
            FileOutputStream out = openFileOutput("test.txt", MODE_PRIVATE);
            out.write(content.getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
