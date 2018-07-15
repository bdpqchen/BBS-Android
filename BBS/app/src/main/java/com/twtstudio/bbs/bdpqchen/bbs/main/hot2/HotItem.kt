package com.twtstudio.bbs.bdpqchen.bbs.main.hot2

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
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IsUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil
import com.twtstudio.bbs.bdpqchen.bbs.main.Hot
import org.jetbrains.anko.layoutInflater

class HotItem(val context: Context, val hot: Hot) : Item {

    companion object Controller : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as HotItem
            var hot = item.hot
            ImageUtil.loadAvatar(item.context, item.hot.author_id, holder.avatar)
            if (IsUtil.isAnon(item.hot.anonymous)) {
                hot.author_id = 0
                hot.author_name = Constants.ANONYMOUS_NAME
                holder.avatar.setOnClickListener(null)
            } else {
                holder.avatar.setOnClickListener {
                    val intent = IntentUtil.toPeople(item.context, hot.author_id)
                    item.context.startActivity(intent)
                }
            }
            holder.name.text = " " + hot.author_name
            holder.boardType.text = TextUtil.getBoardName(hot.board_name)
            holder.title.text = hot.title
            holder.content.text = hot.content
            holder.content.maxLines = 3
            holder.commentNum.text = "" + hot.c_post
            holder.favorNum.text = "" + hot.like
            holder.time.text = TextUtil.getThreadDateTime(hot.t_create, hot.t_modify)
            holder.card.setOnClickListener {
                item.context.startActivity(IntentUtil.toThread(item.context, hot.id))
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val layoutInflater = parent.context.layoutInflater
            val view = layoutInflater.inflate(R.layout.item_hot, parent, false)
            val card = view.findViewById<CardView>(R.id.item_hot_card)
            val avatar = view.findViewById<ImageView>(R.id.item_hot_avatar)
            val name = view.findViewById<TextView>(R.id.item_hot_name)
            val boardType = view.findViewById<TextView>(R.id.item_hot_type)
            val title = view.findViewById<TextView>(R.id.item_hot_title)
            val content = view.findViewById<TextView>(R.id.item_hot_content)
            val commentNum = view.findViewById<TextView>(R.id.item_hot_comment_num)
            val favorNum = view.findViewById<TextView>(R.id.item_hot_favor_num)
            val time = view.findViewById<TextView>(R.id.item_hot_time)
            return ViewHolder(view, card, avatar, name, boardType, title, content, commentNum, favorNum, time)
        }

    }

    class ViewHolder(itemView: View?, val card: CardView, val avatar: ImageView,
                     val name: TextView, val boardType: TextView, val title: TextView,
                     val content: TextView, val commentNum: TextView,
                     val favorNum: TextView, val time: TextView) : RecyclerView.ViewHolder(itemView)

    override val controller: ItemController
        get() = Controller
}