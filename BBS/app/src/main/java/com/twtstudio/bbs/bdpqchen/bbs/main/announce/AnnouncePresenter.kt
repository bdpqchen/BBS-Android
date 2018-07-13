package com.twtstudio.bbs.bdpqchen.bbs.main.announce

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AnnouncePresenter(val mView: AnnounceContract.View) : AnnounceContract.Presenter, RxPresenter() {

    override fun getAnnounce() {
        val observer = object : SimpleObserver<List<AnnounceEntity>>() {
            override fun _onError(msg: String) {
                mView.onGetAnnounceFail(msg)
            }

            override fun _onNext(t: List<AnnounceEntity>) {
                mView.onGetAnnounceSuccess(t)
            }
        }

        addSubscribe(sHttpClient.announce
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { t -> t.data }
                .subscribeWith(observer))
    }
}