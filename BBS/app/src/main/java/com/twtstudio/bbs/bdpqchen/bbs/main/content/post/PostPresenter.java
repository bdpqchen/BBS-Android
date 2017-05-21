package com.twtstudio.bbs.bdpqchen.bbs.main.content.post;

import android.content.Context;


import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangyulong on 5/20/17.
 */

public class PostPresenter extends RxPresenter<PostContract.View> {
    private RxDoHttpClient<IndexPostModel> mHttpClient;
    private Context mContext;

    @Inject
    public PostPresenter(RxDoHttpClient client) {
        mHttpClient = client;

    }

    public void doPost(String threadid, String comment) {

        addSubscribe(mHttpClient.putComment(threadid,comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mHttpClient.mTransformer)
                .subscribeWith(new SimpleObserver<IndexPostModel>() {
                    @Override
                    public void _onError(String msg) {
                        LogUtil.d("_onError", msg);
                        mView.PostFailed(msg);

                    }

                    @Override
                    public void _onNext(IndexPostModel IndexPostModel) {
                        LogUtil.d("_onNext()", IndexPostModel);
                        mView.PostSuccess(IndexPostModel);
                    }

                }));



    }


}
