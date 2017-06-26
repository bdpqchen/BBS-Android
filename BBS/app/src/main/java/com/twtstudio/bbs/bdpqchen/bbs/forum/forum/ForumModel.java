package com.twtstudio.bbs.bdpqchen.bbs.forum.forum;

/**
 * Created by bdpqchen on 17-5-8.
 */

public class ForumModel {

    /**
     * id : 1
     * name : forum_a
     * info : Some info
     * admin :
     * c_board : 0
     */

    private int id;
    private String name;
    private String info;
    private String admin;
    private int c_board;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public int getC_board() {
        return c_board;
    }

    public void setC_board(int c_board) {
        this.c_board = c_board;
    }
}
