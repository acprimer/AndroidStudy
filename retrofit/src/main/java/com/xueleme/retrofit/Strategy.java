package com.xueleme.retrofit;

/*
 * Created by yaodh on 2019/2/15.
 */
public class Strategy {

    /**
     * position : result
     * type : zhixuan
     * clientType : ANDROID
     * timeout : 2000
     * showTime : 0
     * priority : 10
     * available : true
     * createTime : 1515576695000
     * modifyTime : 1515576695000
     */

    private String position;
    private String type;
    private String clientType;
    private int timeout;
    private int showTime;
    private int priority;
    private boolean available;
    private long createTime;
    private long modifyTime;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getShowTime() {
        return showTime;
    }

    public void setShowTime(int showTime) {
        this.showTime = showTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }
}
