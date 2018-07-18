package com.twtstudio.bbs.bdpqchen.bbs.message2

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.TextView
import butterknife.BindView
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.MessagePresenter
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel
import kotterknife.bindView

/**
 * Created by linjiaxin on 2018/7/18.
 */
class Message2Fragment : BaseFragment(), Message2Contract.View {
    private lateinit var xPresenter: Message2Presenter

    private val mtoolbar: Toolbar by bindView(R.id.toolbar)
    private val mRecyclerView: RecyclerView by bindView(R.id.rv_message_list)
    private val mTvNoMessage: TextView by bindView(R.id.tv_no_message)
    private val mSrlMessage: SwipeRefreshLayout by bindView(R.id.srl_message)
    private var mRefreshing = false
    private var lastVisibleItemPosition = 0
    private var mPage = 0
    private var mLayoutManager: LinearLayoutManager? = null
    private var autoClear = true

    override fun initFragment() {
        xPresenter = Message2Presenter(this)
        mtoolbar.setTitle("我的消息")


    }

    override fun onGetMessageFailed(m: String) {

    }

    override fun showMessageList(messageList: List<MessageModel>) {
    }

    override fun onCleared() {
    }

    override fun onClearFailed(msg: String) {
    }

    override fun getFragmentLayoutId(): Int = R.layout.atcivity_message

    override fun getPresenter(): BasePresenter = xPresenter

}