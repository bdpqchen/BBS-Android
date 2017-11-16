package com.bdpqchen.diffutilpractice

/**
 * Created by bdpqchen on 17-11-3.
 *
 */

abstract class DiffChecker2<T>(old: List<T>, new: List<T>) : DiffChecker1<T>(old, new) {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return _areItemsTheSame(oldItemPosition, newItemPosition)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return _areContentsTheSame(oldItemPosition, newItemPosition)
    }

    abstract fun _areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
    abstract override fun _areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean

}