package com.xueleme.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
 * Created by yaodh on 2019/2/15.
 */
public class StrategyData {
    private List<Strategy> result;
    private List<Strategy> offline;
    @SerializedName("copy_trans")
    private List<Strategy> copyTrans;
    private List<Strategy> infoline;
    private List<Strategy> splash;

    public List<Strategy> getResult() {
        return result;
    }

    public void setResult(List<Strategy> result) {
        this.result = result;
    }

    public List<Strategy> getOffline() {
        return offline;
    }

    public void setOffline(List<Strategy> offline) {
        this.offline = offline;
    }

    public List<Strategy> getCopyTrans() {
        return copyTrans;
    }

    public void setCopyTrans(List<Strategy> copyTrans) {
        this.copyTrans = copyTrans;
    }

    public List<Strategy> getInfoline() {
        return infoline;
    }

    public void setInfoline(List<Strategy> infoline) {
        this.infoline = infoline;
    }

    public List<Strategy> getSplash() {
        return splash;
    }

    public void setSplash(List<Strategy> splash) {
        this.splash = splash;
    }
}
