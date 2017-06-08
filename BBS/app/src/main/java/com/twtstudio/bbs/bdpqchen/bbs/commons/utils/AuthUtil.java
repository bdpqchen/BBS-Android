package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

/**
 * Created by bdpqchen on 17-6-8.
 */

public final class AuthUtil {

    public static void logout(){
        PrefUtil.setHadLogin(false);
        PrefUtil.setAuthToken("");
//        PrefUtil.setAuthUsername("");
        PrefUtil.setAuthGroup(0);
//        PrefUtil.setAuthUid(0);
        PrefUtil.setInfoNickname("");
        PrefUtil.setInfoSignature("");
        PrefUtil.setInfoCreate(0);
        PrefUtil.setInfoPoints(0);
        PrefUtil.setInfoUnread(0);
        PrefUtil.setHasUnSyncInfo(false);

    }
}
