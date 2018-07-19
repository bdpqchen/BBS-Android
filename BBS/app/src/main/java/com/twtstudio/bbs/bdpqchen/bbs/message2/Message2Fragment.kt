package com.twtstudio.bbs.bdpqchen.bbs.message2

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemAdapter
import cn.edu.twt.retrox.recyclerviewdsl.ItemManager
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel
import kotterknife.bindView

/**
 * Created by linjiaxin on 2018/7/18.
 */
class Message2Fragment : SimpleFragment(), Message2Contract.View {

    override fun getPerMainFragmentLayoutId() = R.layout.fragment_message2

    private lateinit var mPresenter: Message2Presenter
    private lateinit var itemManager: ItemManager
    private val mRecyclerView: RecyclerView by bindView(R.id.rv_message_list)
    private val mTvNoMessage: TextView by bindView(R.id.tv_no_message)
    private val mSrlMessage: SwipeRefreshLayout by bindView(R.id.srl_message)
    private var mRefreshing = false
    private var lastVisibleItemPosition = 0
    private var mPage = 0
    private var mLayoutManager: LinearLayoutManager = LinearLayoutManager(this.mContext, LinearLayoutManager.VERTICAL, false)
    private var autoClear = true

    override fun initFragments() {
        mPresenter = Message2Presenter(this)
        itemManager = ItemManager()
        mRecyclerView.adapter = ItemAdapter(itemManager)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.addItemDecoration(RecyclerViewItemDecoration(2))
        mPresenter.getMessageList(0)
        setRefreshing(true)
        mSrlMessage.setOnRefreshListener {
            mPresenter.getMessageList(0)
            mRefreshing = true
            mSrlMessage.isRefreshing = false
        }
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 2 >= mRecyclerView.adapter.itemCount) {
                    mPage++
                    mPresenter.getMessageList(mPage)
                }
            }
        })
    }

    override fun onGetMessageFailed(m: String) {
        SnackBarUtil.error(this.mActivity, m)
        setRefreshing(false)
        pageDecrease()
    }

    override fun showMessageList(messageList: List<MessageModel>) {
        val temp = mutableListOf<Item>()
        if (mRefreshing) {
            itemManager.refreshAll(temp)
            mRefreshing = false
        }
        if (messageList.size > 0) {
            temp.addAll(messageList.map { t -> MessageItems(this.mContext, t) })
            itemManager.addAll(temp)
        } else {
            pageDecrease()
            showNoMessage()
        }
        stopRefresh()
    }

    override fun onCleared() {
        mRefreshing = false
        if (!autoClear) {
            autoClear = false
            SnackBarUtil.normal(this.mActivity, "已清空未读消息")
            mPresenter.getMessageList(0)

        }
    }

    override fun onClearFailed(msg: String) {
        if (!autoClear) {
            autoClear = false
            SnackBarUtil.error(this.mActivity, "失败" + msg)
        }
        stopRefresh()
    }

    fun showNoMessage() {
        if (mTvNoMessage != null && mPage == 0) {
            mTvNoMessage.visibility = View.VISIBLE
        }
    }

    private fun stopRefresh() {
        mRefreshing = false
        setRefreshing(false)
    }

    fun setRefreshing(b: Boolean) {
        mSrlMessage.isRefreshing = b
        mRefreshing = b
    }

    fun pageDecrease() {
        mPage = if (mPage > 0) mPage-- else mPage

    }

    companion object {
        fun newInstance(): Message2Fragment = Message2Fragment()
    }

}