package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

import javax.inject.Inject;

/**
 * Created by Ricky on 2017/5/13.
 */

public class MessagePresenter extends RxPresenter<MessageContract.View> implements MessageContract.Presenter {

    private RxDoHttpClient<BaseModel> mRxDoHttpClient;

    @Inject
    MessagePresenter(RxDoHttpClient client){
        this.mRxDoHttpClient = client;
    }

    @Override
    public void getMessageList() {

    }
}
