package com.study.yaodh.androidstudy.activity;

import android.widget.Button;

import com.study.yaodh.androidstudy.R;

public class DialogActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initContent() {
        super.initContent();

        Button btnDialogActivity = findViewById(R.id.button);
        btnDialogActivity.setOnClickListener(v -> start(DialogThemeActivity.class));
    }
}
