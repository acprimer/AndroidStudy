package com.study.yaodh.androidstudy.model;

/**
 * Created by yaodh on 2017/2/20.
 */

public class Feed {
    private long id;
    private String title;
    private String subtitle;

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
