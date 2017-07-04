package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model;

import java.util.List;

/**
 * Created by bdpqchen on 17-5-20.
 */

public class ThreadModel {

    private ThreadBean thread;
    private BoardBean board;
    private List<PostBean> post;

    public ThreadBean getThread() {
        return thread;
    }

    public void setThread(ThreadBean thread) {
        this.thread = thread;
    }

    public BoardBean getBoard() {
        return board;
    }

    public void setBoard(BoardBean board) {
        this.board = board;
    }

    public List<PostBean> getPost() {
        return post;
    }

    public void setPost(List<PostBean> post) {
        this.post = post;
    }

    public static class ThreadBean {

        private int id;
        private String title;
        private int author_id;
        private int anonymous;
        private String author_name;
        private String author_nickname;
        private int c_post;
        private int b_top;
        private int b_elite;
        private int visibility;
        private int t_reply;
        private int t_create;
        private int t_modify;
        private String content;
        private String content_converted;
        private int in_collection;

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

        public int getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(int author_id) {
            this.author_id = author_id;
        }

        public int getAnonymous() {
            return anonymous;
        }

        public void setAnonymous(int anonymous) {
            this.anonymous = anonymous;
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

        public int getC_post() {
            return c_post;
        }

        public void setC_post(int c_post) {
            this.c_post = c_post;
        }

        public int getB_top() {
            return b_top;
        }

        public void setB_top(int b_top) {
            this.b_top = b_top;
        }

        public int getB_elite() {
            return b_elite;
        }

        public void setB_elite(int b_elite) {
            this.b_elite = b_elite;
        }

        public int getVisibility() {
            return visibility;
        }

        public void setVisibility(int visibility) {
            this.visibility = visibility;
        }

        public int getT_reply() {
            return t_reply;
        }

        public void setT_reply(int t_reply) {
            this.t_reply = t_reply;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIn_collection() {
            return in_collection;
        }

        public void setIn_collection(int in_collection) {
            this.in_collection = in_collection;
        }

        public String getContent_converted() {
            return content_converted;
        }

        public void setContent_converted(String content_converted) {
            this.content_converted = content_converted;
        }
    }

    public static class BoardBean {
        /**
         * id : 159
         * forum_id : 30
         * forum_name : 知性感性
         * name : 求实焦点
         */

        private int id;
        private int forum_id;
        private String forum_name;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getForum_id() {
            return forum_id;
        }

        public void setForum_id(int forum_id) {
            this.forum_id = forum_id;
        }

        public String getForum_name() {
            return forum_name;
        }

        public void setForum_name(String forum_name) {
            this.forum_name = forum_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class PostBean {
        /**
         * id : 299394
         * author_id : 13202
         * author_name : lazierboy
         * author_nickname : 哈哈
         * content : 认识了好多人
         * 你最熟悉的ID是啥？
         * <p>
         * anonymous : 0
         * floor : 2
         * t_create : 1496237957
         * t_modify : 0
         */

        private int id;
        private int author_id;
        private String author_name;
        private String author_nickname;
        private String content;
        private String content_converted;
        private int anonymous;
        private int floor;
        private int t_create;
        private int t_modify;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getAnonymous() {
            return anonymous;
        }

        public void setAnonymous(int anonymous) {
            this.anonymous = anonymous;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent_converted() {
            return content_converted;
        }

        public void setContent_converted(String content_converted) {
            this.content_converted = content_converted;
        }
    }
}
