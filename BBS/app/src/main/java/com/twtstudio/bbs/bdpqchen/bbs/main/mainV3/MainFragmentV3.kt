package com.twtstudio.bbs.bdpqchen.bbs.main.mainV3

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemAdapter
import cn.edu.twt.retrox.recyclerviewdsl.ItemManager
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.EndlessRecyclerOnScrollListener
import com.twtstudio.bbs.bdpqchen.bbs.main.latest.LatestEntity
import kotterknife.bindView

class MainFragmentV3 : SimpleFragment(), MainV3Contract.View {

    private var latestList: MutableList<LatestEntity> = mutableListOf()
    private val swipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.main_srl)
    private val recyclerView: RecyclerView by bindView(R.id.fragment_main_v3_rv)
    private val searchIv: ImageView by bindView(R.id.main_thread_search)
    private val mPresenter = MainV3Presenter(this)
    private var mPage = 0
    private lateinit var itemManager: ItemManager
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun getPerMainFragmentLayoutId(): Int = R.layout.fragment_main_v3

    override fun initFragments() {
        searchIv.setOnClickListener { startActivity(IntentUtil.toSearch(mContext)) }

        swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }

        itemManager = ItemManager()
        linearLayoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = ItemAdapter(itemManager)
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener(linearLayoutManager) {
            override fun onLoadMore() {
                loadMore()
            }
        })
        mPresenter.getLastest(0)
    }

    override fun onLatestSucess(latestList: List<LatestEntity>) {
        val temp = mutableListOf<Item>()
        if (mPage == 0) {
            temp.add(MainV3Threadheader(mActivity))
        }
        temp.addAll(latestList.map { t -> MainV3ThreadItem(t, mContext, t.author_id) })
        recyclerView.withItems(temp)
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
        mPresenter.getLastest(mPage)
        swipeRefreshLayout.isRefreshing = false
        SnackBarUtil.notice(mActivity, "刷新成功！")
    }

    private fun loadMore() {
        //      mPresenter.getLastest(++mPage)
    }

}