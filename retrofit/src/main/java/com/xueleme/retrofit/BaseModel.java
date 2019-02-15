package com.xueleme.retrofit;

/*
 * Created by yaodh on 2019/2/15.
 */
public class BaseModel<T> {
    private T data;
    private int status;
    private Object msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
