package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yaodh on 2017/3/30.
 */

public class ChildView extends View {
    public static final String TAG = "TouchEvent";
    Paint paint = new Paint();

    private String name;

    public ChildView(Context context) {
        super(context);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        paint.setColor(0x33ff0000);
        canvas.drawColor(0x33ff0000);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, name + " dispatchTouchEvent: " + parseMotionEventAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, name + " onTouchEvent: " + parseMotionEventAction(event.getAction()));
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return false;
            case MotionEvent.ACTION_MOVE:
                return false;
            case MotionEvent.ACTION_UP:
                return true;
        }
        return super.onTouchEvent(event);
    }

    public static String parseMotionEventAction(int action) {
        switch (action) {
            case 0:
                return "ACTION_DOWN";
            case 1:
                return "ACTOIN_UP";
            case 2:
                return "ACTOIN_MOVE";
            case 3:
                return "ACTION_CANCEL";
            case 4:
                return "ACTION_OUTSIDE";
            default:
                return String.valueOf(action);
        }
    }
}
