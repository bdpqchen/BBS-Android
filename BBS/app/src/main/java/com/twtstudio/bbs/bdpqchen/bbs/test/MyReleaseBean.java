package com.twtstudio.bbs.bdpqchen.bbs.test;

/**
 * Created by Arsener on 2017/5/13.
 */

public class MyReleaseBean {

    MyReleaseBean(String title, int visit, String time){
        this.title = title;
        this.c_post = visit;
        this.t_create = time;
    }

    MyReleaseBean(){

    }

    /**
     * id : 主题帖ID
     * title : 标题
     * t_reply : 最后回帖时间
     * c_post : 回帖量
     * visibility : 可见性
     * b_elite : 是否为精品帖
     * t_create : 发帖时间
     * t_modify : 修改时间
     */

    public String id;
    public String title;
    public String t_reply;
    public int c_post;
    public boolean visibility;
    public String b_elite;
    public String t_create;
    public String t_modify;
}
