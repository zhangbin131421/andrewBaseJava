package com.andrew.java.library.model;

/**
 * author: zhangbin
 * created on: 2021/7/1 14:25
 * description:
 */
public class AndrewResponse<T> {
    private int code;
    private T data;
    private String msg;

    public AndrewResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
