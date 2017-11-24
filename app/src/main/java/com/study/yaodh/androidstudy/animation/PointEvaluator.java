package com.study.yaodh.androidstudy.animation;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by yaodh on 2017/11/23.
 */

public class PointEvaluator implements TypeEvaluator<PointF> {
    private PointF point = new PointF();

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float x = startValue.x + (endValue.x - startValue.x) * fraction;
        float y = startValue.y + (endValue.y - startValue.y) * fraction;

        point.set(x, y);
        return point;
    }
}
