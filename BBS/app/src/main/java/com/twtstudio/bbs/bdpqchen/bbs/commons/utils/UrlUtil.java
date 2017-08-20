package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

/**
 * Created by bdpqchen on 17-8-19.
 */

public final class UrlUtil {

    private static final String BASE_URL = RxDoHttpClient.BASE_URL;

    public static String getAvatarUrl(int uid) {
        /*if (uid == 0){
            return String.valueOf(R.drawable.avatar_default_left);
        }
        */
        return BASE_URL + "user/" + uid + "/avatar";
    }

    public static String getCoverUrl(int fid) {
        return BASE_URL + "forum/" + fid + "/cover";
    }


}
