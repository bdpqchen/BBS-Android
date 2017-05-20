package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.find_username;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

import javax.inject.Inject;

/**
 * Created by bdpqchen on 17-5-21.
 */

class FindUsernamePresenter extends RxPresenter<FindUsernameContract.View> implements FindUsernameContract.Presenter{

    RxDoHttpClient<FindUsernameModel> mHttpClient;

    @Inject
    FindUsernamePresenter(RxDoHttpClient<FindUsernameModel> client){
        mHttpClient = client;
    }




}
