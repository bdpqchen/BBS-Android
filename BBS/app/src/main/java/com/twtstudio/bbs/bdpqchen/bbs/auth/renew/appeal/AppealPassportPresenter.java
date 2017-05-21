package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.appeal;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

import javax.inject.Inject;

/**
 * Created by bdpqchen on 17-5-21.
 */

class AppealPassportPresenter extends RxPresenter<AppealPassportContract.View> implements AppealPassportContract.Presenter{

    RxDoHttpClient<AppealPassportModel> mHttpClient;

    @Inject
    AppealPassportPresenter(RxDoHttpClient client){
        this.mHttpClient = client;
    }



}
