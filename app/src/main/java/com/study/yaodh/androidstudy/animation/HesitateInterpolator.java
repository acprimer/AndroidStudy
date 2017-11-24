package com.study.yaodh.androidstudy.animation;

import android.view.animation.Interpolator;

/**
 * Created by yaodh on 2017/11/23.
 */

public class HesitateInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float t) {
        float x = 2.f * t - 1.f;
        return 0.5f * (x * x * x + 1.f);
    }
}
