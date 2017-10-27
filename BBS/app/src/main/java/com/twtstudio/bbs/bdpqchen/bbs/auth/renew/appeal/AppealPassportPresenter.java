package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.appeal;

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

class AppealPassportPresenter extends RxPresenter implements AppealPassportContract.Presenter{

    private AppealPassportContract.View mView;

    AppealPassportPresenter(AppealPassportContract.View view){
        mView = view;
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
        addSubscribe(sHttpClient.appealPassport(bundle)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
