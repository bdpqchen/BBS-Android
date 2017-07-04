package com.twtstudio.bbs.bdpqchen.bbs.individual.friend;

/**
 * Created by bdpqchen on 17-6-29.
 */

public class FriendModel {
    /**
     * uid : 18480
     * status : 1
     * name : testuser
     * nickname : ttt
     * signature : hahaha
     */

    private int uid;
    private int status;
    private String name;
    private String nickname;
    private String signature;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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
}
