package com.study.yaodh.androidstudy.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

/**
 * Created by yaodh on 2016/10/28.
 */

public class DownloadAdapter extends CursorAdapter {
    private Context mContext;
    private Cursor mCursor;

    private int mIdColumnId;
    private int mTitleColumnId;
    private int mStatusColumnId;
    private int mTotalBytesColumnId;
    private int mCurrentBytesColumnId;

    public DownloadAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
        mCursor = cursor;

        mIdColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_ID);
        mTitleColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TITLE);
        mStatusColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS);
        mTotalBytesColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
        mCurrentBytesColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(mContext).inflate(R.layout.download_item, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String title = mCursor.getString(mTitleColumnId);
        long totalBytes = mCursor.getLong(mTotalBytesColumnId);
        long currentBytes = mCursor.getLong(mCurrentBytesColumnId);

        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        tvTitle.setText(title);
        TextView tvProgress = (TextView) view.findViewById(R.id.progress);
        int progress = (int) (100.0 * currentBytes / totalBytes);
        tvProgress.setText(progress + "%");
        System.out.println(title + ": " + progress + "%");
    }
}
