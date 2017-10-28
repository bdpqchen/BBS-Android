package com.twtstudio.bbs.bdpqchen.bbs.search

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.*
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.*
import com.twtstudio.bbs.bdpqchen.bbs.search.model.CommonModel
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchThreadModel
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchUserModel
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by bdpqchen on 17-10-21.
 */
class SearchAdapter(val mContext: Context, private val mUserClickListener: OnUserItemClick) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mUserDataSet = ArrayList<SearchUserModel>()
    private var mThreadDataSet = ArrayList<SearchThreadModel>()
    private var mCommonDataSet = ArrayList<CommonModel>()
    private var mUserHiding = ArrayList<SearchUserModel>()

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (mCommonDataSet.size > 0) {
            when (holder) {
                is UserViewHolder -> {
                    val entity = mUserDataSet[mCommonDataSet[position].position]
                    holder.tvName.text = entity.name
                    ImageUtil.loadAvatarAsBitmapByUidWithLeft(mContext, entity.id, holder.civAvatar)
                    holder.itemView.setOnClickListener({
                        mUserClickListener.onClick(position)
                    })
                }
                is ThreadViewHolder -> {
                    val entity = mThreadDataSet[mCommonDataSet[position].position]
                    with(holder, { with(entity, {
                            tvTitle.text = title
                            if (IsUtil.is1(anonymous)) {
                                author_name = ANONYMOUS_NAME
                                author_id = 0
                            }
                            tvAuthorAndTime.text = author_name + "  创建于 " + StampUtil.getDatetimeByStamp(t_create)
                            ImageUtil.loadAvatarAsBitmapByUidWithLeft(mContext, author_id, civAvatar)
                            holder.itemView.setOnClickListener({ mContext.startActivity(IntentUtil.toThread(mContext, id, title)) })
                        })
                    })
                }
                is HeaderViewHolder -> {
                    val entity = mCommonDataSet[position]
                    var title = "  用户"
                    if (entity.type == ITEM_SEARCH_THREAD_HEADER) {
                        title = "  帖子"
                    }
                    holder.tvTitle.text = title
                }
                is HidingViewHolder -> {
                    holder.itemView.setOnClickListener({ addMoreUser() })
                }
                is DividerViewHolder -> {
                    LogUtil.dd("create divider view holder")
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder? {
        val layoutInflater = LayoutInflater.from(mContext)
        when (viewType) {
            ITEM_SEARCH_USER ->
                return UserViewHolder(layoutInflater.inflate(R.layout.item_search_user, parent, false))
            ITEM_SEARCH_THREAD ->
                return ThreadViewHolder(layoutInflater.inflate(R.layout.item_search_thread, parent, false))
            ITEM_SEARCH_THREAD_HEADER, ITEM_SEARCH_USER_HEADER ->
                return HeaderViewHolder(layoutInflater.inflate(R.layout.item_search_header, parent, false))
            ITEM_SEARCH_USER_HIDING ->
                return HidingViewHolder(layoutInflater.inflate(R.layout.item_search_hiding, parent, false))
            ITEM_SEARCH_DIVIDER ->
                return DividerViewHolder(layoutInflater.inflate(R.layout.item_search_divider, parent, false))
        }
        return null
    }

    private fun addMoreUser() {
        var pos = MAX_SEARCH_RESULT_USER
        mCommonDataSet.removeAt(pos + 1)
        mCommonDataSet.addAll(pos + 1, mUserHiding.map({ CommonModel(ITEM_SEARCH_USER, pos++) }))
        mUserDataSet.addAll(mUserHiding)
        notifyDataSetChanged()
    }

    fun addUserList(list: List<SearchUserModel>) {
        mUserDataSet.addAll(0, list)
        notifyDataSetChanged()
    }

    fun addThreadList(list: List<SearchThreadModel>) {
        mThreadDataSet.addAll(list)
        notifyDataSetChanged()
    }

    fun addCommonList(list: List<CommonModel>, addFirst: Boolean = false) {
        if (addFirst){
            mCommonDataSet.addAll(0, list)
        }else{
            mCommonDataSet.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addHidingList(hidingList: List<SearchUserModel>) {
        mUserHiding.addAll(hidingList)
    }

    fun getThreadNextPosition(): Int{
        return mThreadDataSet.size
    }

    fun getUser(position: Int): SearchUserModel{
        return mUserDataSet[position]
    }

    fun getUserUid(position: Int): Int {
        return mUserDataSet[position].id
    }

    override fun getItemViewType(position: Int): Int {
        val entity = mCommonDataSet[position]
        return entity.type
    }

    override fun getItemCount(): Int {
        return mCommonDataSet.size
    }

    class UserViewHolder(val view: View) : BaseViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_search_name)
        val civAvatar: CircleImageView = view.findViewById(R.id.civ_search_avatar)
    }

    class ThreadViewHolder(val view: View) : BaseViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_search_title)
        val tvAuthorAndTime: TextView = view.findViewById(R.id.tv_search_author_time)
        val civAvatar: CircleImageView = view.findViewById(R.id.civ_search_thread_avatar)
    }

    class HeaderViewHolder(val view: View) : BaseViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_search_header_name)
    }

    class DividerViewHolder(val view: View) : BaseViewHolder(view)
    class HidingViewHolder(val view: View) : BaseViewHolder(view)

    fun clearDataSet() {
//        removeAllDataSet()
//        return
        mUserDataSet.clear()
        mUserHiding.clear()
        mThreadDataSet.clear()
        mCommonDataSet.clear()
        notifyDataSetChanged()

    }

}