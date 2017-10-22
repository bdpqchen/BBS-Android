package com.twtstudio.bbs.bdpqchen.bbs.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MODE_SEARCH_THREAD
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MODE_SEARCH_USER
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IsUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchThreadModel
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchUserModel
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by bdpqchen on 17-10-21.
 */
//class SearchAdapter(val context: Context) : BaseAdapter<AdapterModel>(context) {
class SearchAdapter(val mContext: Context, val mode: Int) : RecyclerView.Adapter<BaseViewHolder>() {

    //    private val mContext = context
    private var mUserDataSet = ArrayList<SearchUserModel>()
    private var mThreadDataSet = ArrayList<SearchThreadModel>()

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (mUserDataSet.size > 0 || mThreadDataSet.size > 0) {
            if (holder is UserViewHolder) {
                val entity = mUserDataSet[position]
                holder.tvName.text = entity.name
                ImageUtil.loadAvatarAsBitmapByUidWithLeft(mContext, entity.id, holder.civAvatar)
                holder.itemView.setOnClickListener({
                    mContext.startActivity(IntentUtil.toPeople(mContext, entity.id))
                })
            } else if (holder is ThreadViewHolder) {
                val entity = mThreadDataSet[position]
                holder.tvTitle.text = entity.title
                if (IsUtil.is1(entity.anonymous)) {
                    entity.author_name = Constants.ANONYMOUS_NAME
                    entity.author_id = 0
                }
                holder.tvAuthorAndTime.text = entity.author_name + "  创建于 " + StampUtil.getDatetimeByStamp(entity.t_create)
                ImageUtil.loadAvatarAsBitmapByUidWithLeft(mContext, entity.author_id, holder.civAvatar)
                holder.itemView.setOnClickListener({ mContext.startActivity(IntentUtil.toThread(mContext, entity.id, entity.title)) })
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
        return mode
    }

    override fun getItemCount(): Int {
        if (mode == MODE_SEARCH_USER) {
            return mUserDataSet.size
        } else if (mode == MODE_SEARCH_THREAD) {
            return mThreadDataSet.size
        }
        return 0
    }

    class UserViewHolder(val view: View) : BaseViewHolder(view) {
        val tvName = view.findViewById(R.id.tv_search_name) as TextView
        val civAvatar = view.findViewById(R.id.civ_search_avatar) as CircleImageView
    }

    class ThreadViewHolder(val view: View) : BaseViewHolder(view) {
        val tvTitle = view.findViewById(R.id.tv_search_title) as TextView
        val tvAuthorAndTime = view.findViewById(R.id.tv_search_author_time) as TextView
        val civAvatar = view.findViewById(R.id.civ_search_thread_avatar) as CircleImageView
    }

    fun addUserList(list: List<SearchUserModel>) {
        mUserDataSet.addAll(list)
        notifyDataSetChanged()
    }

    fun addThreadList(list: List<SearchThreadModel>) {
        mThreadDataSet.addAll(list)
        notifyDataSetChanged()
    }


}