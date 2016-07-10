package com.study.yaodh.androidstudy.utils;

import android.content.Context;

/**
 * Created by yaodh on 16/7/10.
 */
public class Utils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
