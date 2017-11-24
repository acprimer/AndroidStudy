package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yaodh on 2017/11/23.
 */

public class BallView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private PointF position = new PointF();
    private int color = 0x3300FF00;
    private int radius = 60;

    public BallView(Context context) {
        super(context);
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        mPaint.setColor(color);

        canvas.drawCircle(paddingLeft + radius + position.x, paddingTop + radius + position.y, radius, mPaint);
    }
}
