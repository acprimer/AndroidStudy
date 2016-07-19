package com.study.yaodh.androidstudy.activity;

import android.widget.ListView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.SectionAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastScrollListActivity extends BaseActivity {

    private ListView listview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fast_scroll_list;
    }

    @Override
    protected void initContent() {
        initListView();
    }

    private void initListView() {
        listview = (ListView) findViewById(android.R.id.list);
        String[] fruits = getResources().getStringArray(R.array.fruit_array);
        List<String> fruitList = Arrays.asList(fruits);
        Collections.sort(fruitList);
        listview.setAdapter(new SectionAdapter(this, fruitList));
    }
}
