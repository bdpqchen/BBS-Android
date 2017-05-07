package com.twtstudio.bbs.bdpqchen.bbs.auth.login;

import android.content.Context;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxBaseSubscriber;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;


/**
 * Created by bdpqchen on 17-5-2.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> {

    private RxDoHttpClient mHttpClient;
    private Context mContext;

    @Inject
    public LoginPresenter(RxDoHttpClient client){
        mHttpClient = client;

    }

    public void doLogin(String username, String password){

        Disposable disposable = mHttpClient.doLogin(username, password)
                .map(mHttpClient.mTransformer)
//                .compose(mHttpClient.mTransformer)
                .compose(mHttpClient.mSchedulerHelper.io_main())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<LoginModel>() {
                    @Override
                    public void accept(@NonNull LoginModel loginModel) throws Exception {
                        LogUtil.d("accept");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });

        addSubscribe(disposable);
//        if (username.length() == 0 || password.length())
    }

}
