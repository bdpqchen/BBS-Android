package com.twtstudio.bbs.bdpqchen.bbs.search

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MODE_SEARCH_THREAD
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MODE_SEARCH_USER
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.EndlessRecyclerOnScrollListener
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchThreadModel
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchUserModel

/**
 * Created by bdpqchen on 17-10-18.
 */

class SearchActivity : BaseActivity(), SearchContract.View {

    private val mPresenter: SearchPresenter = SearchPresenter(this)
    override fun getPresenter(): SearchPresenter {
        return mPresenter
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_search
    }

    override fun getToolbarView(): Toolbar {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = "搜索结果"
        return toolbar
    }

    private lateinit var mTvEmptyResult: TextView
    private lateinit var mPbLoading: ProgressBar
    private lateinit var rvSearchResult: RecyclerView
    private lateinit var mAdapter: SearchAdapter
    private lateinit var mKeyThread: String
    private var mPage = 0
    private var mIsLoadingMore = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTvEmptyResult = findViewById(R.id.tv_search_result_empty) as TextView
        mPbLoading = findViewById(R.id.pb_search_result) as ProgressBar
        rvSearchResult = findViewById(R.id.rv_search_result) as RecyclerView
        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        rvSearchResult.layoutManager = layoutManager
        rvSearchResult.addItemDecoration(RecyclerViewItemDecoration(1))
        rvSearchResult.addOnScrollListener(object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore() {
                searchThread(++mPage)
                mIsLoadingMore = true
            }
        })
        doSearch()
    }

    private fun doSearch() {
        val keyUser = intent.getStringExtra(Constants.INTENT_SEARCH_USER)
        if (keyUser != null && !keyUser.isEmpty()) {
            mAdapter = SearchAdapter(mContext, MODE_SEARCH_USER)
            mPresenter.searchUser(keyUser)
        }
        val keyThread = intent.getStringExtra(Constants.INTENT_SEARCH_THREAD)
        if (keyThread != null && !keyThread.isEmpty()) {
            mAdapter= SearchAdapter(mContext, MODE_SEARCH_THREAD)
            mKeyThread = keyThread
            searchThread(0)
        }
        rvSearchResult.adapter = mAdapter

    }
    private fun searchThread(page: Int){
        if(mKeyThread.isNotEmpty())
        mPresenter.searchThread(mKeyThread, page)
    }

    override fun onGotUserList(userList: List<SearchUserModel>) {
        LogUtil.dd("onGotUserList")
        if (userList.isEmpty()) {
            emptyResult()
        } else {
            hideLoading()
            mAdapter.addUserList(userList)
        }
    }

    override fun onGotUserFailed(msg: String) {
        LogUtil.dd("onGotUserListFailed()", msg)
        SnackBarUtil.notice(mActivity, msg)
        hideLoading()
    }

    override fun onGotThreadList(list: List<SearchThreadModel>) {
        if (mIsLoadingMore){
            mAdapter.addThreadList(list)
            mIsLoadingMore = false
            return
        }
        if (list.isEmpty()) {
            emptyResult()
        }else{
            hideLoading()
            mAdapter.addThreadList(list)
        }
    }

    override fun onGotThreadFailed(msg: String) {
        SnackBarUtil.notice(mActivity, msg)
        hideLoading()
        if (mIsLoadingMore){
            mIsLoadingMore = false
            mPage--
        }
    }

    private fun emptyResult() {
        hideLoading()
        showEmptyText()
    }

    private fun showEmptyText() {
        mTvEmptyResult.visibility = VISIBLE
    }

    private fun hideLoading() {
        mPbLoading.visibility = GONE
    }



}
