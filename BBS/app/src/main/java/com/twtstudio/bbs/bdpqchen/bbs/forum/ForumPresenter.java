package com.twtstudio.bbs.bdpqchen.bbs.forum;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-11.
 */

class ForumPresenter extends RxPresenter implements ForumContract.Presenter {

    private ForumContract.View mView;

    ForumPresenter(ForumContract.View view) {
        mView = view;
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
                if (mView != null)
                    mView.showForumList(forumModels);
            }

        };
        addSubscribe(sHttpClient.getForumList()
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
