package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.study.yaodh.androidstudy.R;

/*
 * Created by yaodh on 2018/10/18.
 */
public class AutoSizeTextView extends AppCompatTextView {
    // Temporary upper bounds on the starting text size
    private float mMaxTextSize;

    // Lower bounds for text size
    private float mMinTextSize;

    public AutoSizeTextView(Context context) {
        this(context, null);
    }

    public AutoSizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoSizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final float textSize = getTextSize();
//        mMaxTextSize = textSize;
//        mMinTextSize = textSize;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoSizeTextView);
            mMaxTextSize = typedArray.getDimension(R.styleable.AutoSizeTextView_maxTextSize, textSize);
            mMinTextSize = typedArray.getDimension(R.styleable.AutoSizeTextView_minTextSize, textSize);
//            String text = typedArray.getString(R.styleable.AutoSizeTextView_text);
//            setText(text);
            typedArray.recycle();
            resize();
        }

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        // 文本改变了之后，重新设置字体大小
        resize();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println("height " + getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        resize();
        super.onLayout(changed, left, top, right, bottom);
    }

    private void resize() {
        CharSequence text = getText();
        // Do not resize if the view does not have dimensions or there is no text
        if (TextUtils.isEmpty(text) || getMeasuredWidth() <= 0) {
            return;
        }
        // Get the text view's paint object
        TextPaint textPaint = getPaint();

        // Get max text size
        float targetTextSize = mMaxTextSize;

        int widthLimit = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        System.out.println("widthLimit " + widthLimit);
        // Until we either fit our lines or we had reached our min text size,
        // incrementally try smaller sizes
        while (targetTextSize > mMinTextSize
                && getTextLines(text, textPaint, widthLimit, targetTextSize) > 1) {
            targetTextSize = Math.max(targetTextSize - 2, mMinTextSize);
            System.out.println("getTextLines " + getTextLines(text, textPaint, widthLimit, targetTextSize)
                    + " " + targetTextSize);
        }

        // Some devices try to auto adjust line spacing, so force default line spacing
        // and invalidate the layout as a side effect
        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, targetTextSize);
        invalidate();
    }

    private int getTextLines(CharSequence source, TextPaint paint, int width, float textSize) {
        TextPaint paintCopy = new TextPaint(paint);
        paintCopy.setTextSize(textSize);
        StaticLayout layout = new StaticLayout(source, paintCopy, width, Layout.Alignment.ALIGN_NORMAL, 0, 0, true);
        return layout.getLineCount();
    }
}
