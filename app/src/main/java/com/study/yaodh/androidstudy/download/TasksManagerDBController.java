package com.study.yaodh.androidstudy.download;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaodh on 2017/5/18.
 */

public class TasksManagerDBController {
    public static final String TABLE_NAME = "task";
    private final SQLiteDatabase db;

    public TasksManagerDBController(Context context) {
        TasksManagerDBOpenHelper helper = new TasksManagerDBOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<TaskModel> getAllTasks() {
        final Cursor cursor = db.rawQuery("SELECT * from " + TABLE_NAME, null);
        final List<TaskModel> list = new ArrayList<>();
        try {
            if (!cursor.moveToLast()) {
                return list;
            }
            do {
                TaskModel model = new TaskModel();
                model.id = cursor.getInt(cursor.getColumnIndex(TaskModel.ID));
                model.name = cursor.getString(cursor.getColumnIndex(TaskModel.NAME));
                model.url = cursor.getString(cursor.getColumnIndex(TaskModel.URL));
                model.path = cursor.getString(cursor.getColumnIndex(TaskModel.PATH));
                list.add(model);
            } while (cursor.moveToPrevious());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public TaskModel addTask(final String url, final String path) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null;
        }

        final int id = FileDownloadUtils.generateId(url, path);
        TaskModel model = new TaskModel(id, "task: " + id, url, path);
        final boolean succeed = db.insert(TABLE_NAME, null, model.toContentValues()) != -1;
        return succeed ? model : null;
    }
}
