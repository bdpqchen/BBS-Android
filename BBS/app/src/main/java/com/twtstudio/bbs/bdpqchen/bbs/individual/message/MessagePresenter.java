package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.BaseResponse;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ricky on 2017/5/13.
 */

public class MessagePresenter extends RxPresenter<MessageContract.View> implements MessageContract.Presenter {

    private RxDoHttpClient<BaseModel> mRxDoHttpClient;

    @Inject
    MessagePresenter(RxDoHttpClient client) {
        this.mRxDoHttpClient = client;
    }

    @Override
    public void getMessageList(int page) {
        mRxDoHttpClient.getMessageList(1)
                .map(BaseResponse::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::showMessageList);
    }
}
