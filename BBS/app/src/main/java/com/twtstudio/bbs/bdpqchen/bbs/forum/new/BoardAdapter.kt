package com.twtstudio.bbs.bdpqchen.bbs.forum.new

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumBoardModel

/**
 * Created by bdpqchen on 17-11-5.
 *
 */
class BoardAdapter(context: Context, list: List<ForumBoardModel.BoardModel>) : BaseAdapter<ForumBoardModel.BoardModel>(context){
    init {
        mDataSet = list
    }
    private val mPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(mContext).inflate(R.layout.view_board_text, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        with(holder as VH){
            with(mDataSet[position]){
                tvBoardName.text = boardName
            }
        }
    }

    class VH(view : View) : BaseViewHolder(view) {
        val tvBoardName = view.findViewById<TextView>(R.id.tvBName)
    }


}