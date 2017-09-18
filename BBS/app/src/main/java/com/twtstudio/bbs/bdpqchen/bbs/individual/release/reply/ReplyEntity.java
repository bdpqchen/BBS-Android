package com.twtstudio.bbs.bdpqchen.bbs.individual.release.reply;

/**
 * Created by bdpqchen on 17-9-18.
 */

public class ReplyEntity {

    /**
     * id : 336966
     * thread_id : 169696
     * thread_title : 用一个成语来形容你的家乡吧
     * c_post : 54
     * content : 66的
     * > 回复 #39 Halcao :
     * >
     * > 天地之中
     * floor : 45
     * anonymous : 0
     * t_create : 1505730102
     * t_modify : 0
     */

    private int id;
    private int thread_id;
    private String thread_title;
    private int c_post;
    private String content;
    private int floor;
    private int anonymous;
    private int t_create;
    private int t_modify;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public String getThread_title() {
        return thread_title;
    }

    public void setThread_title(String thread_title) {
        this.thread_title = thread_title;
    }

    public int getC_post() {
        return c_post;
    }

    public void setC_post(int c_post) {
        this.c_post = c_post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(int anonymous) {
        this.anonymous = anonymous;
    }

    public int getT_create() {
        return t_create;
    }

    public void setT_create(int t_create) {
        this.t_create = t_create;
    }

    public int getT_modify() {
        return t_modify;
    }

    public void setT_modify(int t_modify) {
        this.t_modify = t_modify;
    }
}
