package com.twtstudio.bbs.bdpqchen.bbs.commons.support

import android.support.v7.util.DiffUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil

/**
 * Created by bdpqchen on 17-11-3.
 *
 */
class DiffChecker<T>(old: List<T>, new: List<T>) : DiffUtil.Callback() {

    private var mOld: List<T> = old
    private var mNew: List<T> = new

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val b = mOld[oldItemPosition]?.hashCode() == mNew[newItemPosition]?.hashCode()
        LogUtil.dd("areItemsTheSame", b)
        return b
    }

    override fun getOldListSize(): Int {
        return mOld.size
    }

    override fun getNewListSize(): Int {
        return mNew.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val b =  mOld[oldItemPosition]?.hashCode() == mNew[newItemPosition]?.hashCode()
        LogUtil.dd("areContentsTheSame", b)
        return b
    }

}