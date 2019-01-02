package com.study.yaodh.androidstudy.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.SimpleRecyclerViewAdapter;
import com.study.yaodh.androidstudy.model.StaggerItem;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.recyclerview.itemanimator.SlideInOutLeftItemAnimator;

/**
 * Created by yaodh on 2016/5/12.
 */
public class RecyclerViewActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    private RecyclerView recyclerView;
    private List<StaggerItem> data;
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
        data.add(new StaggerItem("sequester", "http://ww4.sinaimg.cn/large/610dc034jw1f5md1e68p9j20fk0ncgo0.jpg"));
        data.add(new StaggerItem("enduring", "http://ww1.sinaimg.cn/large/610dc034jw1f5l6tgzc2sj20zk0nqgq0.jpg"));
        data.add(new StaggerItem("ominous", "http://ww2.sinaimg.cn/large/610dc034jw1f5iv5babirj20zk0nptcn.jpg"));
        data.add(new StaggerItem("leer", "http://ww1.sinaimg.cn/large/610dc034jw1f5hpzuy3r7j20np0zkgpd.jpg"));
        data.add(new StaggerItem("bootless", "http://ww3.sinaimg.cn/large/610dc034jw1f5e7x5vlfyj20dw0euaax.jpg"));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager lm = new GridLayoutManager(this, 2);
        lm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0)return 2;
                return 1;
            }
        });
        recyclerView.setLayoutManager(lm);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new SimpleRecyclerViewAdapter(this, data);
        recyclerView.setAdapter(mAdapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SlideInOutLeftItemAnimator animator = new SlideInOutLeftItemAnimator(recyclerView);
        animator.setAddDuration(500);
        recyclerView.setItemAnimator(animator);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
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
//                data.add("AA");
//                data.add("BB");
//                data.add("CC");
//                mAdapter.notifyDataSetChanged();
//                mAdapter.notifyItemInserted(data.size() - 1);
//                mAdapter.notifyItemRangeInserted(data.size() - 3, 3);
                break;
        }
        return true;
    }
}
