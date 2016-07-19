package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.SimpleRecyclerViewAdapter;
import com.study.yaodh.androidstudy.model.StaggerItem;
import com.study.yaodh.androidstudy.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.recyclerview.itemanimator.SlideInOutLeftItemAnimator;

public class CoordinatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);

        List<StaggerItem> data = new ArrayList<>();
        data.add(new StaggerItem("sequester", "http://ww4.sinaimg.cn/large/610dc034jw1f5md1e68p9j20fk0ncgo0.jpg"));
        data.add(new StaggerItem("enduring", "http://ww1.sinaimg.cn/large/610dc034jw1f5l6tgzc2sj20zk0nqgq0.jpg"));
        data.add(new StaggerItem("ominous", "http://ww2.sinaimg.cn/large/610dc034jw1f5iv5babirj20zk0nptcn.jpg"));
        data.add(new StaggerItem("leer", "http://ww1.sinaimg.cn/large/610dc034jw1f5hpzuy3r7j20np0zkgpd.jpg"));
        data.add(new StaggerItem("bootless", "http://ww3.sinaimg.cn/large/610dc034jw1f5e7x5vlfyj20dw0euaax.jpg"));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SimpleRecyclerViewAdapter mAdapter = new SimpleRecyclerViewAdapter(this, data);
        recyclerView.setAdapter(mAdapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SlideInOutLeftItemAnimator animator = new SlideInOutLeftItemAnimator(recyclerView);
        animator.setAddDuration(500);
        recyclerView.setItemAnimator(animator);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }
}
