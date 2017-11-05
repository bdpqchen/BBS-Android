package com.twtstudio.bbs.bdpqchen.bbs.forum

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder
import com.twtstudio.bbs.bdpqchen.bbs.forum.new.BoardAdapter


/**
 * Created by bdpqchen on 17-5-11.
 */

class ForumAdapter internal constructor(context: Context) : BaseAdapter<ForumBoardModel>(context) {

    init {
        mContext = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_forum, parent, false)
        val holder = ViewHolder(view)

        return holder
    }

    override fun onBindViewHolder(holder0: BaseViewHolder, position: Int) {
        if (mDataSet != null && mDataSet.size > 0) {
            if (holder0 is ViewHolder) {
                val forum = mDataSet[position]
                with(holder0) {
                    with(forum) {
                        mForumName.text = forumName
                        mRvBoards.layoutManager = FlexboxLayoutManager(mContext, FlexDirection.ROW)
                        mRvBoards.adapter = BoardAdapter(mContext, forum.boardList)
                    }
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    internal class ViewHolder(view: View) : BaseViewHolder(view) {
        val mForumName = view.findViewById<TextView>(R.id.tvFName)
        //        val mFlexBoxLayout = view.findViewById<FlexboxLayout>(R.id.flexBoxLayout)
        val mRvBoards = view.findViewById<RecyclerView>(R.id.rvBoards)
    }

}
