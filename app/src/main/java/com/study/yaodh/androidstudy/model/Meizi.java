package com.study.yaodh.androidstudy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yaodh on 16/7/10.
 */
public class Meizi {
    @SerializedName("desc")
    private String desc;
    @SerializedName("url")
    private String url;

    public Meizi(String title, String url) {
        this.desc = title;
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
