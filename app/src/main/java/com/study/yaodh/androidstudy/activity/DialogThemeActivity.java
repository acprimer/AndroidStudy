package com.study.yaodh.androidstudy.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.study.yaodh.androidstudy.R;

public class DialogThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_theme);

        // 设置点击Dialog外面的区域是否关闭Activity
        setFinishOnTouchOutside(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println(isOutOfBounds(this, event));
        return super.onTouchEvent(event);
    }

    private boolean isOutOfBounds(Context context, MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) return false;

        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        final View decorView = getWindow().getDecorView();
        System.out.printf("slop %d x %d y %d width %d height %d\n",
                slop, x, y, decorView.getWidth(), decorView.getHeight());
        return (x < -slop) || (y < -slop)
                || (x > (decorView.getWidth() + slop))
                || (y > (decorView.getHeight() + slop));
    }
}
