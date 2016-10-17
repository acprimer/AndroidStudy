package com.example.download;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by yaodh on 2016/10/17.
 */

public class DownloadNotification {

    Context mContext;
    HashMap<String, NotificationItem> mNotifications;

    static class NotificationItem {
        int mId;
        long mTotalCurrent = 0;
        long mTotalTotal = 0;
        int mTitleCount = 0;
        String mPackageName;
        String mDescription;
        String[] mTitles = new String[2];
        String pausedText = null;

        void addItem(String title, long currentBytes, long totalBytes) {
            mTotalCurrent += currentBytes;
            if(totalBytes <= 0 || mTotalTotal == -1) {
                mTotalTotal = -1;
            } else {
                mTotalTotal += totalBytes;
            }
            if(mTitleCount < 2) {
                mTitles[mTitleCount] = title;
            }
            mTitleCount++;
        }
    }
}
