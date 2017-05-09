package com.twtstudio.bbs.bdpqchen.bbs.auth.register;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

import javax.inject.Inject;

/**
 * Created by bdpqchen on 17-5-2.
 */

public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter{

    private RxDoHttpClient mHttpClient;

    @Inject
    public RegisterPresenter(RxDoHttpClient httpClient){
        this.mHttpClient = httpClient;

    }


    @Override
    public void doRegister(Bundle bundle) {

        addSubscribe(mHttpClient.doRegister(bundle)
        .map(mHttpClient.mTransformer)
        );

    }
}
