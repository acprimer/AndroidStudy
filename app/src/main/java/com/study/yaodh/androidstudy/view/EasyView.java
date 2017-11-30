package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yaodh on 2017/11/30.
 */

public class EasyView extends View {
    Paint mPaint = new Paint();

    public EasyView(Context context) {
        this(context, null);
    }

    public EasyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setAntiAlias(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(300, 300, 200, mPaint);
    }
}