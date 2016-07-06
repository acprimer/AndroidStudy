package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.SectionAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastScrollListActivity extends AppCompatActivity {

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_scroll_list);
        initToolbar();

        initListView();
    }

    private void initListView() {
        listview = (ListView) findViewById(android.R.id.list);
        String[] fruits = getResources().getStringArray(R.array.fruits_array);
        List<String> fruitList = Arrays.asList(fruits);
        Collections.sort(fruitList);
        listview.setAdapter(new SectionAdapter(this, fruitList));
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
