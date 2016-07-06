package com.study.yaodh.androidstudy.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.SectionRecyclerViewAdapter;
import com.study.yaodh.androidstudy.model.ListItem;
import com.study.yaodh.androidstudy.view.SectionDividerItemDecoration;

import java.util.LinkedList;
import java.util.List;

public class SectionRecyclerViewActivity extends BaseActivity {
    private RecyclerView sectionRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_section_recycler_view;
    }

    @Override
    protected void initContent() {
        super.initContent();
        sectionRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        sectionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] fruits = getResources().getStringArray(R.array.fruits_array);
        List<ListItem> list = new LinkedList<>();
        for (int i = 0; i < fruits.length; i++) {
            if (i == 0 || fruits[i - 1].charAt(0) != fruits[i].charAt(0)) {
                list.add(new ListItem(ListItem.TYPE_HEADER, String.valueOf(Character.toUpperCase(fruits[i].charAt(0))), ""));
            }
            list.add(new ListItem(ListItem.TYPE_ITEM, fruits[i], "Fruit " + i));
        }
        sectionRecyclerView.setAdapter(new SectionRecyclerViewAdapter(this, list));
        sectionRecyclerView.addItemDecoration(new SectionDividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

}
