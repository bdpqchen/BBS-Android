package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.appeal;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-21.
 */

class AppealPassportPresenter extends RxPresenter<AppealPassportContract.View> implements AppealPassportContract.Presenter{

    RxDoHttpClient<BaseModel> mHttpClient;

    @Inject
    AppealPassportPresenter(RxDoHttpClient client){
        this.mHttpClient = client;
    }


    @Override
    public void appealPassport(Bundle bundle) {
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.sendFailed(msg);
            }

            @Override
            public void _onNext(BaseModel model) {
                if (mView != null)
                    mView.sendSuccess();
            }
        };
        addSubscribe(mHttpClient.appealPassport(bundle)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
