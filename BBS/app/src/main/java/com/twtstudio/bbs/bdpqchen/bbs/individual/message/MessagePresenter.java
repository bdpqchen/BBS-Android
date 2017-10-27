package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ricky on 2017/5/13.
 */

public class MessagePresenter extends RxPresenter implements MessageContract.Presenter {
    private MessageContract.View mView;

    MessagePresenter(MessageContract.View view) {
        mView = view;
    }

    @Override
    public void getMessageList(int page) {

        SimpleObserver<List<MessageModel>> observer = new SimpleObserver<List<MessageModel>>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onGetMessageFailed(msg);
            }

            @Override
            public void _onNext(List<MessageModel> messageModels) {
                if (mView != null)
                    mView.showMessageList(messageModels);
            }
        };

        addSubscribe(sHttpClient.getMessageList(page)
                .map(new ResponseTransformer<>())
//                .map(messageModels -> messageModels)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );

    }

    public void doClearUnreadMessage() {
        ResponseTransformer<BaseModel> transformer = new ResponseTransformer<>();
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                mView.onClearFailed(msg);
            }

            @Override
            public void _onNext(BaseModel baseModel) {
                mView.onCleared();
            }
        };
        addSubscribe(sHttpClient.doClearUnreadMessage()
                .map(transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );

    }

    @Override
    public void confirmFriend(int position, int id, int bool) {
        ResponseTransformer<BaseModel> transformer = new ResponseTransformer<>();
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                mView.onConfirmFriendFailed(msg, position);
            }

            @Override
            public void _onNext(BaseModel baseModel) {
                mView.onConfirmFriendSuccess(baseModel, position, bool);
            }
        };
        addSubscribe(sHttpClient.confirmFriend(id, bool)
                .map(transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
