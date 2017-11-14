package com.study.yaodh.androidstudy.activity;

import android.view.View;
import android.widget.CheckedTextView;

import com.study.yaodh.androidstudy.R;

public class SwitchButtonActivity extends BaseActivity {
    private CheckedTextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_switch_button;
    }

    @Override
    protected void initContent() {
        textView = (CheckedTextView) findViewById(R.id.checked_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.toggle();
            }
        });
    }
}
