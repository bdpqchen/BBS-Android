package com.twtstudio.bbs.bdpqchen.bbs.auth.register;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-2.
 */

class RegisterPresenter extends RxPresenter implements RegisterContract.Presenter {
    private RegisterContract.View mView;

    RegisterPresenter(RegisterContract.View view) {
        mView = view;
    }

    @Override
    public void doRegister(Bundle bundle) {

        SimpleObserver<RegisterModel> observer = new SimpleObserver<RegisterModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null) {
                    mView.registerFailed(msg);
                }
            }

            @Override
            public void _onNext(RegisterModel registerModel) {
                if (mView != null)
                    mView.registerSuccess();
            }
        };
        addSubscribe(sHttpClient.doRegister(bundle)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));

    }
}
