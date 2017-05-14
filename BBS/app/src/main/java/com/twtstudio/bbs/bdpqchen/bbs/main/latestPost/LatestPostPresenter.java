package com.twtstudio.bbs.bdpqchen.bbs.main.latestPost;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-11.
 */

class LatestPostPresenter extends RxPresenter<LatestPostContract.View> implements LatestPostContract.Presenter {

    public RxDoHttpClient<LatestPostModel> mHttpClient;

    @Inject
    LatestPostPresenter(RxDoHttpClient client){
        mHttpClient = client;
    }

    @Override
    public void refreshAnnounce() {
        SimpleObserver<LatestPostModel> observer = new SimpleObserver<LatestPostModel>() {
            @Override
            public void _onError(String msg) {
                mView.failedToGetLatestPost(msg);
            }

            @Override
            public void _onNext(LatestPostModel latestPostModel) {
                mView.refreshAnnounce(latestPostModel.getAnnounce());
            }

        };
        addSubscribe(mHttpClient.getLatestPost()
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    public void addAnnounce(){

        SimpleObserver<LatestPostModel> observer = new SimpleObserver<LatestPostModel>() {
            @Override
            public void _onError(String msg) {
                mView.failedToGetLatestPost(msg);
            }

            @Override
<<<<<<< HEAD
            public void _onNext(LatestPostModel latestPostModel) {
                mView.refreshAnnounce(latestPostModel.getAnnounce());
=======
            public void _onNext(List<LatestPostModel> LatestPostModels) {
              mView.showLatestPostList(LatestPostModels);
>>>>>>> e3882c000025967bd9b94f720b4bd3fc38106ef6
            }

        };
        addSubscribe(mHttpClient.getLatestPost()
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }


}
