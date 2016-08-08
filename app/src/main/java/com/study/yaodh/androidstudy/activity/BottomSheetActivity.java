package com.study.yaodh.androidstudy.activity;

import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.study.yaodh.androidstudy.R;

public class BottomSheetActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bottom_sheet;
    }

    @Override
    protected void initContent() {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cl);
        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                showBottomSheetDialog();
                break;
        }
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_dialog, null);
        dialog.setContentView(view);
        dialog.show();
    }
}
