package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.view.tab.EnhanceTabLayout;

public class TabActivity extends AppCompatActivity {
//    private TabViewPager tabViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

//        tabViewPager = findViewById(R.id.tab_view_pager);

        EnhanceTabLayout mEnhanceTabLayout = findViewById(R.id.enhance_tab_layout);
        mEnhanceTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("log","onTabSelected");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        String[] sTitle = {"A", "B", "C"};
        for(int i=0;i<sTitle.length;i++){
            mEnhanceTabLayout.addTab(sTitle[i]);
        }
//        mEnhanceTabLayout.setupWithViewPager(mViewPager);
//        List<Fragment> fragments = new ArrayList<>();
//        for(int i=0;i<sTitle.length;i++){
//            fragments.add(ItemFragment.newInstance(sTitle[i]));
//        }
//
//        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(),fragments, Arrays.asList(sTitle));
//        mViewPager.setAdapter(adapter);
//        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mEnhanceTabLayout.getTabLayout()));
//        mEnhanceTabLayout.setupWithViewPager(mViewPager);
    }
}
