package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.retrieve;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

import javax.inject.Inject;

/**
 * Created by bdpqchen on 17-5-21.
 */

class RetrievePresenter extends RxPresenter<RetrieveContract.View> implements RetrieveContract.Presenter{

    RxDoHttpClient<RetrieveModel> mHttpClient;

    @Inject
    RetrievePresenter(RxDoHttpClient client){
        mHttpClient = client;
    }




}
