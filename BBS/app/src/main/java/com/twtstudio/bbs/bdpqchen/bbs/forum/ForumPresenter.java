package com.twtstudio.bbs.bdpqchen.bbs.forum;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-11.
 */

class ForumPresenter extends RxPresenter<ForumContract.View> implements ForumContract.Presenter {


    private RxDoHttpClient<List<ForumModel>> mHttpClient;

    @Inject
    ForumPresenter(RxDoHttpClient client) {
        mHttpClient = client;
    }


    @Override
    public void getForumList() {

        SimpleObserver<List<ForumModel>> observer = new SimpleObserver<List<ForumModel>>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.failedToGetForum(msg);
            }

            @Override
            public void _onNext(List<ForumModel> forumModels) {
//                LogUtil.d(forumModels.size());
//                LogUtil.d(mView);
                if (mView != null)
                    mView.showForumList(forumModels);
            }

        };
        addSubscribe(mHttpClient.getForumList()
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
