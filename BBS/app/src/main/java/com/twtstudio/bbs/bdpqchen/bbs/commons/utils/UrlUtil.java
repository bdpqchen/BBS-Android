package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.AVATAR;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.COVER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.FORUM;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.USER;

/**
 * Created by bdpqchen on 17-8-19.
 */

public final class UrlUtil {

    private static final String BASE_URL = RxDoHttpClient.BASE_URL;
    /**
     * Skip cache and to memory If the value is true
     */
    private static final boolean mDisableImageCache = PrefUtil.isDisabledImageCache();

    public static String getAvatarUrl(int uid) {
        return BASE_URL + USER + "/" + uid + "/" + AVATAR + getImageUrlSuffixStamp();
    }

    public static String getCoverUrl(int fid) {
        return BASE_URL + FORUM + "/" + fid + "/" + COVER + getImageUrlSuffixStamp();
    }

    private static String getImageUrlSuffixStamp() {
        LogUtil.dd("");
        return mDisableImageCache ? "" : "?" + String.valueOf(PrefUtil.getLastImageStamp());
    }

}
