package com.study.yaodh.androidstudy.utils;

import android.content.Context;
import android.content.Intent;

import com.study.yaodh.androidstudy.activity.DrawerActivity;
import com.study.yaodh.androidstudy.activity.ImageViewActivity;
import com.study.yaodh.androidstudy.activity.ListViewLoader;
import com.study.yaodh.androidstudy.activity.NotificationActivity;
import com.study.yaodh.androidstudy.activity.ProgressbarActivity;
import com.study.yaodh.androidstudy.activity.RecyclerViewActivity;
import com.study.yaodh.androidstudy.activity.ShareActivity;
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
    /**
     * start recyclerView activity
     * @param context
     */
    public static void startRecyclerViewActivity(Context context) {
        Intent intent = new Intent(context, RecyclerViewActivity.class);
        context.startActivity(intent);
    }
    /**
     * start recyclerView activity
     * @param context
     */
    public static void startDrawerActivity(Context context) {
        Intent intent = new Intent(context, DrawerActivity.class);
        context.startActivity(intent);
    }

    /**
     * start recyclerView activity
     * @param context
     */
    public static void startProgressbarActivity(Context context) {
        Intent intent = new Intent(context, ProgressbarActivity.class);
        context.startActivity(intent);
    }

    /**
     * start recyclerView activity
     * @param context
     */
    public static void startListActivity(Context context) {
        Intent intent = new Intent(context, ListViewLoader.class);
        context.startActivity(intent);
    }

    /**
     * start share activity
     * @param context
     */
    public static void startShareActivity(Context context) {
        Intent intent = new Intent(context, ShareActivity.class);
        context.startActivity(intent);
    }

    /**
     * start imageview activity
     * @param context
     */
    public static void startImageViewActivity(Context context) {
        Intent intent = new Intent(context, ImageViewActivity.class);
        context.startActivity(intent);
    }

}
