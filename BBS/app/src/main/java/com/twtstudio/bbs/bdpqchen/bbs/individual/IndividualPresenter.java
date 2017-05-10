package com.twtstudio.bbs.bdpqchen.bbs.individual;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-4.
 */

public class IndividualPresenter extends RxPresenter<IndividualContract.View> implements IndividualContract.Presenter {

    private RxDoHttpClient<IndividualInfoModel> mHttpClient;

    @Inject
    IndividualPresenter(RxDoHttpClient httpClient){
        this.mHttpClient = httpClient;
    }

    @Override
    public void doUpdateInfo(Bundle bundle) {
        SimpleObserver<IndividualInfoModel> observer = new SimpleObserver<IndividualInfoModel>() {
            @Override
            public void _onError(String msg) {
                mView.updateInfoSuccess();
            }

            @Override
            public void _onNext(IndividualInfoModel individualInfoModel) {
                mView.updateInfoSuccess();
            }
        };
        addSubscribe(mHttpClient.getIndividualInfo()
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
