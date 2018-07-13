package com.twtstudio.bbs.bdpqchen.bbs.main.announce

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AnnouncePresenter(val mView: AnnounceContract.View) : AnnounceContract.Presenter, RxPresenter() {

    override fun getAnnounce() {

        val connectObservable = sHttpClient.announce.map { t -> t.data }.publish()

        val titleObserver = object : SimpleObserver<List<AnnounceEntity>>() {

            override fun _onError(msg: String) {
                mView.onGetAnnounceFail(msg)
            }

            override fun _onNext(t: List<AnnounceEntity>) {
                mView.onGetAnnounceSuccess(t)
            }
        }

        addSubscribe(connectObservable
                .refCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(titleObserver))

        val contentObserver = object : SimpleObserver<ThreadModel.ThreadBean>() {

            val threadList: MutableList<ThreadModel.ThreadBean> = mutableListOf()

            override fun onComplete() {
                super.onComplete()
                mView.onGetAnnounceDetailSucceess(threadList)
            }

            override fun _onError(msg: String) {
                mView.onGetAnnounceFail(msg)
            }

            override fun _onNext(t: ThreadModel.ThreadBean) {
                threadList.add(t)
            }
        }

        addSubscribe(connectObservable
                .refCount()
                .concatMap { Observable.fromIterable(it) }
                .map { it.id }
                .concatMap { sHttpClient.getThread(it, 0) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseTransformer())
                .map { it.thread }
                .subscribeWith(contentObserver))

    }
}