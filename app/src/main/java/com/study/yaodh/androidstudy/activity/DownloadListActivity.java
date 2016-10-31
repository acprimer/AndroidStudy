package com.study.yaodh.androidstudy.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.databinding.DataBindingUtil;
import android.os.Handler;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.DownloadAdapter;
import com.study.yaodh.androidstudy.databinding.ActivityDownloadListBinding;
import com.study.yaodh.androidstudy.model.PackageModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadListActivity extends BaseActivity {
    private ActivityDownloadListBinding binding;

    private Cursor cursor;
    private MyContentObserver myContentObserver;
    private MyDataSetObserver myDataSetObserver;
    private Map<Long, Integer> map = new HashMap<>();
    private DownloadAdapter mAdapter;

    @Override
    protected void initContent() {
        super.initContent();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_download_list);

        final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterByStatus(DownloadManager.STATUS_RUNNING | DownloadManager.STATUS_SUCCESSFUL);
        cursor = manager.query(query);
        List<PackageModel> list = new ArrayList<>();
        list.add(new PackageModel("网易云音乐", "http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk"));
        list.add(new PackageModel("collins", "http://codown.youdao.com/dictmobile/dictlib/hindict/collins.dat"));
        list.add(new PackageModel("sents", "http://codown.youdao.com/dictmobile/dictlib/hindict/auth_sents_part.dat"));
        list.add(new PackageModel("ee", "http://codown.youdao.com/dictmobile/dictlib/hindict/ee.dat"));
        list.add(new PackageModel("hindi", "http://codown.youdao.com/dictmobile/dictlib/hindict/enhi.dat"));
        mAdapter = new DownloadAdapter(this, list, map, cursor);
//        DownloadCursorAdapter cursorAdapter = new DownloadCursorAdapter(this, cursor);
//        binding.downloadList.setAdapter(cursorAdapter);
        binding.downloadList.setAdapter(mAdapter);

        cursor.registerContentObserver(myContentObserver = new MyContentObserver());
        cursor.registerDataSetObserver(myDataSetObserver = new MyDataSetObserver());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.unregisterContentObserver(myContentObserver);
        cursor.unregisterDataSetObserver(myDataSetObserver);
    }

    private class MyContentObserver extends ContentObserver {
        public MyContentObserver() {
            super(new Handler());
        }

        @Override
        public void onChange(boolean selfChange) {
            System.out.println("content observer onChange");
            cursor.requery();
        }
    }

    private class MyDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            System.out.println("data set onChange");
            int position = 0;
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
                map.put(id, position++);
            }
            mAdapter.notifyDataSetChanged();
//            while(cursor.moveToNext()) {
//                long id = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
//                int position = map.get(id);
//                mAdapter.notifyDataSetChanged();
//            }
        }
    }
}
