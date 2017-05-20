package com.twtstudio.bbs.bdpqchen.bbs.main.content;


import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangyulong on 5/19/17.
 */

public class ContentPresenter extends RxPresenter<ContentContract.View> implements ContentContract.Presenter {
    public RxDoHttpClient<ContentModel.DataBean> mHttpClient;
    @Inject
    ContentPresenter(RxDoHttpClient client){
        mHttpClient = client;
    }
    public void getData(String threadid){
        SimpleObserver<ContentModel.DataBean> observer = new SimpleObserver<ContentModel.DataBean>() {
            @Override
            public void _onError(String msg) {
                mView.failedToGetContent(msg);
            }

            @Override
            public void _onNext(ContentModel.DataBean ContentModel) {
                mView.showPost(ContentModel.getPost());
                mView.showThread(ContentModel.getThread());
            }

        };
        addSubscribe(mHttpClient.getIndexContent(threadid)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
