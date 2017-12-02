package com.twtstudio.bbs.bdpqchen.bbs.commons.utils

/**
 * Created by bdpqchen on 17-9-17.
 */

object IsUtil {

    @JvmStatic
    fun is1(status: Int): Boolean {
        return status == 1
    }

    @JvmStatic
    fun is0(status: Int): Boolean {
        return status == 0
    }

    @JvmStatic
    fun isAnon(status: Int) : Boolean{
        return status == 1
    }

}
