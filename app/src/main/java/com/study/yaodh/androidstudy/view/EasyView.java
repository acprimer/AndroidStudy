package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
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
        mPaint.setTextSize(60);
        mPaint.setAntiAlias(true);
//        mPaint.setColor(Color.RED);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(20);
//        mPaint.setAntiAlias(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText("Hello Android", 100, 100, mPaint);
        canvas.save();
        mPaint.setColor(0x33ff0000);
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{-1.f,0.f,500.f,0.f,1f,0f,0f,0f,1f});
        canvas.concat(matrix);
        canvas.drawText("Hello Android", 100, 100, mPaint);
        canvas.restore();
    }
}