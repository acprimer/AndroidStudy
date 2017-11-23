package com.study.yaodh.androidstudy.interpolator;

import android.view.animation.Interpolator;

/**
 * Created by yaodh on 2017/11/23.
 */

public class EggShakeInterpolator implements Interpolator {
    private static final int SHAKE_DURATION = 2400;
    private static final int WAIT_DURATION = 4000;
    private static final float SLICE = SHAKE_DURATION / 28.f;
    public static final int DURATION = SHAKE_DURATION + WAIT_DURATION;
    float S1 = 2 * SLICE;  // 阶段一，向左摆动一度，占有两个时间片
    float s1 = 12 * SLICE; // 阶段二，第一个循环 左右摆动一个来回，占有12个时间片
    float s2 = s1;              // 阶段二，第二个循环 左右摆动一个来回，占有12个时间片
    float S2 = s1 + s2;         // 阶段二，包含上面两个小阶段
    float S3 = S1;              //阶段三，向右摆动一度，占有两个时间片

    public EggShakeInterpolator() {
    }
    @Override
    public float getInterpolation(float input) {
        input = input * DURATION;
        float result = 0.f;
        if (input <= SHAKE_DURATION) {
            if (input <= S1) {
                double x = (input / S1 * Math.PI);//归一化处理
                result =  (float) Math.sin(Math.PI + x);
            } else if (input <= S1 + S2) {
                double x = (input - S1) / s1 * 2 * Math.PI;
                result =  (float) (7 * Math.sin(x));
            } else {
                double x = (input - S1 - S2) / S3 * Math.PI;
                result = (float) Math.sin(x);
            }
        }
        System.out.printf("getInterpolation %.3f\n", result);
        return result;
    }
}
