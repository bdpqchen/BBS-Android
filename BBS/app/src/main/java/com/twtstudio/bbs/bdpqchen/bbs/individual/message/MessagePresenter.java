package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ricky on 2017/5/13.
 */

public class MessagePresenter extends RxPresenter<MessageContract.View> implements MessageContract.Presenter {

    private RxDoHttpClient<List<MessageModel>> mRxDoHttpClient;

    @Inject
    MessagePresenter(RxDoHttpClient client) {
        this.mRxDoHttpClient = client;
    }

    @Override
    public void getMessageList(int page) {

        SimpleObserver<List<MessageModel>> observer = new SimpleObserver<List<MessageModel>>() {
            @Override
            public void _onError(String msg) {
                mView.onGetMessageFailed(msg);
            }

            @Override
            public void _onNext(List<MessageModel> messageModels) {
                mView.showMessageList(messageModels);
            }
        };

        addSubscribe(mRxDoHttpClient.getMessageList(page)
                .map(mRxDoHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );

    }
}
