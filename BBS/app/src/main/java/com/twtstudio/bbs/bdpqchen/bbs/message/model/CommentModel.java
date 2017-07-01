package com.twtstudio.bbs.bdpqchen.bbs.message.model;

/**
 * Created by bdpqchen on 17-5-28.
 */

public class CommentModel {

    /**
     * id : 218
     * thread_id : 194
     * thread_title : 正经测试帖
     * content : 最正经的测试回复回复回复回复，还要测试长度真的很长长长长长长长长长
     * floor : 4
     * t_create : 1495963849
     * t_modify : 0
     */

    private int id;
    private int thread_id;
    private String thread_title;
    private String content;
    private int floor;
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
