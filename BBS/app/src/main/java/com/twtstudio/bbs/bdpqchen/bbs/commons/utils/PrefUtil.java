package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import com.orhanobut.hawk.Hawk;

import java.security.PublicKey;
import java.security.Signature;
import java.util.HashMap;

/**
 * Created by bdpqchen on 17-4-18.
 */

public final class PrefUtil {

    //
    private static final String IS_JUST_INSTALLED = "is_just_installed";
    private static final String IS_NIGHT_MODE = "is_night_mode";
    private static final String IS_SLIDE_BACK_MODE = "is_slide_back_mode";
    private static final String IS_AUTO_NIGHT_MODE = "is_auto_night_mode";
    private static final String HAD_LOGIN = "had_login";
    private static final String IS_NO_ACCOUNT_USER = "is_no_account_user";
    private static final String IS_NO_NETWORK_RECEIVE_MESSAGE = "is_no_network_receive_message";
    private static final String IS_RECEIVE_UNKNOWN_MESSAGE = "is_receive_unknown_message";
    private static final String NICKNAME = "nickname";
    private static final String SIGNATURE = "signature";
    private static final String AUTH_USERNAME = "auth_username";
    private static final String AUTH_UID = "auth_uid";
    private static final String AUTH_TOKEN = "auth_token";
    private static final String AUTH_GROUP = "auth_group";

//    private static final String INDIVIDUAL_


    public static boolean isJustInstalled() {
        return Hawk.get(IS_JUST_INSTALLED, false);
    }

    public static void setIsJustInstalled(boolean b) {
        Hawk.put(IS_JUST_INSTALLED, b);
    }

    public static boolean isNightMode() {
        return Hawk.get(IS_NIGHT_MODE, false);
    }

    public static void setIsNightMode(boolean b) {
        Hawk.put(IS_NIGHT_MODE, b);
    }

    public static boolean isSlideBackMode() {
        return Hawk.get(IS_SLIDE_BACK_MODE, true);
    }

    public static void setIsSlideBackMode(boolean b) {
        Hawk.put(IS_SLIDE_BACK_MODE, b);
    }

    public static boolean isAutoNightMode() {
        return Hawk.get(IS_AUTO_NIGHT_MODE, false);
    }

    public static void setIsAutoNightMode(boolean b) {
        Hawk.put(IS_AUTO_NIGHT_MODE, b);
    }

    public static boolean hadLogin() {
        return Hawk.get(HAD_LOGIN, false);
    }

    public static void setHadLogin(boolean b) {
        Hawk.put(HAD_LOGIN, b);
    }

    public static boolean isNoAccountUser() {
        return Hawk.get(IS_NO_ACCOUNT_USER, false);
    }

    public static void setIsNoAccountUser(boolean b) {
        Hawk.put(IS_NO_ACCOUNT_USER, b);
    }

    public static boolean isNoNetworkReceiveNewMessage() {
        return Hawk.get(IS_NO_NETWORK_RECEIVE_MESSAGE, true);
    }

    public static void setIsNoNetworkReceiveNewMessage(boolean b) {
        Hawk.put(IS_NO_NETWORK_RECEIVE_MESSAGE, b);
    }

    public static void setIsReceiveUnknownMessage(boolean b) {
        Hawk.put(IS_RECEIVE_UNKNOWN_MESSAGE, b);
    }

    public static boolean isReceiveUnknownMessage() {
        return Hawk.get(IS_RECEIVE_UNKNOWN_MESSAGE, true);
    }

    public static void setNickname(String s) {
        Hawk.put(NICKNAME, s);
    }

    public static String getNickname() {
        return Hawk.get(NICKNAME, "无");
    }

    public static void setSignature(String s) {
        Hawk.put(SIGNATURE, s);
    }

    public static String getSignature() {
        return Hawk.get(SIGNATURE, "无");
    }

    public static void setAuthGroup(String s) {
        Hawk.put(AUTH_GROUP, s);
    }

    public static String getAuthGroup() {
        return Hawk.get(AUTH_GROUP, "");
    }

    public static void setAuthToken(String token) {
        Hawk.put(AUTH_TOKEN, token);
    }

    public static String getAuthToken() {
        return Hawk.get(AUTH_TOKEN, "");
    }

    public static void setAuthUid(String s) {
        Hawk.put(AUTH_UID, s);
    }

    public static String getAuthUid() {
        return Hawk.get(AUTH_UID);
    }


}
