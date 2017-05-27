package com.twtstudio.bbs.bdpqchen.bbs.main.historyHot;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangyulong on 5/13/17.
 */

public class HistoryHotPresenter extends RxPresenter<HistoryHotContract.View> implements HistoryHotContract.Presenter{
    public RxDoHttpClient<HistoryHotModel.DataBean> mHttpClient;

    @Inject
    HistoryHotPresenter(RxDoHttpClient client){
        mHttpClient = client;
    }

    @Override
    public void refreshAnnounce() {
        SimpleObserver<HistoryHotModel.DataBean> observer = new SimpleObserver<HistoryHotModel.DataBean>() {
            @Override
            public void _onError(String msg) {
                mView.failedToGetHistoryHot(msg);
            }

            @Override
            public void _onNext(HistoryHotModel.DataBean HistoryHotModel) {
                mView.refreshAnnounce(HistoryHotModel.historyhot);
            }

        };
        /*addSubscribe(mHttpClient.getHistoryHot()
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );*/
    }

    public void addAnnounce(){

        SimpleObserver<HistoryHotModel.DataBean> observer = new SimpleObserver<HistoryHotModel.DataBean>() {
            @Override
            public void _onError(String msg) {
                mView.failedToGetHistoryHot(msg);
            }

            @Override
            public void _onNext(HistoryHotModel.DataBean historyHotModel) {
                mView.refreshAnnounce(historyHotModel.historyhot);
            }

        };
     /*   addSubscribe(mHttpClient.getHistoryHot()
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );*/
    }
}
