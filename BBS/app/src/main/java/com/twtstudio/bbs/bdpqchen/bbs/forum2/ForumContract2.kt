package com.twtstudio.bbs.bdpqchen.bbs.forum2

import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumBoardModel

interface ForumContract2 {

    interface Presenter {
        fun getBoardList()
    }

    interface View {
        fun onGetListSuccess(forum: List<ForumBoardModel>)
        fun onGetListFail(msg: String)
    }

}