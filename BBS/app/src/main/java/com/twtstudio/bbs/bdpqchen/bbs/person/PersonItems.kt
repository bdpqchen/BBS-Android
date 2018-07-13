package com.twtstudio.bbs.bdpqchen.bbs.person

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel
import com.twtstudio.bbs.bdpqchen.bbs.people.PeopleModel
import org.jetbrains.anko.layoutInflater

class PersonHeaderItem(val people : PeopleModel, val context: Context, val uid : Int) : Item{

    companion object Controller : ItemController{

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as PersonHeaderItem
            ImageUtil.loadAvatar(item.context,item.uid,holder.userAvatarIv)
            ImageUtil.loadBgByUid(item.context,item.uid,holder.background)
            holder.backArrowIv.setOnClickListener{
                val ac = item.context as Activity
                ac.onBackPressed()
//                Toast.makeText(item.context,"返回键暂时不能用",Toast.LENGTH_LONG).show()
            }
            holder.userNameTv.text = TextUtil.getTwoNames(item.people.name,item.people.nickname)
            holder.userSignTv.text = TextUtil.getUserSignature(item.people.signature)
            holder.userPointTv.text = ""+ item.people.points
            holder.userThreadTV.text = "" + item.people.c_thread
            holder.userAgeTv.text = "" + item.people.c_online
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = parent.context.layoutInflater
            val view = inflater.inflate(R.layout.item_ind_header ,parent,false)
            val background : ImageView = view.findViewById(R.id.ind_cover)
            val backArrowIv = view.findViewById<ImageView>(R.id.item_ind_header_back_arrow)
            val userAvatarIv = view.findViewById<ImageView>(R.id.ind_ac_avatar)
            val userNameTv = view.findViewById<TextView>(R.id.ind_ac_username)
            val userLevelTv = view.findViewById<TextView>(R.id.ind_ac_userlevel)
            val userSignTv = view.findViewById<TextView>(R.id.ind_ac_user_sign)
            val userPointTv = view.findViewById<TextView>(R.id.ind_ac_points)
            val userThreadTv = view.findViewById<TextView>(R.id.ind_ac_threads)
            val userAgeTv = view.findViewById<TextView>(R.id.ind_ac_station_age)
            return ViewHolder(view,background ,backArrowIv,userAvatarIv,userNameTv,userLevelTv,userSignTv,userPointTv,userThreadTv,userAgeTv)
        }

    }

    private class ViewHolder(itemView : View?,val background:ImageView, val backArrowIv: ImageView, val userAvatarIv : ImageView,
                             val userNameTv: TextView, val userLevelTv:TextView, val userSignTv:TextView,
                             val userPointTv: TextView,val userThreadTV:TextView, val userAgeTv:TextView
                             ) : RecyclerView.ViewHolder(itemView)

    override val controller: ItemController
        get() = Controller
}

class SingleTextItem(val content : String):Item{

    companion object Controller : ItemController{

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as SingleTextItem
            holder.singleText.text = item.content
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = parent.context.layoutInflater
            val view = inflater.inflate(R.layout.item_single_text,parent,false)
            val singleText = view.findViewById<TextView>(R.id.single_text)
            return ViewHolder(view,singleText)
        }

    }

    class ViewHolder(itemView: View?, val singleText:TextView):RecyclerView.ViewHolder(itemView)

    override val controller: ItemController
        get() = Controller
}

class IndThreadItem(val threadBean: ThreadModel.ThreadBean, val context: Context, val uid: Int) : Item {

    companion object Controller : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as IndThreadItem
            val threadBean = item.threadBean
            ImageUtil.loadAvatar(item.context, item.uid, holder.threadAvatarIv)
            holder.threadNameTv.text = " " + threadBean.author_name + " 发布了帖子"
            holder.threadTitleTv.text = threadBean.title
            holder.threadContentTv.text = threadBean.content
            holder.threadContentTv.maxLines = 3
            holder.threadCommentNumTv.text = "" + threadBean.c_post
            holder.threadFavorNumTv.text = "" + threadBean.like
            holder.threadTimeTv.text = TextUtil.getThreadDateTime(threadBean.t_create,threadBean.t_modify)
            holder.itemView.setOnClickListener {
                item.context.startActivity(IntentUtil.toThread(item.context,threadBean.id))
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val layoutInflater = parent.context.layoutInflater
            val view = layoutInflater.inflate(R.layout.item_thread_indiviual,parent,false)
            val threadAvatarIv = view.findViewById<ImageView>(R.id.ind_item_avatar)
            val threadNameTv = view.findViewById<TextView>(R.id.ind_item_name)
            val threadTitleTv = view.findViewById<TextView>(R.id.ind_item_title)
            val threadContentTv = view.findViewById<TextView>(R.id.ind_item_content)
            val threadCommentNumTv = view.findViewById<TextView>(R.id.ind_item_comment_num)
            val threadFavorNumTv = view.findViewById<TextView>(R.id.ind_item_favor_num)
            val threadTimeTv = view.findViewById<TextView>(R.id.ind_item_time)
            return ViewHolder(view,threadAvatarIv,threadNameTv,threadTitleTv,threadContentTv,threadCommentNumTv,threadFavorNumTv,threadTimeTv)
        }

    }

    class ViewHolder(itemView: View?, val threadAvatarIv : ImageView,val threadNameTv : TextView,val threadTitleTv : TextView,
                       val threadContentTv : TextView, val threadCommentNumTv : TextView, val threadFavorNumTv : TextView,
                       val threadTimeTv : TextView) : RecyclerView.ViewHolder(itemView)

    override val controller: ItemController
        get() = Controller
}