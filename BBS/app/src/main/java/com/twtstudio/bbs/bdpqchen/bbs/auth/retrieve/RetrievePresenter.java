package com.twtstudio.bbs.bdpqchen.bbs.auth.retrieve;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-21.
 */

class RetrievePresenter extends RxPresenter implements RetrieveContract.Presenter {

    private RetrieveContract.View mView;
    RetrievePresenter(RetrieveContract.View view) {
        mView = view;
    }

    @Override
    public void doRetrievePassword(Bundle bundle) {
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
        addSubscribe(sHttpClient.doRetrievePassword(bundle)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }


    @Override
    public void resetPassword(Bundle bundle) {
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                mView.resetFailed(msg);
            }

            @Override
            public void _onNext(BaseModel model) {
                mView.resetSuccess(model);
            }
        };
        addSubscribe(sHttpClient.resetPassword(bundle)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );

    }



}
