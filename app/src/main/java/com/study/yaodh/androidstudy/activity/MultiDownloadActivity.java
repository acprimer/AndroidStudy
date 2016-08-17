package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.AppAdapter;
import com.study.yaodh.androidstudy.databinding.ActivityMultiDownloadBinding;
import com.study.yaodh.androidstudy.model.PackageModel;
import com.study.yaodh.androidstudy.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MultiDownloadActivity extends BaseActivity {
    private ActivityMultiDownloadBinding binding;
    private List<PackageModel> mList;
    private String[] appIcons;
    private String[] appNames;
    private String[] appUrls;

    @Override
    protected void initContent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_multi_download);
        mList = new ArrayList<>();
        appIcons = getResources().getStringArray(R.array.app_icon);
        String[] appNames = getResources().getStringArray(R.array.app_name);
        String[] appUrls = getResources().getStringArray(R.array.app_url);
        for (int i = 0; i < appIcons.length; i++) {
            PackageModel model = new PackageModel(appIcons[i], appNames[i], appUrls[i]);
            mList.add(model);
        }
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new AppAdapter(this, mList));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }
}
