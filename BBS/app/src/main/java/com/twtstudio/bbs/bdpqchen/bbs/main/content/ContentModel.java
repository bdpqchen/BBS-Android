package com.twtstudio.bbs.bdpqchen.bbs.main.content;

import java.util.List;

/**
 * Created by zhangyulong on 5/19/17.
 */

public class ContentModel {

    /**
     * err : 0
     * data : {"thread":{"id":37,"title":"测试5/20/5","author_id":21142,"board_id":1,"author_name":"zhyupe12","author_nickname":"zhyupe12","c_post":2,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495204920,"t_create":1495204820,"t_modify":1495204820,"content":"55555\n","board_name":"这是一个子版块"},"post":[{"id":36,"author_id":21142,"author_name":"zhyupe12","author_nickname":"zhyupe12","content":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\n","floor":1,"t_create":1495204916,"t_modify":0},{"id":37,"author_id":21142,"author_name":"zhyupe12","author_nickname":"zhyupe12","content":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\n","floor":2,"t_create":1495204920,"t_modify":0}]}
     */

    private int err;
    private DataBean data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * thread : {"id":37,"title":"测试5/20/5","author_id":21142,"board_id":1,"author_name":"zhyupe12","author_nickname":"zhyupe12","c_post":2,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495204920,"t_create":1495204820,"t_modify":1495204820,"content":"55555\n","board_name":"这是一个子版块"}
         * post : [{"id":36,"author_id":21142,"author_name":"zhyupe12","author_nickname":"zhyupe12","content":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\n","floor":1,"t_create":1495204916,"t_modify":0},{"id":37,"author_id":21142,"author_name":"zhyupe12","author_nickname":"zhyupe12","content":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\n","floor":2,"t_create":1495204920,"t_modify":0}]
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
             * id : 37
             * title : 测试5/20/5
             * author_id : 21142
             * board_id : 1
             * author_name : zhyupe12
             * author_nickname : zhyupe12
             * c_post : 2
             * b_top : 0
             * b_elite : 0
             * visibility : 0
             * t_reply : 1495204920
             * t_create : 1495204820
             * t_modify : 1495204820
             * content : 55555

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

        public static class PostBean {
            /**
             * id : 36
             * author_id : 21142
             * author_name : zhyupe12
             * author_nickname : zhyupe12
             * content : 啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊

             * floor : 1
             * t_create : 1495204916
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

}
