package com.twtstudio.bbs.bdpqchen.bbs.main;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.main.hot.HotEntity;
import com.twtstudio.bbs.bdpqchen.bbs.main.latest.LatestEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-6-5.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    RxDoHttpClient<List<LatestEntity>> mHttpClient;

    @Inject
    MainPresenter(RxDoHttpClient client) {
        mHttpClient = client;
    }

    @Override
    public void getLatestList() {
        SimpleObserver<List<LatestEntity>> observer = new SimpleObserver<List<LatestEntity>>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onGotDataFailed(msg);
            }

            @Override
            public void _onNext(List<LatestEntity> latestEntity) {
                if (mView != null)
                    mView.onGetLatestList(latestEntity);
            }
        };
        addSubscribe(mHttpClient.getLatestList()
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    @Override
    public void getHotList() {
        SimpleObserver<List<HotEntity>> observer = new SimpleObserver<List<HotEntity>>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onGotDataFailed(msg);
            }

            @Override
            public void _onNext(List<HotEntity> hotEntity) {
                if (mView != null)
                    mView.onGetHotList(hotEntity);
            }
        };
        addSubscribe(mHttpClient.getHotList()
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
