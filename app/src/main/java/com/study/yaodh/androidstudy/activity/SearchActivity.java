package com.study.yaodh.androidstudy.activity;

import android.support.v7.widget.SearchView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.view.FloatingSearchView;

public class SearchActivity extends BaseActivity {
    private SearchView searchView;
    private FloatingSearchView floatingSearchView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initContent() {
        searchView = (SearchView) findViewById(R.id.search);
        floatingSearchView = (FloatingSearchView) findViewById(R.id.floating_search);
        floatingSearchView.showIcon(true);
        floatingSearchView.setIcon(R.drawable.ic_menu);
        floatingSearchView.setOnIconClickListener(new FloatingSearchView.OnIconClickListener() {
            @Override
            public void onNavigationClick() {
                // toggle
                floatingSearchView.setActivated(!floatingSearchView.isActivated());
            }
        });
    }
}
