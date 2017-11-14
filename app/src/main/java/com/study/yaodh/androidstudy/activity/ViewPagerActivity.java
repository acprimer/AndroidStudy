package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

import java.util.LinkedList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private int step = 1;
    private List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        views = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            addView(i);
        }
        viewPager.setAdapter(new DynamicAdapter(views));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addView(int position) {
        TextView tv = new TextView(this);
        tv.setText(String.valueOf(position));
        tv.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setGravity(Gravity.CENTER);
        views.add(position, tv);
    }

    public void onNext(View view) {
        step = 1;
    }

    public void onNextTwo(View view) {
        step = 2;
    }

    private class DynamicAdapter extends PagerAdapter {
        private List<View> mViews;

        DynamicAdapter(List<View> views) {
            this.mViews = views;
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position % getCount()));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position % getCount()), 0);
            return mViews.get(position % getCount());
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


    }
}
