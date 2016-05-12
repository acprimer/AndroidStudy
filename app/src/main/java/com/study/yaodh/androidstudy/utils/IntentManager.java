package com.study.yaodh.androidstudy.utils;

import android.content.Context;
import android.content.Intent;

import com.study.yaodh.androidstudy.TextViewActivity;

/**
 * Created by yaodh on 16/5/12.
 */
public class IntentManager {

    /**
     * start textview activity
     * @param context
     * @param url
     */
    public static void startTextViewActivity(Context context) {
        Intent intent = new Intent(context, TextViewActivity.class);
        context.startActivity(intent);
    }

}
