package com.study.yaodh.androidstudy.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.databinding.DataBindingUtil;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.DownloadAdapter;
import com.study.yaodh.androidstudy.databinding.ActivityDownloadListBinding;

public class DownloadListActivity extends BaseActivity {
    private ActivityDownloadListBinding binding;

    @Override
    protected void initContent() {
        super.initContent();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_download_list);

        final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterByStatus(DownloadManager.STATUS_RUNNING | DownloadManager.STATUS_SUCCESSFUL);
        DownloadAdapter adapter = new DownloadAdapter(this, manager.query(query));

        binding.downloadList.setAdapter(adapter);
    }
}
