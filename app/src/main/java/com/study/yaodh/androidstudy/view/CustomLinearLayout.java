package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.study.yaodh.androidstudy.R;

public class CustomLinearLayout extends LinearLayout {
    public CustomLinearLayout(Context context) {
        this(context, null);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_custom_linear, this, true);
    }
}
