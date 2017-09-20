package com.twtstudio.bbs.bdpqchen.bbs.auth.retrieve;

/**
 * Created by bdpqchen on 17-5-21.
 */

public class RetrieveModel {

    /**
     * uid : UID
     * username : 用户名
     * token : 用于重置密码的token
     */

    private String uid;
    private String username;
    private String token;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
