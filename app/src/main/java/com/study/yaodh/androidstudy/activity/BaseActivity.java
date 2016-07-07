package com.study.yaodh.androidstudy.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.study.yaodh.androidstudy.R;

/**
 * Created by yaodh on 2016/7/6.
 */
public class BaseActivity extends AppCompatActivity {
    protected String TAG = getClass().getSimpleName();
    protected Toolbar toolbar;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != -1) {
            setContentView(getLayoutId());
        }
        mContext = this;

        initToolbar();

        initContent();
    }

    protected void initContent() {

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null) {
            return;
        }
        toolbar.setTitle(TAG);
        setSupportActionBar(toolbar);
        if(!showBack()) {
            toolbar.setNavigationIcon(null);
            return;
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @LayoutRes
    protected int getLayoutId() {
        return -1;
    }

    protected boolean showBack() {
        return true;
    }
}