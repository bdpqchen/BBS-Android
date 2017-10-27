package com.twtstudio.bbs.bdpqchen.bbs.auth.register.old;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-2.
 */

public class RegisterOldPresenter extends RxPresenter implements RegisterOldContract.Presenter {

    private RegisterOldContract.View mView;

    RegisterOldPresenter(RegisterOldContract.View view) {
        mView = view;
    }

    @Override
    public void doRegisterOld(Bundle bundle) {
        SimpleObserver<RegisterOldModel> observer = new SimpleObserver<RegisterOldModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.registerFailed(msg);
            }

            @Override
            public void _onNext(RegisterOldModel RegisterOldModel) {
                if (mView != null)
                    mView.registerSuccess();
            }
        };
        addSubscribe(sHttpClient.doRegisterOld(bundle)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }
}
