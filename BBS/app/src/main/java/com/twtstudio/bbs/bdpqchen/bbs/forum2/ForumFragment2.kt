package com.twtstudio.bbs.bdpqchen.bbs.forum2

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.WindowManager
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumBoardModel
import kotterknife.bindView

class ForumFragment2 : SimpleFragment(), ForumContract2.View {

    private lateinit var mPresenter: ForumPresenter2
    private val recyclerView by bindView<RecyclerView>(R.id.forum_rv)
    private val linearLayoutManager = LinearLayoutManager(mContext)
    override fun getPerMainFragmentLayoutId(): Int = R.layout.fragment_forum_2

    override fun initFragments() {
//        mActivity.enableLightStatusBarMode(true)
        mPresenter = ForumPresenter2(this)
        mPresenter.getBoardList()
        recyclerView.layoutManager = linearLayoutManager
    }

    private fun getWidthInDp(): Int {
        val wm = mActivity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val density: Float = dm.density
        return (width / density).toInt()
    }

    override fun onGetListSuccess(forum: List<ForumBoardModel>) {
        val temp = forum.map { t -> ForumItem(t, getWidthInDp(), mContext) }
        recyclerView.withItems(temp)
    }

    override fun onGetListFail(msg: String) {
        SnackBarUtil.error(mActivity, msg)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ForumFragment2()
    }

}