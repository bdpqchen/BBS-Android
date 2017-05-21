package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.retrieve.reset_password;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-21.
 */

class ResetPasswordPresenter extends RxPresenter<ResetPasswordContract.View> implements ResetPasswordContract.Presenter{

    private RxDoHttpClient<BaseModel> mHttpClient;

    ResetPasswordPresenter(RxDoHttpClient httpClient) {
        mHttpClient = httpClient;

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
        addSubscribe(mHttpClient.resetPassword(bundle)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );

    }
}
