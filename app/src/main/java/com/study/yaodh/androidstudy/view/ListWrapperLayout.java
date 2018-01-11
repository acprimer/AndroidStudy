package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by yaodh on 2018/1/11.
 */

public class ListWrapperLayout extends ListView {
    public ListWrapperLayout(Context context) {
        super(context);
    }

    public ListWrapperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListWrapperLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println("onMeasure-----------");
        System.out.println("listview super onMeasure-----------");
        System.out.printf("listview widthSpec 0x%s, heightSpec 0x%s\n",
                Integer.toHexString(widthMeasureSpec),
                Integer.toHexString(heightMeasureSpec));
        System.out.printf("listview width 0x%s, height 0x%s\n",
                Integer.toHexString(getMeasuredWidthAndState()),
                Integer.toHexString(getMeasuredHeightAndState()));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println("listview frame super onMeasure-----------");
        System.out.printf("listview widthSpec 0x%s, heightSpec 0x%s\n",
                Integer.toHexString(widthMeasureSpec),
                Integer.toHexString(heightMeasureSpec));
        System.out.printf("listview width 0x%s, height 0x%s\n",
                Integer.toHexString(getMeasuredWidthAndState()),
                Integer.toHexString(getMeasuredHeightAndState()));
    }
}
