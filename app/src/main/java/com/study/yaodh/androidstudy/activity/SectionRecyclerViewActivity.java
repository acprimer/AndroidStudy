package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.SectionRecyclerViewAdapter;
import com.study.yaodh.androidstudy.model.ListItem;
import com.study.yaodh.androidstudy.view.SectionDividerItemDecoration;

import java.util.LinkedList;
import java.util.List;

public class SectionRecyclerViewActivity extends AppCompatActivity {
    private RecyclerView sectionRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_recycler_view);
        initToolbar();

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

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
