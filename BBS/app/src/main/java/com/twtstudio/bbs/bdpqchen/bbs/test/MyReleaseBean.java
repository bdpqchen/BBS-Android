package com.twtstudio.bbs.bdpqchen.bbs.test;

/**
 * Created by Arsener on 2017/5/13.
 */

public class MyReleaseBean {

    MyReleaseBean(String title, int visit, String time){
        this.title = title;
        this.visit = visit;
        this.time = time;
    }

    MyReleaseBean(){

    }

    public String title;
    public int visit;
    public String time;
}
