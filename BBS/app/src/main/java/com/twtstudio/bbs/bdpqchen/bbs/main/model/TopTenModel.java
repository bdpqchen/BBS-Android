package com.twtstudio.bbs.bdpqchen.bbs.main.model;

import java.util.List;

/**
 * Created by zhangyulong on 5/13/17.
 */

public class TopTenModel extends LatestPostModel {

    private List<LatestBean> latest;
    private List<HotBean> hot;

    public List<LatestBean> getLatest() {
        return latest;
    }

    public void setLatest(List<LatestBean> latest) {
        this.latest = latest;
    }

    public List<HotBean> getHot() {
        return hot;
    }

    public void setHot(List<HotBean> hot) {
        this.hot = hot;
    }

    public static class LatestBean {
        /**
         * id : 7909
         * title : 我之前发的帖子被删了啊啊啊
         * author_id : 39531
         * board_id : 193
         * author_name : testuser
         * author_nickname : testuser
         * c_post : 0
         * b_top : 0
         * b_elite : 0
         * visibility : 0
         * t_reply : 1496020769
         * t_create : 1496020769
         * t_modify : 1496020993
         * board_name : 青年湖
         */

        private int id;
        private String title;
        private int author_id;
        private int board_id;
        private String author_name;
        private String author_nickname;
        private int c_post;
        private int b_top;
        private int b_elite;
        private int visibility;
        private int t_reply;
        private int t_create;
        private int t_modify;
        private String board_name;

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

        public int getBoard_id() {
            return board_id;
        }

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
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

        public String getBoard_name() {
            return board_name;
        }

        public void setBoard_name(String board_name) {
            this.board_name = board_name;
        }
    }

    public static class HotBean {
        /**
         * id : 6479
         * title : 本田CRV  和  奥迪 A4 买哪个呢?
         * author_id : 29227
         * board_id : 132
         * author_name : Dancingmiqi
         * author_nickname : Dancingmiqi
         * c_post : 107
         * b_top : 0
         * b_elite : 0
         * visibility : 0
         * t_reply : 1495924576
         * t_create : 1207616133
         * t_modify : 1207644933
         * content :
         * 小弟准备 ,买车,不知道选择哪种好呢?
         * <p>
         * board_name : 汽车
         */

        private int id;
        private String title;
        private int author_id;
        private int board_id;
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
        private String board_name;

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

        public int getBoard_id() {
            return board_id;
        }

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
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

        public String getBoard_name() {
            return board_name;
        }

        public void setBoard_name(String board_name) {
            this.board_name = board_name;
        }
    }
}
