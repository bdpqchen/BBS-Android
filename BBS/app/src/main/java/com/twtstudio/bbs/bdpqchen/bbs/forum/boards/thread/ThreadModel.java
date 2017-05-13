package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import java.util.List;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class ThreadModel {

    /**
     * board : {"id":1,"name":"at238ti23yt","info":"greqhq34y34hqe","admin":"","c_thread":0,"visibility":0}
     * thread : []
     */

    private BoardBean board;
    private List<?> thread;

    public BoardBean getBoard() {
        return board;
    }

    public void setBoard(BoardBean board) {
        this.board = board;
    }

    public List<?> getThread() {
        return thread;
    }

    public void setThread(List<?> thread) {
        this.thread = thread;
    }

    public static class BoardBean {
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
    }
}
