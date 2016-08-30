package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.study.yaodh.androidstudy.R;

/**
 * Created by yaodh on 16/8/28.
 */
public class CustomProgressbar extends ProgressBar {

    private static final float DEFAULT_TEXT_SIZE = 14.f;
    private static final String DEFAULT_TEXT_COLOR = "#ff000000";
    private static final float DEFAULT_TEXT_PADDING = 8.f;

    private float mTextSize;
    private int mTextColor;
    private float mTextPadding;

    public CustomProgressbar(Context context) {
        this(context, null);
    }

    public CustomProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(attrs);
    }

    /**
     * init attributes of progressbar, such as progress color
     * @param attrs
     */
    private void initAttributes(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CustomProgressbar);
    }
}
