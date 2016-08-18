package com.study.yaodh.androidstudy.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.AppAdapter;
import com.study.yaodh.androidstudy.databinding.ActivityMultiDownloadBinding;
import com.study.yaodh.androidstudy.model.PackageModel;
import com.study.yaodh.androidstudy.service.DownloadService;
import com.study.yaodh.androidstudy.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MultiDownloadActivity extends BaseActivity {
    private ActivityMultiDownloadBinding binding;
    private List<PackageModel> mList;
    private AppAdapter mAdapter;

    @Override
    protected void initContent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_multi_download);
        mList = new ArrayList<>();
        String[] appIcons = getResources().getStringArray(R.array.app_icon);
        String[] appNames = getResources().getStringArray(R.array.app_name);
        String[] appUrls = getResources().getStringArray(R.array.app_url);
        for (int i = 0; i < appIcons.length; i++) {
            PackageModel model = new PackageModel(i, appIcons[i], appNames[i], appUrls[i]);
            mList.add(model);
        }
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(mAdapter = new AppAdapter(this, mList));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        // register broadcast receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        filter.addAction(DownloadService.ACTION_FINISH);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    /**
     * update progress
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                // update progressbar
                int id = intent.getIntExtra(DownloadService.ID_KEY, 0);
                int progress = intent.getIntExtra(DownloadService.PROGRESS_KEY, 0);
                int length = intent.getIntExtra(DownloadService.SIZE_KEY, 0);
                mList.get(id).setProgress(progress);
                mList.get(id).setLength(length);
                mAdapter.notifyItemChanged(id);
            } else if (DownloadService.ACTION_FINISH.equals(intent.getAction())) {
                // download finished
                int id = intent.getIntExtra(DownloadService.ID_KEY, 0);
                mList.get(id).setProgress(mList.get(id).getLength());
                mAdapter.notifyItemChanged(id);
            }
        }
    };
}
