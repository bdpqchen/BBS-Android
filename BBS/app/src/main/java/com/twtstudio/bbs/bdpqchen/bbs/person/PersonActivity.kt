package com.twtstudio.bbs.bdpqchen.bbs.person

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel
import com.twtstudio.bbs.bdpqchen.bbs.home.HomeContract
import com.twtstudio.bbs.bdpqchen.bbs.people.PeopleModel
import org.jetbrains.anko.activityManager

class PersonActivity : AppCompatActivity() , PersonContract.View {

    private val mPresenter = PersonPresenter(this)
    private lateinit var recyclerView: RecyclerView
    private var uid : Int = 0
    private lateinit var mContext: Context
    private val itemList : MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)
        uid = intent.getIntExtra("uid",0)
        mPresenter.getPersonInfo(uid)
        recyclerView = findViewById(R.id.rv_person) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this@PersonActivity,0))
    }

    override fun onPersonInfoSuccess(person: PeopleModel) {
        itemList.add(PersonHeaderItem(person,this@PersonActivity,uid))
        itemList.add(SingleTextItem("最近帖子"))
        recyclerView.withItems (itemList)
    }

    override fun onLoadFailed(info: String) {
        SnackBarUtil.error(this@PersonActivity,"加载失败，请检查网络设置")
    }

    override fun onThreadInfoSuccess(thread: ThreadModel.ThreadBean) {
        itemList.add(ThreadItem(thread,this@PersonActivity,uid))
        recyclerView.withItems(itemList)
    }

    override fun addFooter() {
        if(itemList.size == 11){
            itemList.add(SingleTextItem("没有更多帖子了（只展示最近十条）"))
        } else {
            itemList.add(SingleTextItem("没有更多帖子了"))
        }
        recyclerView.withItems(itemList)
    }

}
