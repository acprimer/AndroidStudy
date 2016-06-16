package com.study.yaodh.androidstudy.utils;

import android.content.Context;
import android.content.Intent;

import com.study.yaodh.androidstudy.activity.NotificationActivity;
import com.study.yaodh.androidstudy.activity.TextViewActivity;

/**
 * Created by yaodh on 16/5/12.
 */
public class IntentManager {

    /**
     * start textview activity
     * @param context
     */
    public static void startTextViewActivity(Context context) {
        Intent intent = new Intent(context, TextViewActivity.class);
        context.startActivity(intent);
    }

    /**
     * start textview activity
     * @param context
     */
    public static void startNotificationActivity(Context context) {
        Intent intent = new Intent(context, NotificationActivity.class);
        context.startActivity(intent);
    }

}
