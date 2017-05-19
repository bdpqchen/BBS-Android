package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

/**
 * Created by Ricky on 2017/5/16.
 */

public class MessageModel {

    /**
     * id : 34
     * content : Hello, world!
     * author_id : 21143
     * author_name : rickygao
     * author_nickname : rickygao
     * tag : 1
     * read : 0
     * t_create : 1495189939
     */

    private int id;
    private String content;
    private int author_id;
    private String author_name;
    private String author_nickname;
    private int tag;
    private int read;
    private int t_create;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_nickname() {
        return author_nickname;
    }

    public void setAuthor_nickname(String author_nickname) {
        this.author_nickname = author_nickname;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getT_create() {
        return t_create;
    }

    public void setT_create(int t_create) {
        this.t_create = t_create;
    }
}
