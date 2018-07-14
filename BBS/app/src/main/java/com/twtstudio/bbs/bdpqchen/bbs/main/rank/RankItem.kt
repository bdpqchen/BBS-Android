package com.twtstudio.bbs.bdpqchen.bbs.main.rank

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil
import com.twtstudio.bbs.bdpqchen.bbs.main.Rank
import org.jetbrains.anko.layoutInflater

class RankItem(val rank: Rank, val context: Context, val index: Int, val type: Int) : Item {

    companion object Controller : ItemController {

        val RANK_WEEK = 0
        val RANK_MONTH = 1

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as RankItem
            holder.index.text = "" + item.index
            ImageUtil.loadAvatar(item.context, item.rank.id, holder.avatar)
            holder.name.text = item.rank.name
            holder.level.text = TextUtil.getHonor(item.rank.points)
            holder.point.text = "" + item.rank.points
            holder.weekOrMonthPoint.text = "" + item.rank.points_inc
            holder.weekOrMonthTV.text = when (item.type) {
                RANK_WEEK -> "周积分"
                RANK_MONTH -> "月积分"
                else -> ""
            }
            setTextColorByIndex(item.index, holder.index, holder.name, holder.weekOrMonthPoint)
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = parent.context.layoutInflater
            val view = inflater.inflate(R.layout.item_rank, parent, false)

            val index = view.findViewById<TextView>(R.id.rank_item_index)
            val avatar = view.findViewById<ImageView>(R.id.rank_item_avatar)
            val name = view.findViewById<TextView>(R.id.rank_item_username)
            val level = view.findViewById<TextView>(R.id.rank_item_user_level)
            val point = view.findViewById<TextView>(R.id.item_rank_user_point)
            val weekOrMonthPoint = view.findViewById<TextView>(R.id.item_rank_week_point)
            val weekOrMonthTV = view.findViewById<TextView>(R.id.item_rank_week_or_month)

            return ViewHolder(view, index, avatar, name, level, point, weekOrMonthPoint, weekOrMonthTV)
        }


        fun setTextColorByIndex(index: Int, vararg textViews: TextView) = when (index) {
            1 -> textViews.forEach { it.setTextColor(Color.parseColor("#e90f06")) }
            2 -> textViews.forEach { it.setTextColor(Color.parseColor("#f2680f")) }
            3 -> textViews.forEach { it.setTextColor(Color.parseColor("#fac517")) }
            else -> textViews.forEach { it.setTextColor(Color.parseColor("#434343")) }
        }

    }

    class ViewHolder(itemView: View, val index: TextView, val avatar: ImageView, val name: TextView,
                     val level: TextView, val point: TextView, val weekOrMonthPoint: TextView,
                     val weekOrMonthTV: TextView) : RecyclerView.ViewHolder(itemView)

    override val controller: ItemController
        get() = Controller

}