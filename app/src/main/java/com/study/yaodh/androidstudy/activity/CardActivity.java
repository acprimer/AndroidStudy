package com.study.yaodh.androidstudy.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.CardAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private CardAdapter mAdapter;
    private List<String> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card;
    }

    @Override
    protected void initContent() {
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.fruit_array);
        list.addAll(Arrays.asList(array));
        recyclerView.setAdapter(mAdapter = new CardAdapter(this, list));
    }
}
