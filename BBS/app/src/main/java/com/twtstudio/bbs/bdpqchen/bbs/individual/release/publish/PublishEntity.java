package com.twtstudio.bbs.bdpqchen.bbs.individual.release.publish;

/**
 * Created by bdpqchen on 17-9-18.
 */

public class PublishEntity {

    /**
     * id : 164661
     * title : Android 端新版本v1.2.0发布
     * content : 还是有一部分机型的用户收不到更新提示，本次更新主要是，针对1.1.0版本部分机型用户收不到更新，从此换了一个平台，比较稳定。
     * 我们在渐渐完善求实BBS
     * 没有收到
     * t_reply : 2017-07-07 19:03:21
     * visibility : 0
     * b_elite : 0
     * c_post : 9
     * t_create : 1499102244
     * t_modify : 1499102416
     */

    private int id;
    private String title;
    private String content;
    private String t_reply;
    private int visibility;
    private int b_elite;
    private int c_post;
    private int t_create;
    private int t_modify;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getT_reply() {
        return t_reply;
    }

    public void setT_reply(String t_reply) {
        this.t_reply = t_reply;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getB_elite() {
        return b_elite;
    }

    public void setB_elite(int b_elite) {
        this.b_elite = b_elite;
    }

    public int getC_post() {
        return c_post;
    }

    public void setC_post(int c_post) {
        this.c_post = c_post;
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
