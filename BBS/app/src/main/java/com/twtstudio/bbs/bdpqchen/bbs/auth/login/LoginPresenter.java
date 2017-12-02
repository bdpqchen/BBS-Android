package com.twtstudio.bbs.bdpqchen.bbs.auth.login;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by bdpqchen on 17-5-2.
 */

class LoginPresenter extends RxPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;

    LoginPresenter(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void doLogin(String username, String password) {
        addSubscribe(sHttpClient.doLogin(username, password)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SimpleObserver<LoginModel>() {
                    @Override
                    public void _onError(String msg) {
                        if (mView != null)
                            mView.loginFailed(msg);
                    }

                    @Override
                    public void _onNext(LoginModel loginModel) {
                        if (mView != null)
                            mView.loginSuccess(loginModel);
                    }
                }));
    }

}
