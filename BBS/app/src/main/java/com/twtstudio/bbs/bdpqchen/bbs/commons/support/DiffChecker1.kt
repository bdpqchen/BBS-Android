package com.bdpqchen.diffutilpractice

import android.support.v7.util.DiffUtil

/**
 * Created by bdpqchen on 17-11-3.
 *
 */
abstract class DiffChecker1<T>(old: List<T>, new: List<T>) : DiffUtil.Callback() {

    private var mOld: List<T> = old
    private var mNew: List<T> = new

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOld[oldItemPosition]?.hashCode() == mNew[newItemPosition]?.hashCode()
    }

    override fun getOldListSize(): Int {
        return mOld.size
    }

    override fun getNewListSize(): Int {
        return mNew.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return _areContentsTheSame(oldItemPosition, newItemPosition)
    }

    abstract fun _areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean

}