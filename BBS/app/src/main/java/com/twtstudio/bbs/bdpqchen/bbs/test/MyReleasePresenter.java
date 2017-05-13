package com.twtstudio.bbs.bdpqchen.bbs.test;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

import javax.inject.Inject;

/**
 * Created by Arsener on 2017/5/13.
 */

class MyReleasePresenter extends RxPresenter<MyReleaseContract.View> implements  MyReleaseContract.Presenter {

    private RxDoHttpClient<ReleaseBean> mHttpClient;

    @Inject
    MyReleasePresenter(RxDoHttpClient client){
        mHttpClient = client;
    }

}
