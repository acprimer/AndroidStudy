package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityFlexboxLayoutBinding;
import com.study.yaodh.androidstudy.view.SearchLabelView;

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

    public void addLabel(View view) {
        String label = binding.input.getText().toString();
        if (TextUtils.isEmpty(label)) {
            return;
        }
        final SearchLabelView searchLabelView = new SearchLabelView(this);
        searchLabelView.setLabel(label);
        searchLabelView.setOnCloseListener(new SearchLabelView.OnCloseListener() {
            @Override
            public void onClosed() {
                binding.flexLayout.removeView(searchLabelView);
            }
        });
        binding.flexLayout.addView(searchLabelView);
    }
}
