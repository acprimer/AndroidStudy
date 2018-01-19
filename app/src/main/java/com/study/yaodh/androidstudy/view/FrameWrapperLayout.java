package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by yaodh on 2018/1/10.
 */

public class FrameWrapperLayout extends FrameLayout {
    public FrameWrapperLayout(@NonNull Context context) {
        super(context);
    }

    public FrameWrapperLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FrameWrapperLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        System.out.println("onMeasure-----------");
//        System.out.println("frame super onMeasure-----------");
//        System.out.printf("frame widthSpec 0x%s, heightSpec 0x%s\n",
//                Integer.toHexString(widthMeasureSpec),
//                Integer.toHexString(heightMeasureSpec));
//        System.out.printf("frame width 0x%s, height 0x%s\n",
//                Integer.toHexString(getMeasuredWidthAndState()),
//                Integer.toHexString(getMeasuredHeightAndState()));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        System.out.println("after frame super onMeasure-----------");
//        System.out.printf("frame widthSpec 0x%s, heightSpec 0x%s\n",
//                Integer.toHexString(widthMeasureSpec),
//                Integer.toHexString(heightMeasureSpec));
//        System.out.printf("frame width 0x%s, height 0x%s\n",
//                Integer.toHexString(getMeasuredWidthAndState()),
//                Integer.toHexString(getMeasuredHeightAndState()));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        System.out.println("onLayout-----------");
        System.out.println("frame super onLayout-----------");
        System.out.printf("frame %d, %d, %d, %d\n",
                left, top, right, bottom);
        super.onLayout(changed, left, top, right, bottom);
    }
}
