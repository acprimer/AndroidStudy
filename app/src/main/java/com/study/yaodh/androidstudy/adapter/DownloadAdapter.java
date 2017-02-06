package com.study.yaodh.androidstudy.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.model.PackageModel;

import java.util.List;
import java.util.Map;

/**
 * Created by yaodh on 2016/10/28.
 */

public class DownloadAdapter extends BaseAdapter {
    private Context mContext;
    private List<PackageModel> mPackages;
    private Map<Long, Integer> map;
    private Cursor mCursor;
    ButtonListener btnListener = new ButtonListener();

    public DownloadAdapter(Context context, List<PackageModel> packages, Map<Long, Integer> map, Cursor cursor) {
        mContext = context;
        mPackages = packages;
        this.map = map;
        mCursor = cursor;
    }

    @Override
    public int getCount() {
        return mPackages.size();
    }

    @Override
    public Object getItem(int position) {
        return mPackages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.download_item, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        TextView tvProgress = (TextView) view.findViewById(R.id.progress);

        final PackageModel model = mPackages.get(position);
        String title = model.getName();
        tvTitle.setText(title);
        long downloadId = model.getDownloadId();
        if (downloadId != 0 && map.containsKey(downloadId)) {
            mCursor.moveToPosition(map.get(downloadId));
            long totalBytes = mCursor.getLong(mCursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            long currentBytes = mCursor.getLong(mCursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            int progress = (int) (100.0 * currentBytes / totalBytes);
            tvProgress.setText(progress + "%");
            System.out.println(title + ": " + progress + "%");
        } else {
            tvProgress.setText("0");
        }

        Button btn = (Button) view.findViewById(R.id.download);
        btn.setTag(position);
        btn.setOnClickListener(btnListener);
        Log.d("listener", "getView: " + btnListener.toString());
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // download
//                long id = DownloadActivity.downloadPackage(mContext, model);
//                model.setDownloadId(id);
//            }
//        });
        return view;
    }

    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            Toast.makeText(mContext, "postion " + position, Toast.LENGTH_SHORT).show();
            Log.d("listener", "onClick: " + position);
        }
    }
}
