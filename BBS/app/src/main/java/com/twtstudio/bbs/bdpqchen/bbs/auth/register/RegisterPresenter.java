package com.twtstudio.bbs.bdpqchen.bbs.auth.register;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;

import javax.inject.Inject;

/**
 * Created by bdpqchen on 17-5-2.
 */

public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter{

    @Inject
    public RegisterPresenter(){

    }


    @Override
    public void doRegister(Bundle bundle) {

    }
}
