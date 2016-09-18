package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.study.yaodh.androidstudy.R;

/**
 * Created by yaodh on 16/8/28.
 */
public class CustomProgressbar extends ProgressBar {

    private int color;
    private float thickness;
    private RectF bounds;
    private Paint paint;
    int size;

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
        color = ta.getColor(R.styleable.CustomProgressbar_pb_color, getResources().getColor(R.color.colorAccent));
        thickness = ta.getDimension(R.styleable.CustomProgressbar_pb_thick, 4.0f);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(thickness);
        bounds = new RectF();
        setIndeterminate(true);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        size = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
        bounds.set(getPaddingLeft() + thickness,
                getPaddingTop() + thickness,
                size - getPaddingLeft() - thickness,
                size - getPaddingTop() - thickness);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float sweepAngle = getProgress() / getMax() * 360;
        canvas.drawArc(bounds, 0, sweepAngle, false, paint);
    }
}
