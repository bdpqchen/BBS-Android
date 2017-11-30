package com.twtstudio.bbs.bdpqchen.bbs.commons.utils

import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.*

/**
 * Created by bdpqchen on 17-8-19.
 */

object UrlUtil {

    private val BASE_URL = RxDoHttpClient.BASE_URL
    /**
     * Skip cache and to memory If the value is true
     */
    private val mDisableImageCache = PrefUtil.isDisabledImageCache()

    private val imageUrlSuffixStamp: String
        get() {
            return if (mDisableImageCache) "" else "?" + PrefUtil.getLastImageStamp().toString()
        }

    fun getAvatarUrl(uid: Int): String {
        return "$BASE_URL$USER/$uid/$AVATAR$imageUrlSuffixStamp"
    }

    fun getCoverUrl(fid: Int): String {
        return "$BASE_URL$FORUM/$fid/$COVER$imageUrlSuffixStamp"
    }

}
