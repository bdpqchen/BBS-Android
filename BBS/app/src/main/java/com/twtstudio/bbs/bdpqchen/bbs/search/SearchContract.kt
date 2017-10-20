package com.twtstudio.bbs.bdpqchen.bbs.search

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchUserModel

/**
 * Created by bdpqchen on 17-10-18.
 */
interface SearchContract {
    interface Presenter : BasePresenter {
        fun searchUser(username: String)
        fun searchThread(keyword: String, page: Int)
    }

    interface View : BaseView {
        fun onGotUserList(userList: List<SearchUserModel>?)
        fun onGotUserFailed(msg: String)
        fun onGotThreadList()
        fun onGotThreadFailed(msg: String)
    }
}