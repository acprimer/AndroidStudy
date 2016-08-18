package com.study.yaodh.androidstudy.model;

/**
 * Created by yaodh on 2016/8/17.
 */
public class ThreadInfo {
    private int id;
    private String url;
    private int start;
    private int end;
    private int progress;

    public ThreadInfo() {
    }

    public ThreadInfo(int id, String url, int start, int end) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
