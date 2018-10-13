package com.twtstudio.bbs.bdpqchen.bbs.main.activity

import com.twtstudio.bbs.bdpqchen.bbs.main.AcEntity

interface AcContract {

    interface Presenter{
        fun getActivityList()
    }

    interface View{
        fun onGetActivityListFailed(msg:String)

        fun onGetActivityListSucess(data: AcEntity)

    }
}