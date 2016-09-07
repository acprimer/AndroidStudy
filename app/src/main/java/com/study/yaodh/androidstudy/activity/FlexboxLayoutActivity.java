package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityFlexboxLayoutBinding;

public class FlexboxLayoutActivity extends BaseActivity {
    private ActivityFlexboxLayoutBinding binding;

    @Override
    protected int getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_flexbox_layout);
        return 0;
    }

    @Override
    protected void initContent() {
        super.initContent();
    }
}
