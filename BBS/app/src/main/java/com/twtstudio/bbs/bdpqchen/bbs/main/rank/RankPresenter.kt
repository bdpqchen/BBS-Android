package com.twtstudio.bbs.bdpqchen.bbs.main.rank

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver
import com.twtstudio.bbs.bdpqchen.bbs.main.Rank
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RankPresenter(val mView: RankContract.View) : RxPresenter(), RankContract.Presenter {

    val observer = object : SimpleObserver<List<Rank>>() {
        override fun _onError(msg: String) {
            mView.onGetRankFail(msg)
        }

        override fun _onNext(t: List<Rank>) {
            mView.onGetRankSucces(t)
        }

    }

    override fun getWeekRank() {
        addSubscribe(sHttpClient.weekRank
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.data }
                .map { it.rank }
                .subscribeWith(observer))
    }

    override fun getMonthRank() {
        addSubscribe(sHttpClient.monthRank
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.data }
                .map { it.rank }
                .subscribeWith(observer))
    }

}