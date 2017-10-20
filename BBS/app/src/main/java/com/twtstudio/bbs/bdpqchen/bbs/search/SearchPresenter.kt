package com.twtstudio.bbs.bdpqchen.bbs.search

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchUserModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by bdpqchen on 17-10-18.
 */
class  SearchPresenter @Inject constructor(client: RxDoHttpClient<SearchUserModel>) : RxPresenter<SearchContract.View>(), SearchContract.Presenter {
    val mHttpClient = client

    override fun searchUser(keyName: String) {
        val observer = object : SimpleObserver<List<SearchUserModel>>() {
            override fun _onError(msg: String) {
                if (mView != null) {
                    mView.onGotUserFailed(msg)
                }
            }

            override fun _onNext(t: List<SearchUserModel>?) {
                if (mView != null) {
                    mView.onGotUserList(t)
                }
            }
        }
        addSubscribe(mHttpClient.searchUser(keyName)
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