package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import java.io.Serializable;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class BaseResponse<T> implements Serializable {

    private int err;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }
}
