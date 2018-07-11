package com.twtstudio.bbs.bdpqchen.bbs.main.mainV3

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.main.latest.LatestEntity
import kotterknife.bindView

class MainFragmentV3 : SimpleFragment(), MainV3Contract.View {

    //    val banner : Banner by bindView(R.id.main_v3_banner)
//    val imgList = mutableListOf(R.drawable.bbs_banner0)
    private val recyclerView: RecyclerView by bindView(R.id.fragment_main_v3_rv)
    private val mPresenter = MainV3Presenter(this)
    private val searchIv: ImageView by bindView(R.id.main_thread_search)


    override fun getPerMainFragmentLayoutId(): Int = R.layout.fragment_main_v3

    override fun initFragments() {
//        banner.setImageLoader(GlideImageLoader())
//                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
//                .setImages(imgList)
//                .setIndicatorGravity(BannerConfig.CENTER)
//                .start()
        searchIv.setOnClickListener { startActivity(IntentUtil.toSearch(this@MainFragmentV3.mContext)) }
        recyclerView.setHasFixedSize(true)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = LinearLayoutManager(this.mContext)
        mPresenter.getLastest()
    }

    override fun onLatestSucess(latestList: List<LatestEntity>) {
        val a: List<MainV3ThreadItem> = latestList.map { t -> MainV3ThreadItem(t, this.mContext, t.author_id) }
        recyclerView.withItems(a)
    }

    override fun onLatestFail(msg: String) {
        SnackBarUtil.error(this@MainFragmentV3.mActivity, msg)
    }

    companion object {
        @JvmStatic
        fun newInstance(): MainFragmentV3 = MainFragmentV3()
    }
}