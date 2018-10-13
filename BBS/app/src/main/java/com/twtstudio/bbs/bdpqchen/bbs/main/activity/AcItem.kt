package com.twtstudio.bbs.bdpqchen.bbs.main.activity

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.twtstudio.bbs.bdpqchen.bbs.main.AcEntity

class AcItem(val context: Context,val data:AcEntity) : Item {
    override val controller: ItemController
        get() = Controller

    companion object Controller : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {

        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            return ViewHolder(View(parent.context))
        }

    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

}