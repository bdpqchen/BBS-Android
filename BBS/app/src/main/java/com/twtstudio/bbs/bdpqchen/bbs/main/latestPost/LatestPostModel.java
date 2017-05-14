package com.twtstudio.bbs.bdpqchen.bbs.main.latestPost;

import java.util.List;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class LatestPostModel {
    /**
     * err : 0
     * data : {"latest":[{"id":2,"title":"再次控制台发帖","author_id":21142,"board_id":1,"author_name":"zhyupe12","author_nickname":"zhyupe12","b_top":0,"b_elite":0,"visibility":0,"t_create":1494720361,"t_modify":1494720361,"board_name":"这是一个子版块"},{"id":1,"title":"控制台发帖","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"aaaaaaaaa","b_top":0,"b_elite":0,"visibility":0,"t_create":1494719929,"t_modify":1494719929,"board_name":"这是一个子版块"}]}
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
        private List<LatestBean> latest;

        public List<LatestBean> getLatest() {
            return latest;
        }

        public void setLatest(List<LatestBean> latest) {
            this.latest = latest;
        }

        public static class LatestBean {
            /**
             * id : 2
             * title : 再次控制台发帖
             * author_id : 21142
             * board_id : 1
             * author_name : zhyupe12
             * author_nickname : zhyupe12
             * b_top : 0
             * b_elite : 0
             * visibility : 0
             * t_create : 1494720361
             * t_modify : 1494720361
             * board_name : 这是一个子版块
             */

            private int id;
            private String title;
            private int author_id;
            private int board_id;
            private String author_name;
            private String author_nickname;
            private int b_top;
            private int b_elite;
            private int visibility;
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
    }

/*
        private List<?> latest;
        private List<AnnounceBean> announce;

        public List<?> getLatest() {
            return latest;
        }

        public void setLatest(List<?> latest) {
            this.latest = latest;
        }

        public List<AnnounceBean> getAnnounce() {
            return announce;
        }

        public void setAnnounce(List<AnnounceBean> announce) {
            this.announce = announce;
        }

        public static class AnnounceBean {
            /**
             * id : 1
             * title : 这是一片公告
             * content : 这是公告内容
             * t_create : 1494382828


            private int id;
            private String title;
            private String content;
            private int t_create;

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

            public int getT_create() {
                return t_create;
            }

            public void setT_create(int t_create) {
                this.t_create = t_create;
            }
        }
*/

}
