package com.study.yaodh.androidstudy.model;

import java.io.Serializable;

/**
 * Created by yaodh on 2016/8/17.
 */
public class PackageModel implements Serializable{
    private int id;
    private String icon;
    private String name;
    private String url;
    private int length;
    private int progress;

    public PackageModel() {
    }

    public PackageModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public PackageModel(int id, String icon, String name, String url) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "PackageModel{" +
                "id=" + id +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", length=" + length +
                ", progress=" + progress +
                '}';
    }
}
