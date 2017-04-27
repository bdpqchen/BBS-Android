package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import java.io.Serializable;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class RxBaseResponse<T> implements Serializable {

    private int error_code;

    private String msg;

    private T data;


    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
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
