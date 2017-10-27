package com.twtstudio.bbs.bdpqchen.bbs.individual.release.publish;

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

public class PublishPresenter extends RxPresenter implements PublishContract.Presenter {
    private PublishContract.View mView;
    PublishPresenter(PublishContract.View view) {
        mView = view;
    }

    @Override
    public void getPublishList(int page) {
        SimpleObserver<List<PublishEntity>> observer = new SimpleObserver<List<PublishEntity>>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onGetPublishFailed(msg);
            }

            @Override
            public void _onNext(List<PublishEntity> list) {
                if (mView != null)
                    mView.onGetPublishList(list);
            }
        };
        addSubscribe(sHttpClient.getPublishList(page)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    @Override
    public void deleteThread(int tid, int position) {
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onDeleteThreadFailed(msg);
            }

            @Override
            public void _onNext(BaseModel entity) {

                if (mView != null)
                    mView.onDeleteThread(entity, position);
            }
        };
        addSubscribe(sHttpClient.deleteThread(tid)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }


}
