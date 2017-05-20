package com.twtstudio.bbs.bdpqchen.bbs.auth.register;

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

public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private RxDoHttpClient<RegisterModel> mHttpClient;

    @Inject
    public RegisterPresenter(RxDoHttpClient httpClient) {
        this.mHttpClient = httpClient;

    }


    @Override
    public void doRegister(Bundle bundle) {

        SimpleObserver<RegisterModel> observer = new SimpleObserver<RegisterModel>() {
            @Override
            public void _onError(String msg) {
                mView.registerFailed(msg);
            }

            @Override
            public void _onNext(RegisterModel registerModel) {
                mView.registerSuccess();
            }
        };
        addSubscribe(mHttpClient.doRegister(bundle)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));

    }
}
