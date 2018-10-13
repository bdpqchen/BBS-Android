package com.twtstudio.bbs.bdpqchen.bbs.main.mainV3

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemAdapter
import cn.edu.twt.retrox.recyclerviewdsl.ItemManager
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.main.latest.LatestEntity
import kotterknife.bindView

class MainFragmentV3 : SimpleFragment(), MainV3Contract.View {

    private var latestList: MutableList<MainV3ThreadItem> = mutableListOf()
    private val swipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.main_srl)
    val recyclerView: RecyclerView by bindView(R.id.fragment_main_v3_rv)
    private val searchIv: ImageView by bindView(R.id.main_thread_search)
    private val mPresenter = MainV3Presenter(this)
    private var mPage = 0
    private var isRefreshing = false
    private var latestVisibleItem = 0
    private lateinit var itemManager: ItemManager
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun getPerMainFragmentLayoutId(): Int = R.layout.fragment_main_v3

    override fun initFragments() {
        searchIv.setOnClickListener { startActivity(IntentUtil.toSearch(mContext)) }

        swipeRefreshLayout.setOnRefreshListener {
            isRefreshing = true
            refresh()
        }

        itemManager = ItemManager()
        linearLayoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = ItemAdapter(itemManager)
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                latestVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && latestVisibleItem + 1 == recyclerView.adapter.itemCount) {
                    loadMore()
                }
            }
        })
        mPresenter.getLastest(0)
    }

    override fun onLatestSucess(latestList: List<LatestEntity>) {

        val temp = mutableListOf<Item>()
        if (mPage == 0) {
            temp.add(MainV3Threadheader(mActivity as BaseActivity))
        }
        if (PrefUtil.isFilterAdvertisement()) {
            temp.addAll(latestList.filter { it.board_name != "招聘信息" && it.board_name != "找工作" }.map { t -> MainV3ThreadItem(t, mContext, t.author_id) })
        } else {
            temp.addAll(latestList.map { t -> MainV3ThreadItem(t, mContext, t.author_id) })
        }
        if (isRefreshing) {
            itemManager.refreshAll(temp)
            isRefreshing = false
        } else {
            itemManager.addAll(temp)
        }
    }

    override fun onLatestFail(msg: String) {
        SnackBarUtil.error(mActivity, msg)
    }

    companion object {
        @JvmStatic
        fun newInstance(): MainFragmentV3 = MainFragmentV3()
    }

    private fun refresh() {
        mPage = 0
        isRefreshing = true
        mPresenter.getLastest(mPage)
        swipeRefreshLayout.isRefreshing = false
    }

    private fun loadMore() {
        mPresenter.getLastest(++mPage)
    }

}