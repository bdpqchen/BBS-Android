package com.twtstudio.bbs.bdpqchen.bbs.main;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainContract;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-6-5.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    RxDoHttpClient<MainModel> mHttpClient;

    @Inject
    MainPresenter(RxDoHttpClient client) {
        mHttpClient = client;
    }


    @Override
    public void getDataList() {
        SimpleObserver<MainModel> observer = new SimpleObserver<MainModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onGotDataFailed(msg);
            }

            @Override
            public void _onNext(MainModel mainModel) {
                if (mView != null)
                    mView.onGotDataList(mainModel);
            }
        };
        addSubscribe(mHttpClient.getMainData()
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
