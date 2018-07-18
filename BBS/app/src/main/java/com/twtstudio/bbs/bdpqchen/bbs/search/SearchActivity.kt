package com.twtstudio.bbs.bdpqchen.bbs.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.*
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.EndlessRecyclerOnScrollListener
import com.twtstudio.bbs.bdpqchen.bbs.search.model.CommonModel
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchThreadModel
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchUserModel

/**
 * Created by bdpqchen on 17-10-18.
 */

class SearchActivity : BaseActivity(), SearchContract.View, View.OnTouchListener, OnUserItemClick {

    private val mPresenter: SearchPresenter = SearchPresenter(this)
    override fun getPresenter(): SearchPresenter {
        return mPresenter
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_search
    }

    override fun getToolbarView(): Toolbar {
        return findViewById(R.id.toolbar) as Toolbar
    }

    private lateinit var mTvEmptyResult: TextView
    private lateinit var mPbLoading: ProgressBar
    private lateinit var rvSearchResult: RecyclerView
    private lateinit var mAdapter: SearchAdapter
    private lateinit var mTvSearch: TextView
    private lateinit var mEtSearch: AppCompatEditText
    private var mKeyword = ""
    private var mReSearch = false
    private var mEnding = false
    private var mPage = 0
    private var mIsLoadingMore = false
    private var mMode = MODE_SEARCH_BOTH
    private var mAutoSearchInterval = 300
    private var mLastTimeInput : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTvEmptyResult = findViewById(R.id.tv_search_result_empty) as TextView
        mPbLoading = findViewById(R.id.pb_search_result) as ProgressBar
        rvSearchResult = findViewById(R.id.rv_search_result) as RecyclerView
        mEtSearch = findViewById(R.id.et_search) as AppCompatEditText
        mTvSearch = findViewById(R.id.tv_search) as TextView
        mEtSearch.setOnTouchListener(this)
        mMode = intent.getIntExtra(INTENT_SEARCH_MODE, MODE_SEARCH_BOTH)
        if (mMode == MODE_SEARCH_USER){
            mTvSearch.visibility = GONE
            mEtSearch.hint = "Search user"
        }

        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        rvSearchResult.layoutManager = layoutManager
        mAdapter = SearchAdapter(mContext, this)
        rvSearchResult.adapter = mAdapter

