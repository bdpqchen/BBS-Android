package com.twtstudio.bbs.bdpqchen.bbs.individual.updateInfo;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-6.
 */

class UpdateInfoPresenter extends RxPresenter implements UpdateInfoContract.Presenter {
    private UpdateInfoContract.View mView;

    UpdateInfoPresenter(UpdateInfoContract.View view) {
        mView = view;
    }

    @Override
    public void doUpdateAvatar(File file) {
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.updateAvatarFailed(msg);
            }

            @Override
            public void _onNext(BaseModel baseModel) {
                if (mView != null)
                    mView.updateAvatarSuccess(baseModel);
            }

        };
        addSubscribe(sHttpClient.doUpdateAvatar(file)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    public void doUpdateInfo(Bundle bundle, int type) {
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.updateInfoFailed(msg);
            }

            @Override
            public void _onNext(BaseModel baseModel) {
                if (mView != null)
                    mView.updateInfoSuccess();
            }
        };
        addSubscribe(sHttpClient.doUpdateInfo(bundle, type)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
