package com.twtstudio.bbs.bdpqchen.bbs.main.topTen;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.main.model.MainModel;


import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangyulong on 5/13/17.
 */

public class TopTenPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    public RxDoHttpClient<MainModel> mHttpClient;

    @Inject
    TopTenPresenter(RxDoHttpClient client) {
        mHttpClient = client;
    }

    @Override
    public void getHomeDataList() {

        SimpleObserver<MainModel> observer = new SimpleObserver<MainModel>() {
            @Override
            public void _onError(String msg) {
                mView.onFailedGetHomeData(msg);
            }

            @Override
            public void _onNext(MainModel homeModel) {
                mView.onGotHomeData(homeModel);
            }

        };
        addSubscribe(mHttpClient.getTopTen()
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

}
