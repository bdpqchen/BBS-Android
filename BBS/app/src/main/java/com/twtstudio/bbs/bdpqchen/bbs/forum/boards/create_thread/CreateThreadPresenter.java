package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.create_thread;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

import javax.inject.Inject;

/**
 * Created by bdpqchen on 17-5-27.
 */

class CreateThreadPresenter extends RxPresenter<CreateThreadContract.View> implements CreateThreadContract.Presenter{

    private RxDoHttpClient<CreateThreadModel> mHttpClient;

    @Inject
    CreateThreadPresenter(RxDoHttpClient client){
        mHttpClient = client;
    }






}
