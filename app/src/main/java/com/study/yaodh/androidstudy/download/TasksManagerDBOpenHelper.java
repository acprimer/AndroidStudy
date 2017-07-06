package com.study.yaodh.androidstudy.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yaodh on 2017/5/18.
 */

public class TasksManagerDBOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "task.db";
    public static final int DATABASE_VERSION = 1;

    public TasksManagerDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TasksManagerDBController.TABLE_NAME
                + String.format(
                "("
                        + "%s INTEGER PRIMARY KEY, " // id, download id
                        + "%s VARCHAR, " // name
                        + "%s VARCHAR, " // url
                        + "%s VARCHAR " // path
                        + ")"
                , TaskModel.ID
                , TaskModel.NAME
                , TaskModel.URL
                , TaskModel.PATH

        ));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
