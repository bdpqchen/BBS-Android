package com.twtstudio.bbs.bdpqchen.bbs.people;

import java.util.List;

/**
 * Created by bdpqchen on 17-7-3.
 */

public class PeopleModel {

    /**
     * name : bdpqchen
     * nickname : bdpqchen
     * signature : TwT-Android-BBS-砌墙
     * points : 44
     * c_online : 538
     * group : 1
     * t_create : 1492075618
     * c_thread : 2
     * c_post : 157
     * recent : [{"id":156128,"title":"【必要的更新提示】Android端新版本1.1发布","b_elite":0,"visibility":0,"t_create":"2017-06-10 13:12:56","t_reply":"2017-07-03 15:54:27"},{"id":155651,"title":"Android端bug收集反馈贴","b_elite":0,"visibility":0,"t_create":"2017-06-01 00:20:31","t_reply":"2017-07-03 06:37:24"}]
     */

    private String name;
    private String nickname;
    private String signature;
    private int points;
    private int c_online;
    private int group;
    private int t_create;
    private int c_thread;
    private int c_post;
    private List<RecentBean> recent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getC_online() {
        return c_online;
    }

    public void setC_online(int c_online) {
        this.c_online = c_online;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getT_create() {
        return t_create;
    }

    public void setT_create(int t_create) {
        this.t_create = t_create;
    }

    public int getC_thread() {
        return c_thread;
    }

    public void setC_thread(int c_thread) {
        this.c_thread = c_thread;
    }

    public int getC_post() {
        return c_post;
    }

    public void setC_post(int c_post) {
        this.c_post = c_post;
    }

    public List<RecentBean> getRecent() {
        return recent;
    }

    public void setRecent(List<RecentBean> recent) {
        this.recent = recent;
    }

    public static class RecentBean {
        /**
         * id : 156128
         * title : 【必要的更新提示】Android端新版本1.1发布
         * b_elite : 0
         * visibility : 0
         * t_create : 2017-06-10 13:12:56
         * t_reply : 2017-07-03 15:54:27
         */

        private int id;
        private String title;
        private int b_elite;
        private int visibility;
        private int t_create;
        private int t_reply;

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

        public int getT_reply() {
            return t_reply;
        }

        public void setT_reply(int t_reply) {
            this.t_reply = t_reply;
        }
    }
}
