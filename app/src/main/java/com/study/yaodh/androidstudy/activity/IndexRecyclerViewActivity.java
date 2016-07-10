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

public class IndexRecyclerViewActivity extends BaseActivity {
    private RecyclerView rvList;
    private TextView tvCenter;
    private SideBar sideBar;
    private SectionRecyclerViewAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index_recycler_view;
    }

    @Override
    protected void initContent() {
        rvList = (RecyclerView) findViewById(R.id.list);
        tvCenter = (TextView) findViewById(R.id.text_center);
        sideBar = (SideBar) findViewById(R.id.side_bar);

        sideBar.setTextView(tvCenter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rvList.setLayoutManager(layoutManager);
        String[] fruits = getResources().getStringArray(R.array.fruits_array);
        List<ListItem> list = new LinkedList<>();
        for (int i = 0; i < fruits.length; i++) {
            if (i == 0 || fruits[i - 1].charAt(0) != fruits[i].charAt(0)) {
                list.add(new ListItem(ListItem.TYPE_HEADER, String.valueOf(Character.toUpperCase(fruits[i].charAt(0))), ""));
            }
            list.add(new ListItem(ListItem.TYPE_ITEM, fruits[i], "Fruit " + i));
        }
        rvList.setAdapter(mAdapter = new SectionRecyclerViewAdapter(this, list));
        rvList.addItemDecoration(new SectionDividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (mAdapter != null) {
                }
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    rvList.getLayoutManager().scrollToPosition(position);
                }
            }
        });
    }
}
