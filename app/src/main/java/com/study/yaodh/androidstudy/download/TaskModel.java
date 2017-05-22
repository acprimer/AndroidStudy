package com.study.yaodh.androidstudy.download;

import android.content.ContentValues;

/**
 * Created by yaodh on 2017/5/18.
 */

public class TaskModel {
    public static final String ID = "id", NAME = "name", URL = "url", PATH = "path";
    public int id;
    public String name, url, path;

    public TaskModel() {
    }

    public TaskModel(int id, String name, String url, String path) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.path = path;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ID, id);
        cv.put(NAME, name);
        cv.put(URL, url);
        cv.put(PATH, path);
        return cv;
    }
}
