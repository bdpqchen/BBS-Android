package com.twtstudio.bbs.bdpqchen.bbs.individual.letter;

/**
 * Created by bdpqchen on 17-7-4.
 */

public class LetterModel {

    /**
     * id : 13349
     * content : ttttt1
     * author_id : 17004
     * author_name : bdpqchen
     * author_nickname : bdpqchen
     * t_create : 1499086568
     */

    private int id;
    private String content;
    private int author_id;
    private String author_name;
    private String author_nickname;
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

    public int getT_create() {
        return t_create;
    }

    public void setT_create(int t_create) {
        this.t_create = t_create;
    }
}
