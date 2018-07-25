package com.twtstudio.bbs.bdpqchen.bbs.main.announce

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.twt.wepeiyang.commons.experimental.extensions.enableLightStatusBarMode
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel
import com.twtstudio.bbs.bdpqchen.bbs.main.AnnounceEntity
import kotterknife.bindView

class AnnounceActivity : BaseActivity(), AnnounceContract.View {

    private val announceList: MutableList<AnnounceEntity> = mutableListOf()
    private val backIv: ImageView by bindView(R.id.announce_ic_back)
    private lateinit var recyclerView: RecyclerView
    private val mPresenter = AnnouncePresenter(this)

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
        recyclerView = findViewById(R.id.announce_rv) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        mPresenter.getAnnounce()
        backIv.setOnClickListener {
            onBackPressed()
        }
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_announce

    override fun getToolbarView(): Toolbar? = null

    override fun getPresenter(): BasePresenter = mPresenter

    override fun onGetAnnounceSuccess(announceList: List<AnnounceEntity>) {
        this.announceList.addAll(announceList)
    }

    override fun onGetAnnounceFail(msg: String) {
        SnackBarUtil.error(this, "加载失败，请检查网络设置")
    }

    override fun onGetAnnounceDetailSucceess(thread: List<ThreadModel.ThreadBean>) {
        val itemList: MutableList<AnnounceItem> = mutableListOf()
        for (i in 0 until thread.size) {
            itemList.add(AnnounceItem(announceList[i], thread[i], mContext))
        }
        recyclerView.withItems(itemList)
    }

}
