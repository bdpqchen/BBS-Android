package com.twtstudio.bbs.bdpqchen.bbs.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_FORUM
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil


/**
 * Created by bdpqchen on 17-5-11.
 */

class ForumAdapter internal constructor(context: Context) : BaseAdapter<FlexBoardModel>(context) {

    init {
        mContext = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
//        return ForumVH(LayoutInflater.from(mContext).inflate(R.layout.item_forum, parent, false))
        return when (viewType) {
            ITEM_FORUM -> ForumVH(LayoutInflater.from(mContext).inflate(R.layout.item_forum_text, parent, false))
        //ITEM_BOARD
            else -> BoardVH(LayoutInflater.from(mContext).inflate(R.layout.view_board_text, parent, false))
        }
    }

    override fun onBindViewHolder(holder0: BaseViewHolder, position: Int) {
        if (mDataSet != null && mDataSet.size > 0) {
            if (holder0 is BoardVH) {
                val board = mDataSet[position]
                with(holder0) {
                    with(board) {
                        mBoardName.text = name
                        mBoardName.setOnClickListener({
                            mContext.startActivity(IntentUtil.toThreadList(mContext, id, name, canAnon))
                        })

                    }
                }
            } else if (holder0 is ForumVH) {
                val forum = mDataSet[position]
                with(holder0) {
                    with(forum) {
                        mForumName.text = name
                    }
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return mDataSet[position].type
    }

    internal class ForumVH(view: View) : BaseViewHolder(view) {
        val mForumName = view.findViewById<TextView>(R.id.tvForumName)
//        val mRvBoards = view.findViewById<RecyclerView>(R.id.rvBoards)
    }

    internal class BoardVH(view: View) : BaseViewHolder(view) {
        val mBoardName = view.findViewById<TextView>(R.id.tvBName)
        //        val mFlexBoxLayout = view.findViewById<FlexboxLayout>(R.id.flexBoxLayout)
//        val mRvBoards = view.findViewById<RecyclerView>(R.id.rvBoards)
    }

}
