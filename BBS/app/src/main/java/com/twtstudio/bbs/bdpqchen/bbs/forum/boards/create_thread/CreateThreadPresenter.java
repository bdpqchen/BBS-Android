package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.create_thread;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-27.
 */

class CreateThreadPresenter extends RxPresenter<CreateThreadContract.View> implements CreateThreadContract.Presenter {

    private RxDoHttpClient<CreateThreadModel> mHttpClient;

    @Inject
    CreateThreadPresenter(RxDoHttpClient client) {
        mHttpClient = client;
    }

    @Override
    public void doPublishThread(Bundle bundle) {
        SimpleObserver<CreateThreadModel> observer = new SimpleObserver<CreateThreadModel>() {
            @Override
            public void _onError(String msg) {
                mView.onPublishFailed(msg);
            }
            @Override
            public void _onNext(CreateThreadModel model) {
                mView.onPublished();
            }
        };
        addSubscribe(mHttpClient.doPublishThread(bundle)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
