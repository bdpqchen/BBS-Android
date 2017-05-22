package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.retrieve;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-21.
 */

class RetrievePresenter extends RxPresenter<RetrieveContract.View> implements RetrieveContract.Presenter {

    RxDoHttpClient<RetrieveModel> mHttpClient;

    @Inject
    RetrievePresenter(RxDoHttpClient client) {
        mHttpClient = client;
    }


    @Override
    public void doRetrieveUsername(Bundle bundle) {
        SimpleObserver<RetrieveModel> observer = new SimpleObserver<RetrieveModel>() {
            @Override
            public void _onError(String msg) {
                mView.retrieveFailed(msg);
            }

            @Override
            public void _onNext(RetrieveModel model) {
                mView.retrieveSuccess(model);
            }
        };
        addSubscribe(mHttpClient.doRetrieveUsername(bundle)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
