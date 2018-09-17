package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class LabelViewGroup extends ViewGroup {
    private int space = 10;

    public LabelViewGroup(Context context) {
        super(context);
    }

    public LabelViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LabelViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int usedHorz = 0;
        int usedVert = 0;
        int width = getMeasuredWidth();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getMeasuredHeight() + usedHorz > width) {
                usedVert += child.getMeasuredHeight() + space;
                usedHorz = 0;
            }
            child.layout(usedHorz, usedVert, usedHorz + child.getMeasuredWidth(), usedVert + child.getMeasuredHeight());
            usedHorz += child.getMeasuredWidth() + space;
        }
    }
}
