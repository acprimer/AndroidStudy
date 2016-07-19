package com.study.yaodh.androidstudy.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.SectionRecyclerViewAdapter;
import com.study.yaodh.androidstudy.model.ListItem;
import com.study.yaodh.androidstudy.view.SectionDividerItemDecoration;
import com.study.yaodh.androidstudy.view.SideBar;

import java.util.LinkedList;
import java.util.List;

public class SectionRecyclerViewActivity extends BaseActivity {
    private RecyclerView sectionRecyclerView;
    private TextView tvCenter;
    private SideBar sideBar;
    private LinearLayoutManager layoutManager;
    private SectionRecyclerViewAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_section_recycler_view;
    }

    @Override
    protected void initContent() {
        super.initContent();
        sectionRecyclerView = (RecyclerView) findViewById(R.id.list);
        tvCenter = (TextView) findViewById(R.id.text_center);
        sideBar = (SideBar) findViewById(R.id.side_bar);

        sideBar.setTextView(tvCenter);

        layoutManager = new LinearLayoutManager(this);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
        sectionRecyclerView.setLayoutManager(layoutManager);
        String[] fruits = getResources().getStringArray(R.array.fruit_array);
        List<ListItem> list = new LinkedList<>();
        for (int i = 0; i < fruits.length; i++) {
            if (i == 0 || fruits[i - 1].charAt(0) != fruits[i].charAt(0)) {
                list.add(new ListItem(ListItem.TYPE_HEADER, String.valueOf(Character.toUpperCase(fruits[i].charAt(0))), ""));
            }
            list.add(new ListItem(ListItem.TYPE_ITEM, fruits[i], "Fruit " + i));
        }
        sectionRecyclerView.setAdapter(mAdapter = new SectionRecyclerViewAdapter(this, list));
        sectionRecyclerView.addItemDecoration(new SectionDividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (mAdapter != null) {
                }
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    layoutManager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

    }

}
