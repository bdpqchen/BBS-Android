package com.twtstudio.bbs.bdpqchen.bbs.auth.login;

import android.content.Context;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by bdpqchen on 17-5-2.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> {

    private RxDoHttpClient<LoginModel> mHttpClient;
    private Context mContext;

    @Inject
    public LoginPresenter(RxDoHttpClient client) {
        mHttpClient = client;

    }

    public void doLogin(String username, String password) {

        addSubscribe(mHttpClient.doLogin(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mHttpClient.mTransformer)
                .subscribeWith(new SimpleObserver<LoginModel>() {
                    @Override
                    public void _onError(String msg) {
                        LogUtil.d("_onError", msg);
                        if (mView != null)
                            mView.loginFailed(msg);

                    }

                    @Override
                    public void _onNext(LoginModel loginModel) {
                        LogUtil.d("_onNext()", loginModel);
                        if (mView != null)
                            mView.loginSuccess(loginModel);
                    }

                }));


    }

}
