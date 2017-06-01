package com.twtstudio.bbs.bdpqchen.bbs.individual.updatePassword;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-6.
 */

class UpdatePasswordPresenter extends RxPresenter<UpdatePasswordContract.View> implements UpdatePasswordContract.Presenter {

    RxDoHttpClient<BaseModel> mHttpClient;

    @Inject
    UpdatePasswordPresenter(RxDoHttpClient client) {
        mHttpClient = client;
    }

    @Override
    public void doUpdatePass(String newPass, String oldPass) {
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onUpdateFailed(msg);
            }

            @Override
            public void _onNext(BaseModel model) {
                if (mView != null)
                    mView.onUpdated(model);
            }
        };
        addSubscribe(mHttpClient.doUpdatePassword(newPass, oldPass)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }
}
