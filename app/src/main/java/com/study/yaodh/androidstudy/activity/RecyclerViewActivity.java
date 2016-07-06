package com.study.yaodh.androidstudy.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.SimpleRecyclerViewAdapter;
import com.study.yaodh.androidstudy.view.DividerItemDecoration;

/**
 * Created by yaodh on 2016/5/12.
 */
public class RecyclerViewActivity extends BaseActivity {
    private RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initContent() {
        String[] data = new String[52];
        for (int i=0;i<data.length;i++) {
            data[i] = String.valueOf((char)('A' + i));
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new SimpleRecyclerViewAdapter(this, data));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

}
