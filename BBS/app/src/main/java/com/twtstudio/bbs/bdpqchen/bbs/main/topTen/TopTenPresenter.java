package com.twtstudio.bbs.bdpqchen.bbs.main.topTen;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;


import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangyulong on 5/13/17.
 */

public class TopTenPresenter extends RxPresenter<TopTenContract.View> implements TopTenContract.Presenter{
    public RxDoHttpClient<TopTenModel.DataBean> mHttpClient;

    @Inject
    TopTenPresenter(RxDoHttpClient client){
        mHttpClient = client;
    }

    @Override
    public void refreshAnnounce() {

        SimpleObserver<TopTenModel.DataBean> observer = new SimpleObserver<TopTenModel.DataBean>() {
            @Override
            public void _onError(String msg) {
                mView.failedToGetTopTen(msg);
            }

            @Override
            public void _onNext(TopTenModel.DataBean TopTenModel) {
                mView.refreshAnnounce(TopTenModel.hot);
            }

        };
        addSubscribe(mHttpClient.getTopTen()
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    public void addAnnounce(){

        SimpleObserver<TopTenModel.DataBean> observer = new SimpleObserver<TopTenModel.DataBean>() {
            @Override
            public void _onError(String msg) {
                mView.failedToGetTopTen(msg);
            }

            @Override

            public void _onNext(TopTenModel.DataBean TopTenModel) {
                mView.refreshAnnounce(TopTenModel.hot);

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
