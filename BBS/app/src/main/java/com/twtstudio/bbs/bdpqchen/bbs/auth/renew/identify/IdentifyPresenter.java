package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-21.
 */

class IdentifyPresenter extends RxPresenter<IdentifyContract.View> implements IdentifyContract.Presenter{
    RxDoHttpClient<IdentifyModel> mHttpClient;

    @Inject
    IdentifyPresenter(RxDoHttpClient client){
        mHttpClient = client;
    }

    @Override
    public void doIdentify(String username, String password) {
        SimpleObserver<IdentifyModel> observer = new SimpleObserver<IdentifyModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.identifyFailed(msg);
            }

            @Override
            public void _onNext(IdentifyModel identifyModel) {
                if (mView != null)
                    mView.identifySuccess(identifyModel);
            }
        };
        addSubscribe(mHttpClient.doIdentifyOldUser(username, password)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
