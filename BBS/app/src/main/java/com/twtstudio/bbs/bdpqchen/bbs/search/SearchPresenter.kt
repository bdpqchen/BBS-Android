package com.twtstudio.bbs.bdpqchen.bbs.search

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchUserModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by bdpqchen on 17-10-18.
 */
class SearchPresenter(view: SearchContract.View) : RxPresenter(), SearchContract.Presenter {
    private val mView: SearchContract.View = view

    override fun searchUser(keyName: String) {
        val observer = object : SimpleObserver<List<SearchUserModel>>() {
            override fun _onError(msg: String) {
                mView.onGotUserFailed(msg)
            }

            override fun _onNext(t: List<SearchUserModel>) {
                mView.onGotUserList(t)
            }
        }
        addSubscribe(sHttpClient.searchUser(keyName)
                .map(ResponseTransformer<List<SearchUserModel>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )

    }

    override fun searchThread(keyword: String, page: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}