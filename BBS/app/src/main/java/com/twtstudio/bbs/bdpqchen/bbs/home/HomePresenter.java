package com.twtstudio.bbs.bdpqchen.bbs.home;


import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by bdpqchen on 17-4-21.
 */

public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {

    private RxDoHttpClient<IndividualInfoModel> mHttpClient;
    private ResponseTransformer<IndividualInfoModel> mTransformer = new ResponseTransformer<>();

    @Inject
    public HomePresenter(RxDoHttpClient httpClient) {
        this.mHttpClient = httpClient;

    }

    @Override
    public void checkUpdate(int currentVersionCode) {
//        LogUtil.d("show the method--> checkUpdate()");
    }


    @Override
    public void initIndividualInfo() {
        SimpleObserver<IndividualInfoModel> observer = new SimpleObserver<IndividualInfoModel>() {
            @Override
            public void _onError(String msg) {
                PrefUtil.setIsLatestInfo(false);
            }

            @Override
            public void _onNext(IndividualInfoModel individualInfoModel) {
                LogUtil.d(individualInfoModel.getNickname());
                mView.showIndividualInfo(individualInfoModel);
            }
        };
        addSubscribe(mHttpClient.getIndividualInfo()
                .map(mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
