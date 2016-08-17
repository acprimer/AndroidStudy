package com.study.yaodh.androidstudy.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.study.yaodh.androidstudy.model.ThreadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaodh on 2016/8/17.
 */
public class ThreadDaoImpl implements IThreadDao {
    private DBHelper helper;

    public ThreadDaoImpl(Context context) {
        helper = new DBHelper(context);
    }

    @Override
    public void insert(ThreadInfo info) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id, url, start, end, progress) values(?,?,?,?,?)",
                new Object[]{info.getId(), info.getUrl(), info.getStart(), info.getEnd(), info.getProgress()});
        db.close();
    }

    @Override
    public void delete(String url, int thread_id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from thread_info where url=? and thread_id=?",
                new Object[]{url, thread_id});
        db.close();
    }

    @Override
    public void update(String url, int thread_id, int progress) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update thread_info set progress=? where url=? and thread_id=?",
                new Object[]{progress, url, thread_id});
        db.close();
    }

    @Override
    public List<ThreadInfo> query(String url) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url=?", new String[]{url});
        List<ThreadInfo> infos = new ArrayList<>();
        while(cursor.moveToNext()) {
            ThreadInfo info = new ThreadInfo();
            info.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            info.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            info.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            info.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            info.setProgress(cursor.getInt(cursor.getColumnIndex("progress")));
            infos.add(info);
        }
        cursor.close();
        db.close();
        return infos;
    }

    @Override
    public boolean exists(String url, int thread_id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url=?", new String[]{url});
        boolean exists = cursor.moveToNext();
        cursor.close();
        db.close();
        return exists;
    }
}
