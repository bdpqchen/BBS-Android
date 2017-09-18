package com.twtstudio.bbs.bdpqchen.bbs.home;


import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by bdpqchen on 17-4-21.
 */

public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {

    private RxDoHttpClient<Integer> mUnreadClient;
    private ResponseTransformer<BaseModel> mTransformer = new ResponseTransformer<>();
    private ResponseTransformer<Integer> mUnreadTrans = new ResponseTransformer<>();


    @Inject
    HomePresenter(RxDoHttpClient httpClient) {
        this.mUnreadClient = httpClient;
    }

    @Override
    public void getUnreadMessageCount() {
        SimpleObserver<Integer> observer = new SimpleObserver<Integer>() {
            @Override
            public void _onError(String msg) {
                if (mView != null) {
                    mView.onGetMessageFailed(msg);
                }
            }

            @Override
            public void _onNext(Integer integer) {
                if (mView != null) {
                    mView.onGotMessageCount(integer);
                }
            }
        };
        addSubscribe(mUnreadClient.getUnreadCount()
                .map(mUnreadTrans)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

}
