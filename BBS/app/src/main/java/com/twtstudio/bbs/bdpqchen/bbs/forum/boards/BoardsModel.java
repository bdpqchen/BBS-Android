package com.twtstudio.bbs.bdpqchen.bbs.forum.boards;

import java.util.List;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class BoardsModel {

    /**
     * forum : {"id":1,"name":"abcddddd","info":"g3rewhqrehqe","admin":"","c_board":0}
     * boards : [{"id":1,"name":"at238ti23yt","info":"greqhq34y34hqe","admin":"","c_thread":0,"visibility":0}]
     */

    private ForumBean forum;
    private List<BoardsBean> boards;

    public ForumBean getForum() {
        return forum;
    }

    public void setForum(ForumBean forum) {
        this.forum = forum;
    }

    public List<BoardsBean> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardsBean> boards) {
        this.boards = boards;
    }

    public static class ForumBean {
        /**
         * id : 1
         * name : abcddddd
         * info : g3rewhqrehqe
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

    public static class BoardsBean {
        /**
         * id : 1
         * name : at238ti23yt
         * info : greqhq34y34hqe
         * admin :
         * c_thread : 0
         * visibility : 0
         */

        private int id;
        private String name;
        private String info;
        private String admin;
        private int c_thread;
        private int anonymous;
        private int visibility;

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

        public int getC_thread() {
            return c_thread;
        }

        public void setC_thread(int c_thread) {
            this.c_thread = c_thread;
        }

        public int getVisibility() {
            return visibility;
        }

        public void setVisibility(int visibility) {
            this.visibility = visibility;
        }

        public int getAnonymous() {
            return anonymous;
        }

        public void setAnonymous(int anonymous) {
            this.anonymous = anonymous;
        }
    }
}
