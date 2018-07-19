package com.twtstudio.bbs.bdpqchen.bbs.message2

import android.content.Context
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.*
import com.twtstudio.bbs.bdpqchen.bbs.htmltextview.GlideImageGeter
import com.twtstudio.bbs.bdpqchen.bbs.htmltextview.HtmlTextView
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.layoutInflater

/**
 * Created by linjiaxin on 2018/7/18.
 */
class MessageItems(val context: Context, val message: MessageModel) : Item {
    companion object Controller : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as MessageItems
            var message = item.message
            ImageUtil.loadAvatarButDefault(item.context, message.author_id, holder.mAvatar)
            holder.mRedDot.visibility = if (message.read == 0) View.VISIBLE else View.GONE
            holder.mComposeTitle.text = message.author_name
            holder.mDatetime.text = StampUtil.getMessageDatetimeByStamp(message.t_create)
            holder.mAvatar.setOnClickListener { item.context.startActivity(IntentUtil.toPeople(item.context, message.author_id), TransUtil.getAvatarTransOptions(item.context, holder.mAvatar)) }
            if (message.tag == 1) {
                var content = if (message.content.length>15) message.content.substring(0,15)+"..." else message.content
                holder.mSummary.text = content
                holder.itemView.setOnClickListener {
                    holder.mRedDot.visibility = View.GONE
                    item.context.startActivity(IntentUtil.toLetter(item.context,message.author_id,message.author_name))
                }
            } else {
                val model = message.content_model
                var action = TextUtil.getMsgActionText(message.tag) + "了你"
                var content = TextUtil.formatContent(model.content, action)
                content = if (action == "提到了你")
                    " 在 " + model.thread_title + " 中" + action
                else
                    "<p>" + action + ":" + content.substring(3)
                holder.mSummary.setHtml(content, GlideImageGeter(item.context, holder.mSummary))
                holder.itemView.setOnClickListener {
                    holder.mRedDot.visibility = View.GONE
                    item.context.startActivity(IntentUtil.toThread(item.context, model.thread_id, model.thread_title, model.floor))
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val layoutInflater = parent.context.layoutInflater
            val view = layoutInflater.inflate(R.layout.item_rv_message_comment, parent, false)
            val summary = view.findViewById<HtmlTextView>(R.id.tv_summary)
            val dateTime = view.findViewById<TextView>(R.id.tv_datetime)
            val redDot = view.findViewById<View>(R.id.red_dot)
            val composeTitle = view.findViewById<TextView>(R.id.tv_compose_title)
            val avatar = view.findViewById<CircleImageView>(R.id.civ_message)
            return ViewHolder(view, summary, dateTime, redDot, composeTitle, avatar)
        }
    }


    class ViewHolder(itemView: View, val mSummary: HtmlTextView, val mDatetime: TextView, val mRedDot: View,
                     val mComposeTitle: TextView, val mAvatar: CircleImageView) : RecyclerView.ViewHolder(itemView)


    override val controller: ItemController
        get() = Controller

}