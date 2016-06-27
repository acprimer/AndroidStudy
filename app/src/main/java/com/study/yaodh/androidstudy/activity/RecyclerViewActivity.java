package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.SimpleRecyclerViewAdapter;
import com.study.yaodh.androidstudy.view.DividerItemDecoration;

/**
 * Created by yaodh on 2016/5/12.
 */
public class RecyclerViewActivity extends AppCompatActivity{
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.RecyclerView);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
//        collapsingToolbarLayout.setTitle("Test");
//        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
//        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);


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
