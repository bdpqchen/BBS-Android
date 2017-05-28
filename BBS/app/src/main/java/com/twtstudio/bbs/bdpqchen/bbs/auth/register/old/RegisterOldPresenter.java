package com.twtstudio.bbs.bdpqchen.bbs.auth.register.old;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-2.
 */

public class RegisterOldPresenter extends RxPresenter<RegisterOldContract.View> implements RegisterOldContract.Presenter {

    private RxDoHttpClient<RegisterOldModel> mHttpClient;

    @Inject
    public RegisterOldPresenter(RxDoHttpClient httpClient) {
        this.mHttpClient = httpClient;

    }

    @Override
    public void doRegisterOld(Bundle bundle) {
        SimpleObserver<RegisterOldModel> observer = new SimpleObserver<RegisterOldModel>() {
            @Override
            public void _onError(String msg) {
                mView.registerFailed(msg);
            }

            @Override
            public void _onNext(RegisterOldModel RegisterOldModel) {
                mView.registerSuccess();
            }
        };
        addSubscribe(mHttpClient.doRegisterOld(bundle)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }
}
