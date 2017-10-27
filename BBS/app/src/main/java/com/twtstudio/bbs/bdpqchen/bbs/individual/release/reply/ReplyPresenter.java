package com.twtstudio.bbs.bdpqchen.bbs.individual.release.reply;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-9-18.
 */

class ReplyPresenter extends RxPresenter implements ReplyContract.Presenter {
    private ReplyContract.View mView;

    ReplyPresenter(ReplyContract.View view) {
        mView = view;
    }

    @Override
    public void getReplyList(int page) {
        SimpleObserver<List<ReplyEntity>> observer = new SimpleObserver<List<ReplyEntity>>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onGetReplyFailed(msg);
            }

            @Override
            public void _onNext(List<ReplyEntity> list) {
                if (mView != null)
                    mView.onGetReplyList(list);
            }
        };
        addSubscribe(sHttpClient.getReplyList(page)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    @Override
    public void deletePost(int pid, int position) {
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onDeleteFailed(msg);
            }

            @Override
            public void _onNext(BaseModel entity) {

                if (mView != null)
                    mView.onDeletePost(entity, position);
            }
        };
        addSubscribe(sHttpClient.deletePost(pid)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }


}
