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
    public RxDoHttpClient<HistoryHotModel> mHttpClient;

    @Inject
    HistoryHotPresenter(RxDoHttpClient client){
        mHttpClient = client;
    }

    @Override
    public void refreshAnnounce() {
        SimpleObserver<HistoryHotModel> observer = new SimpleObserver<HistoryHotModel>() {
            @Override
            public void _onError(String msg) {
                mView.failedToGetHistoryHot(msg);
            }

            @Override
            public void _onNext(HistoryHotModel HistoryHotModel) {
                mView.refreshAnnounce(HistoryHotModel.getAnnounce());
            }

        };
        addSubscribe(mHttpClient.getHistoryHot()
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    public void addAnnounce(){

        SimpleObserver<HistoryHotModel> observer = new SimpleObserver<HistoryHotModel>() {
            @Override
            public void _onError(String msg) {
                mView.failedToGetHistoryHot(msg);
            }

            @Override
            public void _onNext(HistoryHotModel HistoryHotModel) {
                mView.refreshAnnounce(HistoryHotModel.getAnnounce());
            }

        };
        addSubscribe(mHttpClient.getHistoryHot()
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
