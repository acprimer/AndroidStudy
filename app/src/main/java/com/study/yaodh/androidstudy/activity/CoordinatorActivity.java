package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.SimpleRecyclerViewAdapter;
import com.study.yaodh.androidstudy.databinding.ActivityCoordinatorBinding;
import com.study.yaodh.androidstudy.model.StaggerItem;
import com.study.yaodh.androidstudy.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.recyclerview.itemanimator.SlideInOutLeftItemAnimator;

public class CoordinatorActivity extends BaseActivity {

    private ActivityCoordinatorBinding binding;
    private LinearLayoutManager mLayoutManage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coordinator;
    }

    @Override
    protected void initContent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coordinator);
        binding.toolbar.setTitle("");
        List<StaggerItem> data = new ArrayList<>();
        data.add(new StaggerItem("sequester", "http://ww4.sinaimg.cn/large/610dc034jw1f5md1e68p9j20fk0ncgo0.jpg"));
        data.add(new StaggerItem("enduring", "http://ww1.sinaimg.cn/large/610dc034jw1f5l6tgzc2sj20zk0nqgq0.jpg"));
        data.add(new StaggerItem("ominous", "http://ww2.sinaimg.cn/large/610dc034jw1f5iv5babirj20zk0nptcn.jpg"));
        data.add(new StaggerItem("leer", "http://ww1.sinaimg.cn/large/610dc034jw1f5hpzuy3r7j20np0zkgpd.jpg"));
        data.add(new StaggerItem("bootless", "http://ww3.sinaimg.cn/large/610dc034jw1f5e7x5vlfyj20dw0euaax.jpg"));

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recyclerView.setLayoutManager(mLayoutManage = new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SimpleRecyclerViewAdapter mAdapter = new SimpleRecyclerViewAdapter(this, data);
        binding.recyclerView.setAdapter(mAdapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SlideInOutLeftItemAnimator animator = new SlideInOutLeftItemAnimator(binding.recyclerView);
        animator.setAddDuration(500);
        binding.recyclerView.setItemAnimator(animator);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayoutManage.scrollToPosition(0);
            }
        });
    }
}
