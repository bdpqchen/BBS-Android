package com.twtstudio.bbs.bdpqchen.bbs.main.latestPost;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

import javax.inject.Inject;

/**
 * Created by bdpqchen on 17-5-11.
 */

class LatestPostPresenter extends RxPresenter<LatestPostContract.View> implements LatestPostContract.Presenter {

    public RxDoHttpClient<LatestPostModel> mHttpClient;

    @Inject
    LatestPostPresenter(RxDoHttpClient client){
        mHttpClient = client;
    }




}
