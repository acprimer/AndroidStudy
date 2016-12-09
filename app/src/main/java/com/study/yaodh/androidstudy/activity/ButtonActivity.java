package com.study.yaodh.androidstudy.activity;

import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.study.yaodh.androidstudy.R;

public class ButtonActivity extends BaseActivity {

    private Button btnShow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_button;
    }

    @Override
    protected void initContent() {
    }

    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view, Gravity.CENTER);
        popupMenu.inflate(R.menu.menu_popup);
        popupMenu.show();
    }
}
