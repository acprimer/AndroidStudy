package com.study.yaodh.androidstudy.activity;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.view.FloatingSearchView;

public class SearchActivity extends BaseActivity {
    private RecyclerView mReccylerView;
    private SearchView searchView;
    private FloatingSearchView floatingSearchView;
    private MaterialSearchBar searchBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initContent() {
        mReccylerView = (RecyclerView) findViewById(R.id.recycler_view);
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

        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setHint("Custom hint");
        searchBar.setSpeechMode(true);
        //enable searchbar callbacks
//        searchBar.setOnSearchActionListener(this);
        //restore last queries from disk
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("searchView", query);
                searchView.clearFocus();
                searchItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}
