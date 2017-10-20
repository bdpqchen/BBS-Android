package com.twtstudio.bbs.bdpqchen.bbs.individual.updatePassword;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-6.
 */

class UpdatePasswordPresenter extends RxPresenter implements UpdatePasswordContract.Presenter {
    private UpdatePasswordContract.View mView;

    UpdatePasswordPresenter(UpdatePasswordContract.View view) {
        mView = view;
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
        addSubscribe(sHttpClient.doUpdatePassword(newPass, oldPass)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }
}
