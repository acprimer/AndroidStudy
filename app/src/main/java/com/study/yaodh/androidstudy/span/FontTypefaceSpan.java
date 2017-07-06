package com.study.yaodh.androidstudy.span;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

/**
 * Created by yaodh on 2017/5/19.
 */

public class FontTypefaceSpan extends TypefaceSpan {
    private Typeface newTypeface;

    public FontTypefaceSpan(Typeface typeface) {
        super("");
        newTypeface = typeface;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        apply(ds, newTypeface);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        apply(paint, newTypeface);
    }

    private static void apply(Paint paint, Typeface typeface) {
        int oldStyle;

        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }
        Typeface tf = Typeface.create(typeface, oldStyle);
        int fake = oldStyle & ~tf.getStyle();

        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(tf);
    }
}
