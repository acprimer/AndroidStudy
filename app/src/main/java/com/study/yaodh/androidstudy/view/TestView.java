package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by yaodh on 2018/12/16.
 */
public class TestView extends AppCompatTextView {

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println("dispatchTouchEvent " + event.getActionMasked());
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
        System.out.println("onTouchEvent " + event.getActionMasked());
        return true;
    }
}
