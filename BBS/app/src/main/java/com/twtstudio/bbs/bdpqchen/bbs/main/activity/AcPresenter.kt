package com.twtstudio.bbs.bdpqchen.bbs.main.activity

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver
import com.twtstudio.bbs.bdpqchen.bbs.main.AcEntity

class AcPresenter(val view:AcContract.View) : RxPresenter(),AcContract.Presenter{


    override fun getActivityList() {
        val observer = object : SimpleObserver<AcEntity>() {
            override fun _onError(msg: String) {
                view.onGetActivityListFailed(msg)
            }

            override fun _onNext(t: AcEntity) {
                view.onGetActivityListSucess(t)
            }
        }

//        addSubscribe()

    }
}