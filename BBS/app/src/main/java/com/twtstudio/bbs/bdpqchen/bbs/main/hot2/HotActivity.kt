package com.twtstudio.bbs.bdpqchen.bbs.main.hot2

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.main.Hot
import kotterknife.bindView

class HotActivity : BaseActivity(), HotContract.View {

    override fun getLayoutResourceId() = R.layout.activity_hot

    override fun getToolbarView() = null

    override fun getPresenter() = mPresenter

    private val swipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.hot_srl)
    private val mPresenter = HotPresenter(this)
    private val backIv: ImageView by bindView(R.id.activity_hot_ic_back)
    private val recyclerView: RecyclerView by bindView(R.id.hot_rv)
    private val layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView.layoutManager = layoutManager
        mPresenter.getHotList()
        backIv.setOnClickListener {
            onBackPressed()
        }
        swipeRefreshLayout.setOnRefreshListener {
            mPresenter.getHotList()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onGetHotListSuccess(hotList: List<Hot>) {
        val temp = mutableListOf<Item>()
        temp.addAll(hotList.map { t -> HotItem(this, t) })
        recyclerView.withItems(temp)
    }

    override fun onGetHotListFailed(msg: String) {
        SnackBarUtil.error(this, msg)
    }


}
