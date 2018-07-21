package com.twtstudio.bbs.bdpqchen.bbs.forum2

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.ImageView
import android.widget.TextView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.google.android.flexbox.FlexboxLayout
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumBoardModel
import org.jetbrains.anko.dip
import org.jetbrains.anko.internals.AnkoInternals
import org.jetbrains.anko.layoutInflater
import org.jetbrains.annotations.NotNull

class ForumItem(val forumList: ForumBoardModel, val screenWidth: Int, val context: Context) : Item {

    companion object Controller : ItemController {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as ForumItem
            val context = item.context
            val width = item.screenWidth
            val tWidth = Math.ceil(width / 4.0).toInt()
            val tHeight = 48
            val boardNum = item.forumList.boardList.size
            val cWidth = width - 3 * tWidth
            val cHeight = Math.ceil(boardNum / 3.0).toInt() * tHeight
            holder.constraintLayout.layoutParams.height = context.dip(cHeight)
            holder.constraintLayout.layoutParams.width = context.dip(cWidth)
            holder.flexbox.removeAllViews()
            item.forumList.boardList.forEach {
                holder.flexbox.textView {
                    text = it.boardName
                    textSize = 14f
                    isClickable = true
                    isFocusable = true
                    foreground = resources.getDrawable(R.drawable.selector_foreground_settings)
                    background = resources.getDrawable(R.drawable.flex_item_decoration)
                }.apply {
                    gravity = Gravity.CENTER
                    layoutParams = FlexboxLayout.LayoutParams(context.dip(tWidth), context.dip(tHeight))
                }
            }
            holder.icon.setImageResource(getIconRes(item.forumList.fid))
            holder.boardName.text = item.forumList.forumName
            val temp: Int = if (item.forumList.fid == 33) 3 else 0
            val blankNum: Int = boardNum % 3 + temp
            for (i in 0 until blankNum) {
                holder.flexbox.textView { background = resources.getDrawable(R.drawable.flex_item_decoration) }.apply { layoutParams = FlexboxLayout.LayoutParams(context.dip(tWidth), context.dip(tHeight)) }
            }
            for (i in 0 until boardNum) {
                val boardModel: ForumBoardModel.BoardModel? = item.forumList.boardList[i]
                if (boardModel != null) {
                    holder.flexbox.getChildAt(i).setOnClickListener {
                        item.context.startActivity(IntentUtil.toThreadList(item.context, boardModel.bid, boardModel.boardName, boardModel.canAnon))
                    }
                } else {
                    holder.flexbox.getChildAt(i).setOnClickListener(null)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = parent.context.layoutInflater
            val view = inflater.inflate(R.layout.item_frag_forum, parent, false)
            val icon = view.findViewById<ImageView>(R.id.item_forum_icon)
            val boardName = view.findViewById<TextView>(R.id.item_forum_name)
            val constraint = view.findViewById<ConstraintLayout>(R.id.item_forum_constraint)
            val flexbox = view.findViewById<FlexboxLayout>(R.id.item_forum_flex_layout)
            return ViewHolder(view, icon, boardName, constraint, flexbox)
        }

        private fun getIconRes(index: Int): Int {
            return when (index) {
                28 -> R.drawable.entertainment
                29 -> R.drawable.art_and_culture
                30 -> R.drawable.emotion
                31 -> R.drawable.sport
                32 -> R.drawable.school_business
                33 -> R.drawable.tju
                34 -> R.drawable.station_management
                35 -> R.drawable.service_message
                else -> R.drawable.entertainment
            }
        }
    }

    class ViewHolder(@NotNull itemView: View, val icon: ImageView, val boardName: TextView, val constraintLayout: ConstraintLayout, val flexbox: FlexboxLayout) : RecyclerView.ViewHolder(itemView)

    override val controller
        get() = Controller


}

inline fun <T : View> ViewManager.ankoView(factory: (ctx: Context) -> T, theme: Int, init: T.() -> Unit): T {
    val ctx = AnkoInternals.wrapContextIfNeeded(AnkoInternals.getContext(this), theme)
    val view = factory(ctx)
    view.init()
    AnkoInternals.addView(this, view) // this.addView(view)
    return view
}

fun ViewManager.textView(init: TextView.() -> Unit): TextView {
    return ankoView({ TextView(it) }, theme = 0, init = init)
}