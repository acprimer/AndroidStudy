package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by yaodh on 2017/3/30.
 */

public class ParentViewGroup extends LinearLayout {
    public static final String TAG = "TouchEvent";

    private String name;
    private boolean intercept;
    private boolean touchEventReturn;

    public ParentViewGroup(Context context) {
        super(context);
    }

    public ParentViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntercept(boolean intercept) {
        this.intercept = intercept;
    }

    public void setTouchEventReturn(boolean touchEventReturn) {
        this.touchEventReturn = touchEventReturn;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, name + " dispatchTouchEvent: " + ChildView.parseMotionEventAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, name + " onInterceptTouchEvent: " + ChildView.parseMotionEventAction(ev.getAction()));
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, name + " onTouchEvent: " + ChildView.parseMotionEventAction(event.getAction()));
        return touchEventReturn;
    }
}
