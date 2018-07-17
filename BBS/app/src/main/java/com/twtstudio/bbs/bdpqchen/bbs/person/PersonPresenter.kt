package com.twtstudio.bbs.bdpqchen.bbs.person

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel
import com.twtstudio.bbs.bdpqchen.bbs.people.PeopleModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PersonPresenter(view: PersonContract.View?) : RxPresenter(), PersonContract.Presenter {

    private var mView : PersonContract.View? = view

    override fun getPersonInfo(uid: Int) {

        val connectObservable = sHttpClient.getUserInfo(uid)
                .map(ResponseTransformer())
                .publish()

        val peopleObserver: SimpleObserver<PeopleModel> = object : SimpleObserver<PeopleModel>() {
            override fun _onError(msg: String) {
                mView?.onLoadFailed(msg)
            }

            override fun _onNext(t: PeopleModel) {
                mView?.onPersonInfoSuccess(t)
            }

        }

        addSubscribe(connectObservable.refCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(peopleObserver))

        val threadObserver : SimpleObserver<ThreadModel.ThreadBean> = object : SimpleObserver<ThreadModel.ThreadBean>() {
            val list = mutableListOf<ThreadModel.ThreadBean>()
            override fun _onError(msg: String) {
                mView?.onLoadFailed(msg)
            }

            override fun _onNext(t: ThreadModel.ThreadBean) {
                list.add(t)
            }

            override fun onComplete() {
                super.onComplete()
                mView?.onThreadInfoSuccess(list)
            }
        }

        addSubscribe(connectObservable.refCount()
                .map { it.recent }
                .flatMap { Observable.fromIterable(it) }
                .map { it.id }
                .concatMap { sHttpClient.getThread(it,0) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map (ResponseTransformer())
                .map { it.thread }
                .subscribeWith(threadObserver))

    }

}