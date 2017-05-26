package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import java.util.List;

/**
 * Created by bdpqchen on 17-5-20.
 */

public class ThreadModel {

    /**
     * thread : {"id":3,"title":"《锦瑟》（唐）李商隐","author_id":21146,"board_id":1,"author_name":"EasonK","author_nickname":"EasonK","c_post":4,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495126386,"t_create":1494814381,"t_modify":1494814381,"content":"锦瑟无端五十弦，一弦一柱思华年。庄生晓梦迷蝴蝶，望帝春心托杜鹃。沧海月明珠有泪，兰田日暖玉生烟。此情可待成追忆，只是当时已惘然","board_name":"这是一个子版块"}
     * post : [{"id":7,"author_id":21146,"author_name":"EasonK","author_nickname":"EasonK","content":"无言独上西楼","floor":1,"t_create":1494820040,"t_modify":0},{"id":16,"author_id":21141,"author_name":"naiveuser","author_nickname":"asdafff","content":"寂寞梧桐深院锁清秋\n","floor":2,"t_create":1494888799,"t_modify":0},{"id":17,"author_id":21141,"author_name":"naiveuser","author_nickname":"asdafff","content":"[b]剪不断[/b]，[i]理还乱[/i]，[u]是离愁[/u]\n","floor":3,"t_create":1494888824,"t_modify":0},{"id":33,"author_id":21149,"author_name":"testadmin","author_nickname":"testadmin","content":"[b]韩国釜山广发华福[/b]\n","floor":4,"t_create":1495126386,"t_modify":0}]
     */

    private ThreadBean thread;
    private List<PostBean> post;

    public ThreadBean getThread() {
        return thread;
    }

    public void setThread(ThreadBean thread) {
        this.thread = thread;
    }

    public List<PostBean> getPost() {
        return post;
    }

    public void setPost(List<PostBean> post) {
        this.post = post;
    }

    public static class ThreadBean {
        /**
         * id : 3
         * title : 《锦瑟》（唐）李商隐
         * author_id : 21146
         * board_id : 1
         * author_name : EasonK
         * author_nickname : EasonK
         * c_post : 4
         * b_top : 0
         * b_elite : 0
         * visibility : 0
         * t_reply : 1495126386
         * t_create : 1494814381
         * t_modify : 1494814381
         * content : 锦瑟无端五十弦，一弦一柱思华年。庄生晓梦迷蝴蝶，望帝春心托杜鹃。沧海月明珠有泪，兰田日暖玉生烟。此情可待成追忆，只是当时已惘然
         * board_name : 这是一个子版块
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

    public static class PostBean{
        /**
         * id : 7
         * author_id : 21146
         * author_name : EasonK
         * author_nickname : EasonK
         * content : 无言独上西楼
         * floor : 1
         * t_create : 1494820040
         * t_modify : 0
         */

        private int id;
        private int author_id;
        private String author_name;
        private String author_nickname;
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
}
