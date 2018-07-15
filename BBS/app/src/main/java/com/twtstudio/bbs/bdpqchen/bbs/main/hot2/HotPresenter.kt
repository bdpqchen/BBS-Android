package com.twtstudio.bbs.bdpqchen.bbs.main.hot2

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver
import com.twtstudio.bbs.bdpqchen.bbs.main.Hot
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HotPresenter(val mView: HotContract.View) : RxPresenter(), HotContract.Presenter {

    override fun getHotList() {
        val observer = object : SimpleObserver<List<Hot>>() {

            override fun _onError(msg: String) {
                mView.onGetHotListFailed(msg)
            }

            override fun _onNext(t: List<Hot>) {
                mView.onGetHotListSuccess(t)
            }
        }

        addSubscribe(sHttpClient.hot
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.data }
                .map { it.hot }
                .subscribeWith(observer))
    }


}