package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import com.orhanobut.hawk.Hawk;

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
    private static final String IS_LATEST_INFO = "is_latest_info";
    private static final String IS_RECEIVE_UNKNOWN_MESSAGE = "is_receive_unknown_message";
    //验证
    private static final String AUTH_USERNAME = "auth_username";
    private static final String AUTH_UID = "auth_uid";
    private static final String AUTH_TOKEN = "auth_token";
    private static final String AUTH_GROUP = "auth_group";
    //个人信息
    private static final String INFO_NICKNAME = "info_nickname";
    private static final String INFO_SIGNATURE = "info_signature";
    private static final String INFO_ONLINE = "info_online";
    private static final String INFO_CREATE = "info_create";
    private static final String INFO_LEVEL = "info_level";
    private static final String INFO_POINTS = "info_points";
    private static final String INFO_UNREAD = "info_unread";
    private static final String INFO_POST = "info_post";
    private static final String INFO_GROUP = "info_group";
    private static final String HAS_UN_SYNC_INFO = "has_un_sync_info";
    private static final String IS_FIRST_OPEN = "is_first_open";
    private static final String IS_ALWAYS_ANONYMOUS = "is_always_anonymous";
    private static final String HABIT_HAND = "habit_hand";
    private static final String IS_SIMPLE_BOARD_LIST = "is_simple_board_list";
    private static final String IS_THIS_VERSION_FIRST_OPEN = "is_this_version_first_open";
    private static final String LAST_IMAGE_STAMP = "last_image_stamp";
    private static final String DISABLED_IMAGE_CACHE = "is_disabled_image_cache";
    private static final String IS_UPDATE_AVAILABLE = "is_update_available";

    public static void setInfoGroup(int infoGroup) {
        Hawk.put(INFO_GROUP, infoGroup);
    }

    public static int getInfoGroup() {
        return Hawk.get(INFO_GROUP, 1);
    }

    public static void setAuthUsername(String username) {
        Hawk.put(AUTH_USERNAME, username);
    }

    public static String getAuthUsername() {
        return Hawk.get(AUTH_USERNAME, "");
    }

    public static boolean getIsLatestInfo() {
        return Hawk.get(IS_LATEST_INFO, false);
    }

    public static void setIsLatestInfo(boolean isLatestInfo) {
        Hawk.put(IS_LATEST_INFO, isLatestInfo);
    }

    public static void setInfoPost(int infoPost) {
        Hawk.put(INFO_POST, infoPost);
    }

    public static int getInfoPost() {
        return Hawk.get(INFO_POST, 0);
    }

    public static void setInfoUnread(int i) {
        Hawk.put(INFO_UNREAD, i);
    }

    public static int getInfoUnread() {
        return Hawk.get(INFO_UNREAD, 0);
    }

    public static void setInfoPoints(int infoPoints) {
        Hawk.put(INFO_POINTS, infoPoints);
    }

    public static int getInfoPoints() {
        return Hawk.get(INFO_POINTS, 0);
    }

    public static void setInfoLevel(int infoLevel) {
        Hawk.put(INFO_LEVEL, infoLevel);
    }

    public static int getInfoLevel() {
        return Hawk.get(INFO_LEVEL, 0);
    }

    public static void setInfoOnline(int i) {
        Hawk.put(INFO_ONLINE, i);
    }

    public static int getInfoOnline() {
        return Hawk.get(INFO_ONLINE, 0);
    }

    public static void setInfoCreate(int t) {
        Hawk.put(INFO_CREATE, t);
    }

    public static int getInfoCreate() {
        return Hawk.get(INFO_CREATE, 0);
    }

    public static void setInfoNickname(String s) {
        Hawk.put(INFO_NICKNAME, s);
    }

    public static String getInfoNickname() {
        return Hawk.get(INFO_NICKNAME, "");
    }

    public static void setInfoSignature(String s) {
        Hawk.put(INFO_SIGNATURE, s);
    }

    public static String getInfoSignature() {
        return Hawk.get(INFO_SIGNATURE, "");
    }

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

    public static void setAuthGroup(int i) {
        Hawk.put(AUTH_GROUP, i);
    }

    public static int getAuthGroup() {
        return Hawk.get(AUTH_GROUP, -9);
    }

    public static void setAuthToken(String token) {
        Hawk.put(AUTH_TOKEN, token);
    }

    public static String getAuthToken() {
        return Hawk.get(AUTH_TOKEN, "");
    }

    public static void setAuthUid(int i) {
        Hawk.put(AUTH_UID, i);
    }

    public static int getAuthUid() {
        return Hawk.get(AUTH_UID, 0);
    }


    public static boolean hasUnSyncInfo() {
        return Hawk.put(HAS_UN_SYNC_INFO, false);
    }

    public static void setHasUnSyncInfo(boolean b) {
        Hawk.get(HAS_UN_SYNC_INFO, b);
    }

    public static boolean isFirstOpen() {
        return Hawk.get(IS_FIRST_OPEN, true);
    }

    public static void setFirstOpen(boolean b) {
        Hawk.put(IS_FIRST_OPEN, b);
    }

    public static boolean isAlwaysAnonymous() {
        return Hawk.get(IS_ALWAYS_ANONYMOUS, false);
    }

    public static void setIsAlwaysAnonymous(boolean b) {
        Hawk.put(IS_ALWAYS_ANONYMOUS, b);
    }

    public static int getHabitHand() {
        return Hawk.get(HABIT_HAND, 0);
    }

    public static void setHabitHand(int i) {
        Hawk.put(HABIT_HAND, i);
    }

    public static boolean isSimpleBoardList() {
        return Hawk.get(IS_SIMPLE_BOARD_LIST, false);
    }

    public static void setIsSimpleBoardList(boolean b) {
        Hawk.put(IS_SIMPLE_BOARD_LIST, b);
    }

    public static boolean thisVersionFirstOpen() {
        return Hawk.get(IS_THIS_VERSION_FIRST_OPEN, true);
    }

    public static void setIsThisVersionFirstOpen(boolean b) {
        Hawk.put(IS_THIS_VERSION_FIRST_OPEN, b);
    }

    public static void setLastImageStamp(long time) {
        Hawk.put(LAST_IMAGE_STAMP, time);
    }

    public static long getLastImageStamp() {
        LogUtil.dd("PrefUtil", "getLastImageStamp");
        return Hawk.get(LAST_IMAGE_STAMP, System.currentTimeMillis());
    }

    public static boolean isDisabledImageCache() {
        return Hawk.get(DISABLED_IMAGE_CACHE, false);
    }

    public static void setDisabledImageCache(boolean b) {
        Hawk.put(DISABLED_IMAGE_CACHE, b);
    }

    public static boolean isUpdateAvailable(){
        return Hawk.get(IS_UPDATE_AVAILABLE, false);
    }
    public static void setUpdateAvailable(boolean b){
        Hawk.put(IS_UPDATE_AVAILABLE, b);
    }

}
