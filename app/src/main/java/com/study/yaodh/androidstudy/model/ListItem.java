package com.study.yaodh.androidstudy.model;

/**
 * Created by yaodh on 2016/7/6.
 */
public class ListItem {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM   = 1;

    public int type;
    public String title;
    public String content;

    public ListItem(int type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
