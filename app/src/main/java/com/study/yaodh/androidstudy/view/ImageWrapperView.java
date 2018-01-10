package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by yaodh on 2018/1/10.
 */

public class ImageWrapperView extends AppCompatImageView {
    public ImageWrapperView(Context context) {
        super(context);
    }

    public ImageWrapperView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageWrapperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println("\t\timage super onMeasure-----------");
        System.out.printf("\t\timage widthSpec 0x%s, heightSpec 0x%s\n",
                Integer.toHexString(widthMeasureSpec),
                Integer.toHexString(heightMeasureSpec));
        System.out.printf("\t\timage width 0x%s, height 0x%s\n",
                Integer.toHexString(getMeasuredWidthAndState()),
                Integer.toHexString(getMeasuredHeightAndState()));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println("\t\tafter image super onMeasure-----------");
        System.out.printf("\t\timage widthSpec 0x%s, heightSpec 0x%s\n",
                Integer.toHexString(widthMeasureSpec),
                Integer.toHexString(heightMeasureSpec));
        System.out.printf("\t\timage width 0x%s, height 0x%s\n",
                Integer.toHexString(getMeasuredWidthAndState()),
                Integer.toHexString(getMeasuredHeightAndState()));
    }
}
