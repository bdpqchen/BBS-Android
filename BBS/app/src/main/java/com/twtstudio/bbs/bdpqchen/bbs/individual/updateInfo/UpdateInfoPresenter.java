package com.twtstudio.bbs.bdpqchen.bbs.individual.updateInfo;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-6.
 */

class UpdateInfoPresenter extends RxPresenter<UpdateInfoContract.View> implements UpdateInfoContract.Presenter {

    private RxDoHttpClient<BaseModel> mRxDoHttpClient;

    @Inject
    UpdateInfoPresenter(RxDoHttpClient client) {
        this.mRxDoHttpClient = client;
    }


    @Override
    public void doUpdateAvatar(String imagePath) {

        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                mView.updateAvatarFailed(msg);
            }

            @Override
            public void _onNext(BaseModel baseModel) {
                mView.updateAvatarSuccess(baseModel);
            }

        };
        addSubscribe(mRxDoHttpClient.doUpdateAvatar(imagePath)
                .map(mRxDoHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    public void doUpdateInfo(Bundle bundle, int type) {
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                mView.updateInfoFailed(msg);
            }

            @Override
            public void _onNext(BaseModel baseModel) {
                mView.updateInfoSuccess();
            }
        };
        addSubscribe(mRxDoHttpClient.doUpdateInfo(bundle, type)
                .map(mRxDoHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