        rvSearchResult.addOnScrollListener(object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore() {
                if (!mEnding && !mIsLoadingMore){
                    ++mPage
                    searchThread()
                    LogUtil.dd("Loading more , in endless recycler scroll listener")
                    mIsLoadingMore = true
                }
            }
        })
        mTvSearch.setOnClickListener({
            val inputted = mEtSearch.text.toString()
            if (inputted.isNotEmpty()) {
                mKeyword = inputted
                mReSearch = true
                mPage = 0
                doSearch()
            }
        })
        if (mMode == MODE_SEARCH_USER) {
            mEtSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (System.currentTimeMillis() - mLastTimeInput > mAutoSearchInterval) {
                        mKeyword = s.toString()
                        doSearch()
                        mReSearch = true
                    }
                    mLastTimeInput = System.currentTimeMillis()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        }
    }

    override fun onClick(position: Int) {
        LogUtil.dd("====getuid", mAdapter.getUserUid(position-1).toString())
        val entity : SearchUserModel = mAdapter.getUser(position-1)
        if (mMode == MODE_SEARCH_USER){
            val intentResult = Intent()
            intentResult.putExtra(INTENT_RESULT_AT_USER_UID, entity.id)
            intentResult.putExtra(INTENT_RESULT_AT_USER_NAME, entity.name)
            setResult(Activity.RESULT_OK, intentResult)
            finishMe()
        }else{
            mContext.startActivity(IntentUtil.toPeople(mContext, mAdapter.getUserUid(position-1)))
        }
    }

    private fun doSearch() {
        if (mKeyword.isNotEmpty()) {
            showLoading()
            searchUser()
            searchThread()
        }
    }

    private fun searchUser(){
        if (mMode == MODE_SEARCH_USER || mMode == MODE_SEARCH_BOTH){
            mPresenter.searchUser(mKeyword)
        }
    }

    private fun searchThread() {
        if (mMode == MODE_SEARCH_THREAD || mMode == MODE_SEARCH_BOTH) {
            mPresenter.searchThread(mKeyword, mPage)
        }
    }

    override fun onGotUserList(userList: List<SearchUserModel>) {
        clearDataSet()
        LogUtil.dd("onGotUserList")
        if (userList.isEmpty()) {
            emptyResult()
        } else {
            hideLoading()
            val commonList = ArrayList<CommonModel>()
            var i = 0
            if (mMode == MODE_SEARCH_USER){
                userList.mapTo(commonList, { CommonModel(ITEM_SEARCH_USER, i++) })
                mAdapter.addUserList(userList)
                mAdapter.addCommonList(commonList)
                return
            }
            var showingList = userList
            val subAt = MAX_SEARCH_RESULT_USER
            if (userList.size > subAt) {
                showingList = userList.subList(0, subAt)
                val hidingList = userList.subList(subAt, userList.size)
                mAdapter.addHidingList(hidingList)
            }
            commonList.add(CommonModel(ITEM_SEARCH_USER_HEADER, -1))
            showingList.mapTo(commonList, { CommonModel(ITEM_SEARCH_USER, i++) })
            if (userList.size > subAt) {
                commonList.add(CommonModel(ITEM_SEARCH_USER_HIDING, i++))
            }
            commonList.add(CommonModel(ITEM_SEARCH_DIVIDER, i++))
            mAdapter.addUserList(showingList)
            mAdapter.addCommonList(commonList, true)
        }
    }

    override fun onGotUserFailed(msg: String) {
        mReSearch = false
        LogUtil.dd("onGotUserListFailed()", msg)
        SnackBarUtil.notice(mActivity, msg)
        hideLoading()
    }

    override fun onGotThreadList(list: List<SearchThreadModel>) {
        clearDataSet()
        if (mIsLoadingMore) {
            addThreadCommonData(list)
            mAdapter.addThreadList(list)
            mIsLoadingMore = false
            return
        }
        if (list.isEmpty()) {
            emptyResult()
        } else {
            hideLoading()
            addThreadCommonData(list)
            mAdapter.addThreadList(list)
        }
    }

    private fun addThreadCommonData(list: List<SearchThreadModel>) {
        if (list.isEmpty()) return
        val commonList = ArrayList<CommonModel>()
        var i = mAdapter.getThreadNextPosition()
        if (mPage == 0){
            commonList.add(CommonModel(ITEM_SEARCH_THREAD_HEADER, -2))
        }
        list.mapTo(commonList, { CommonModel(ITEM_SEARCH_THREAD, i++) })
        mAdapter.addCommonList(commonList)
    }

    override fun onGotThreadFailed(msg: String) {
        mReSearch = false
        SnackBarUtil.notice(mActivity, msg)
        hideLoading()
        if (mIsLoadingMore) {
            mIsLoadingMore = false
            mPage--
        }
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (v.id == R.id.et_search && event.action == MotionEvent.ACTION_DOWN) {
            if (mEtSearch.compoundDrawables[2] != null) {
                if (event.x > mEtSearch.width - mEtSearch.compoundDrawables[2].bounds.width()) {
                mEtSearch.clearComposingText()
                    mEtSearch.setText("")
                }
            }
        }
        return false
    }

    private fun clearDataSet() {
        if (mReSearch) {
            mReSearch = false
            mAdapter.clearDataSet()
        }
    }

    private fun showLoading() {
        mPbLoading.visibility = VISIBLE
    }

    private fun emptyResult() {
        hideLoading()
//        mTvEmptyResult.visibility = VISIBLE
    }

    private fun hideLoading() {
        mPbLoading.visibility = GONE
    }


}
