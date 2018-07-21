package com.twtstudio.bbs.bdpqchen.bbs.main.mainV3

import android.app.Activity
import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.banner.GlideImageLoader
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IsUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil
import com.twtstudio.bbs.bdpqchen.bbs.main.latest.LatestEntity
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import org.jetbrains.anko.layoutInflater
import org.jetbrains.annotations.NotNull

class MainV3Threadheader(val activity: Activity) : Item {


    companion object Controller : ItemController {
        private val images = mutableListOf(R.drawable.bbs_banner0,R.drawable.bbs_banner0,R.drawable.bbs_banner0)

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as MainV3Threadheader
            val banner = holder.banner
            banner.setImageLoader(GlideImageLoader())
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            banner.setImages(images)
            banner.setBannerAnimation(Transformer.DepthPage)
            banner.setDelayTime(2000)
            banner.setIndicatorGravity(BannerConfig.CENTER)
            banner.start()
            holder.noticeIv.setOnClickListener {
                item.activity.startActivity(IntentUtil.toAnnounce(item.activity))
            }
            holder.hotIv.setOnClickListener {
                item.activity.startActivity(IntentUtil.toHot(item.activity))
            }
            holder.rankIv.setOnClickListener {
                item.activity.startActivity(IntentUtil.toRank(item.activity))
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = parent.context.layoutInflater
            val view = inflater.inflate(R.layout.item_main_v3_header, parent, false)
            val noticeIv = view.findViewById<ImageView>(R.id.main_notice_iv)
            val activityIv = view.findViewById<ImageView>(R.id.main_activity_iv)
            val hotIv = view.findViewById<ImageView>(R.id.main_hot_iv)
            val rankIv = view.findViewById<ImageView>(R.id.main_rank_iv)
            val banner = view.findViewById<Banner>(R.id.main_v3_banner)
            return ViewHolder(view, banner, noticeIv, activityIv, hotIv, rankIv)
        }

    }

    class ViewHolder(@NotNull itemView: View, val banner: Banner, val noticeIv: ImageView, val activityIv: ImageView, val hotIv: ImageView, val rankIv: ImageView) : RecyclerView.ViewHolder(itemView)

    override val controller: ItemController
        get() = Controller
}

class MainV3ThreadItem(val latest: LatestEntity, val context: Context, val uid: Int) : Item {

    override val controller: ItemController
        get() = Controller

    companion object Controller : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as MainV3ThreadItem
            if (IsUtil.isAnon(item.latest.anonymous)) {
                item.latest.author_id = 0
                item.latest.author_name = Constants.ANONYMOUS_NAME
                holder.avatar.setOnClickListener(null)
                ImageUtil.loadAnonAvatar(item.context,holder.avatar)
            } else {
                holder.avatar.setOnClickListener {
                    val intent = IntentUtil.toPeople(item.context, item.uid)
                    item.context.startActivity(intent)
                }
                ImageUtil.loadAvatar(item.context, item.uid, holder.avatar)
            }
            holder.name.text = item.latest.author_name
            holder.threadType.text = item.latest.board_name
            holder.threadType.setOnClickListener {
                item.context.startActivity(IntentUtil.toThreadList(item.context, item.latest.board_id, item.latest.board_name, item.latest.anonymous))
            }
            holder.title.text = item.latest.title
            holder.card.setOnClickListener {
                item.context.startActivity(IntentUtil.toThread(item.context, item.latest.id, item.latest.title, item.latest.board_id, item.latest.board_name))
            }
            holder.time.text = StampUtil.getTimeFromNow(item.latest.t_create, item.latest.t_reply)
            holder.favorNum.text = "" + item.latest.like
            holder.commentNum.text = "" + item.latest.c_post
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val layoutInflater = parent.context.layoutInflater
            val view = layoutInflater.inflate(R.layout.item_main_v3_thread, parent, false)
            val card: CardView = view.findViewById(R.id.item_thread_v3_card)
            val avtar: ImageView = view.findViewById(R.id.item_main_thread_avatar)
            val name: TextView = view.findViewById(R.id.item_main_thread_username)
            val threadType: TextView = view.findViewById(R.id.item_main_thread_type)
            val title: TextView = view.findViewById(R.id.item_main_thread_title)
            val commentNum: TextView = view.findViewById(R.id.item_main_thread_comment_num)
            val favorNum: TextView = view.findViewById(R.id.item_main_thread_favor_num)
            val time: TextView = view.findViewById(R.id.item_main_thread_time)
            return ViewHolder(view, card, avtar, name, threadType, title, commentNum, favorNum, time)
        }

    }

    class ViewHolder(@NotNull itemView: View, val card: CardView, val avatar: ImageView, val name: TextView, val threadType: TextView,
                     val title: TextView, val commentNum: TextView, val favorNum: TextView,
                     val time: TextView) : RecyclerView.ViewHolder(itemView)
}