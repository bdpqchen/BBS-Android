package com.twtstudio.bbs.bdpqchen.bbs.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arsener on 2017/5/16.
 */

public class MyReleaseModle {
    public int err;

    private List<MyReleaseBean> data = new ArrayList<>();

    public List<MyReleaseBean> getData() {
        return data;
    }

    public void setData(List<MyReleaseBean> data) {
        this.data = data;
    }
}
