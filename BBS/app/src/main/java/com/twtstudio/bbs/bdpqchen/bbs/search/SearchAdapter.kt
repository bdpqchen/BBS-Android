package com.twtstudio.bbs.bdpqchen.bbs.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MODE_SEARCH_THREAD
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MODE_SEARCH_USER
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil
import com.twtstudio.bbs.bdpqchen.bbs.search.model.AdapterModel
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by bdpqchen on 17-10-21.
 */
class SearchAdapter(val context: Context) : BaseAdapter<AdapterModel>(context) {


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (mDataSet != null && mDataSet.size > 0) {
            if (holder is UserViewHolder) {
//                val holder = holder
                val entity = mDataSet[position]
                holder.tvName.text = entity.name
                ImageUtil.loadAvatarAsBitmapByUidWithLeft(mContext, entity.id, holder.civAvatar)
                holder.itemView.setOnClickListener({
                    mContext.startActivity(IntentUtil.toPeople(mContext, entity.id))
                })
            } else if (holder is ThreadViewHolder) {

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder? {
        val view: View?
        if (viewType == MODE_SEARCH_USER) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_search_user, parent, false)
            return UserViewHolder(view)
        } else if (viewType == MODE_SEARCH_THREAD) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_search_thread, parent, false)
            return ThreadViewHolder(view)
        }
        return null
    }

    override fun getItemViewType(position: Int): Int {
        return mDataSet[position].mode
    }

    class UserViewHolder(val view: View) : BaseViewHolder(view) {
        val tvName = view.findViewById(R.id.tv_search_name) as TextView
        val civAvatar = view.findViewById(R.id.civ_search_avatar) as CircleImageView
    }

    class ThreadViewHolder(val view: View) : BaseViewHolder(view) {
        val tvTitle = view.findViewById(R.id.tv_search_title)
        val tvAuthorAndTime = view.findViewById(R.id.tv_search_author_time)
        val civAvatar = view.findViewById(R.id.civ_search_thread_avatar)
    }

}