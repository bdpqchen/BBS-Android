package com.twtstudio.bbs.bdpqchen.bbs.main.hot2

import com.twtstudio.bbs.bdpqchen.bbs.main.Hot

interface HotContract {

    interface Presenter {
        fun getHotList()
    }

    interface View {
        fun onGetHotListSuccess(hotList: List<Hot>)
        fun onGetHotListFailed(msg: String)
    }

}