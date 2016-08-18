package com.study.yaodh.androidstudy.db;

import com.study.yaodh.androidstudy.model.ThreadInfo;

import java.util.List;

/**
 * Created by yaodh on 2016/8/17.
 */
public interface IThreadDao {
    void insert(ThreadInfo info);
    void delete(String url);
    void update(String url, int thread_id, int progress);
    List<ThreadInfo> query(String url);
    boolean exists(String url, int thread_id);
}
