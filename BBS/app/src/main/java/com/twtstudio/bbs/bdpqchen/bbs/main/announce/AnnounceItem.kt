package com.twtstudio.bbs.bdpqchen.bbs.main.announce

//import
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil
import org.jetbrains.anko.layoutInflater

class AnnounceItem(val announce: AnnounceEntity, val context: Context) : Item {

    companion object Controller : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as AnnounceItem
            holder.itemView.setOnClickListener {
                item.context.startActivity(IntentUtil.toThread(item.context, item.announce.id))
            }
            holder.title.text = item.announce.title
            holder.time.text = TextUtil.getThreadDateTime(item.announce.t_create, item.announce.t_modify)
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = parent.context.layoutInflater
            val view = inflater.inflate(R.layout.item_announce, parent, false)
            val title = view.findViewById<TextView>(R.id.announce_title)
            val time = view.findViewById<TextView>(R.id.announce_time)
            val content = view.findViewById<TextView>(R.id.announce_content)
            return ViewHolder(view, title, time, content)
        }

    }

    class ViewHolder(itemView: View, val title: TextView, val time: TextView, val content: TextView) : RecyclerView.ViewHolder(itemView)

    override val controller: ItemController
        get() = Controller
}