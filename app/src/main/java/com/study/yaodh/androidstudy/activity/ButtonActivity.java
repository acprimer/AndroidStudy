package com.study.yaodh.androidstudy.activity;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

public class ButtonActivity extends BaseActivity {

    private Button btnShow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_button;
    }

    @Override
    protected void initContent() {
        View view = findViewById(R.id.rc_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setClipToOutline(true);
        }

        TextView tvClick = findViewById(R.id.tv_camera);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ColorStateList stateList = getResources().getColorStateList(R.color.button_pressed);
            Drawable content = getResources().getDrawable(R.drawable.shape_round_bg);
            RippleDrawable d = new RippleDrawable(stateList, content, null);
            tvClick.setBackground(d);
        } else {
            Drawable content = getResources().getDrawable(R.drawable.shape_round_bg);
            tvClick.setBackgroundDrawable(content);
        }
    }

    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view, Gravity.CENTER);
        popupMenu.inflate(R.menu.menu_popup);
        popupMenu.show();
    }

    public void clickMe(View view) {

    }
}
