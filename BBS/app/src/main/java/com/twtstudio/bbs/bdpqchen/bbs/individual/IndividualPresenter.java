package com.twtstudio.bbs.bdpqchen.bbs.individual;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
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
    private ResponseTransformer<Integer> mUnreadTrans = new ResponseTransformer<>();

    @Inject
    IndividualPresenter(RxDoHttpClient httpClient){
        this.mHttpClient = httpClient;
    }

    @Override
    public void initIndividualInfo() {
        SimpleObserver<IndividualInfoModel> observer = new SimpleObserver<IndividualInfoModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null){
                    PrefUtil.setIsLatestInfo(false);
                    mView.getInfoFailed(msg);
                }
            }

            @Override
            public void _onNext(IndividualInfoModel individualInfoModel) {
                LogUtil.dd(individualInfoModel.getNickname() + "1");
                if (mView != null){
                    mView.gotInfo(individualInfoModel);
                }
            }
        };
        addSubscribe(mHttpClient.getIndividualInfo()
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }


    @Override
    public void getUnreadMessageCount() {

        SimpleObserver<Integer> observer = new SimpleObserver<Integer>() {
            @Override
            public void _onError(String msg) {
//                LogUtil.d(msg);
                if (mView != null){
                    mView.onGetMessageFailed(msg);
                }
            }
            @Override
            public void _onNext(Integer integer) {
//                LogUtil.d(integer);
                if (mView != null){
                    mView.onGotMessageCount(integer);
                }
            }
        };
        addSubscribe(mHttpClient.getUnreadCount()
                .map(mUnreadTrans)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }


}
