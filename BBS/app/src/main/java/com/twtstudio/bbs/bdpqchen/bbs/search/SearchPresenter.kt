package com.twtstudio.bbs.bdpqchen.bbs.search

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchThreadModel
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchUserModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by bdpqchen on 17-10-18.
 */
class SearchPresenter(view: SearchContract.View) : RxPresenter(), SearchContract.Presenter {
    private val mView: SearchContract.View = view

    override fun searchUser(username: String) {
        val observer = object : SimpleObserver<List<SearchUserModel>>() {
            override fun _onError(msg: String) {
                mView.onGotUserFailed(msg)
            }

            override fun _onNext(t: List<SearchUserModel>) {
                mView.onGotUserList(t)
            }
        }
        addSubscribe(sHttpClient.searchUser(username)
                .map(ResponseTransformer<List<SearchUserModel>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )

    }

    override fun searchThread(keyword: String, page: Int) {
        val observer = object : SimpleObserver<List<SearchThreadModel>>() {
            override fun _onError(msg: String) {
                mView.onGotThreadFailed(msg)
            }

            override fun _onNext(list: List<SearchThreadModel>) {
                mView.onGotThreadList(list)
            }

        }
        addSubscribe(sHttpClient.searchThread(keyword, page)
                .map(ResponseTransformer<List<SearchThreadModel>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }

}