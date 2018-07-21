package com.twtstudio.bbs.bdpqchen.bbs.main.rank

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import com.twt.wepeiyang.commons.experimental.extensions.enableLightStatusBarMode
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter
import kotterknife.bindView

class RankActivity : BaseActivity() {

    private val mArrowBack: ImageView by bindView(R.id.rank_ic_back)
    private val mTabLayout: TabLayout by bindView(R.id.rank_tab_layout)
    private val mViewPager: ViewPager by bindView(R.id.rank_view_pager)
    private lateinit var mTabAdaper: RankTabAdapter

    override fun getLayoutResourceId() = R.layout.activity_rank

    override fun getToolbarView(): Toolbar? = null

    override fun getPresenter(): BasePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            decorView.systemUiVisibility = option
            window.navigationBarColor = Color.BLACK
            window.statusBarColor = Color.WHITE
        }
        enableLightStatusBarMode(true)
        mTabAdaper = RankTabAdapter(supportFragmentManager)
        mViewPager.adapter = mTabAdaper
        mTabLayout.setupWithViewPager(mViewPager)
        mArrowBack.setOnClickListener {
            onBackPressed()
        }
    }
}
