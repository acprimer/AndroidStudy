package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityBottomSheetBinding;

public class BottomSheetActivity extends BaseActivity {
    private ActivityBottomSheetBinding binding;

    @Override
    protected int getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_sheet);
        return 0;
    }

    @Override
    protected void initContent() {
    }

    public void showBottomSheetDialog(View view) {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_dialog, null);
        dialog.setContentView(layout);
        dialog.show();
    }
}
