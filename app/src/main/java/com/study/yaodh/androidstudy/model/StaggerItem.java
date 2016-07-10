package com.study.yaodh.androidstudy.model;

/**
 * Created by yaodh on 16/7/10.
 */
public class StaggerItem {
    private String title;
    private String img;

    public StaggerItem(String title, String img) {
        this.title = title;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
