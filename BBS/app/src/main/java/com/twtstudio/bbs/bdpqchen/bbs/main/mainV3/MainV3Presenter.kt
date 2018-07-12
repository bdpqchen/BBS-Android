package com.twtstudio.bbs.bdpqchen.bbs.main.mainV3

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver
import com.twtstudio.bbs.bdpqchen.bbs.main.latest.LatestEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainV3Presenter(val view: MainV3Contract.View) : RxPresenter(), MainV3Contract.Presenter {

    val latestList = mutableListOf<LatestEntity>()

    override fun getLastest(page: Int) {

        val observer = object : SimpleObserver<List<LatestEntity>>() {
            override fun _onError(msg: String) {
                view.onLatestFail(msg)
            }

            override fun _onNext(t: List<LatestEntity>) {
                latestList.clear()
                latestList.addAll(t)
                view.onLatestSucess(latestList)
            }
        }

        addSubscribe(sHttpClient.getLatestList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseTransformer<List<LatestEntity>>())
                .subscribeWith(observer))
    }
}