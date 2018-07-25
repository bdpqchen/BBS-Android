package com.twtstudio.bbs.bdpqchen.bbs.commons

import android.app.Activity
import android.content.Context
import cn.edu.twt.retrox.recyclerviewdsl.Item
import com.twtstudio.bbs.bdpqchen.bbs.main.latest.LatestEntity
import com.twtstudio.bbs.bdpqchen.bbs.main.mainV3.MainV3ThreadItem
import com.twtstudio.bbs.bdpqchen.bbs.main.mainV3.MainV3Threadheader
import com.twtstudio.bbs.bdpqchen.bbs.person.SingleTextItem

fun MutableList<Item>.latestThread(latest: LatestEntity, context: Context, uid: Int) = add(MainV3ThreadItem(latest, context, uid))

fun MutableList<Item>.singleText(content: String) = add(SingleTextItem(content))

fun MutableList<Item>.lastestItem(latest: LatestEntity, context: Context) = add(MainV3ThreadItem(latest, context, latest.author_id))