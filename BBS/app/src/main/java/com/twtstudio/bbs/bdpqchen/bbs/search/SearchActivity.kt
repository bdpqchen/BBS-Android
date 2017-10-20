package com.twtstudio.bbs.bdpqchen.bbs.search

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil
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

    override fun getToolbarView(): Toolbar? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mPresenter.searchUser("bd")

    }

    override fun onGotUserList(userList: List<SearchUserModel>?) {
        LogUtil.dd("onGotUserList")

    }

    override fun onGotUserFailed(msg: String) {
        LogUtil.dd("onGotUserListFailed()", msg)
    }

    override fun onGotThreadList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGotThreadFailed(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}
