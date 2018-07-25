package com.twtstudio.bbs.bdpqchen.bbs.person

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel
import com.twtstudio.bbs.bdpqchen.bbs.people.PeopleModel


class PersonActivity : BaseActivity() , PersonContract.View {

    override fun getLayoutResourceId() = R.layout.activity_person

    override fun getToolbarView(): Toolbar? = null

    override fun getPresenter(): BasePresenter = mPresenter

    private val mPresenter = PersonPresenter(this)
    private lateinit var recyclerView: RecyclerView
    private var uid : Int = 0
    private val itemList : MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置浸入式状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }
//        hideBottomUIMenu()
        val actionBar = supportActionBar
        actionBar?.hide()
        uid = intent.getIntExtra("uid",0)
        mPresenter.getPersonInfo(uid)
        recyclerView = findViewById(R.id.rv_person) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onPersonInfoSuccess(person: PeopleModel) {
        itemList.add(PersonHeaderItem(person,this@PersonActivity,uid))
        itemList.add(SingleTextItem("最近动态"))
        recyclerView.withItems (itemList)
    }

    override fun onLoadFailed(info: String) {
        SnackBarUtil.error(this@PersonActivity,"加载失败，请检查网络设置")
    }

    override fun onThreadInfoSuccess(thread: List<ThreadModel.ThreadBean>) {
        itemList.addAll(thread.map { t -> IndThreadItem(t, this@PersonActivity, uid) })
        recyclerView.withItems(itemList)
    }

    override fun addFooter() {
//        if(itemList.size == 11){
//            itemList.add(SingleTextItem("没有更多帖子了（只展示最近十条）"))
//        } else {
//            itemList.add(SingleTextItem("没有更多帖子了"))
//        }
//        recyclerView.withItems(itemList)
    }

}
