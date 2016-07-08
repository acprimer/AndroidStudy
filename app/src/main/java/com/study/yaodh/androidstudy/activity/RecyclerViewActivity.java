package com.study.yaodh.androidstudy.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.SimpleRecyclerViewAdapter;
import com.study.yaodh.androidstudy.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaodh on 2016/5/12.
 */
public class RecyclerViewActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    private RecyclerView recyclerView;
    private List<String> data;
    private SimpleRecyclerViewAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initContent() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        data = new ArrayList<>();
        for (int i = 0; i < 52; i++) {
            data.add(String.valueOf((char) ('A' + i)));
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SimpleRecyclerViewAdapter(this, data);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                data.add(1, "AA");
                data.add(1, "AA");
//                mAdapter.notifyDataSetChanged();
                mAdapter.notifyItemInserted(1);
                mAdapter.notifyItemRangeInserted(1, 2);
                break;
        }
        return true;
    }
}
