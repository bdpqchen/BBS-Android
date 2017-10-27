package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-21.
 */

class IdentifyPresenter extends RxPresenter implements IdentifyContract.Presenter {
    private IdentifyContract.View mView;

    IdentifyPresenter(IdentifyContract.View view) {
        mView = view;
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
        addSubscribe(sHttpClient.doIdentifyOldUser(username, password)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
