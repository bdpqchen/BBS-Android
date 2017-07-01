package com.twtstudio.bbs.bdpqchen.bbs.individual.star;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-6-29.
 */

class StarPresenter extends RxPresenter<StarContract.View> implements StarContract.Presenter {
    private RxDoHttpClient<List<StarModel>> mHttpClient;
    private ResponseTransformer<BaseModel> transformer = new ResponseTransformer<>();

    @Inject
    StarPresenter(RxDoHttpClient httpClient) {
        mHttpClient = httpClient;
    }


    @Override
    public void getStarList() {
        SimpleObserver<List<StarModel>> observer = new SimpleObserver<List<StarModel>>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onGetStarFailed(msg);
            }
            @Override
            public void _onNext(List<StarModel> starModel) {
                if (mView != null)
                    mView.onGetStarList(starModel);
            }
        };
        addSubscribe(mHttpClient.getStarThreadList()
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    @Override
    public void starThread(int tid, int position) {
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onStarFailed(msg);
            }
            @Override
            public void _onNext(BaseModel starModelBaseResponse) {
                if (mView != null)
                    mView.onStar(position);
            }
        };
        addSubscribe(mHttpClient.starThread(tid)
                .map(transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    @Override
    public void unStarThread(int tid, int position) {
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onUnStarFailed(msg);
            }
            @Override
            public void _onNext(BaseModel starModelBaseResponse) {
                if (mView != null)
                    mView.onUnStar(position);
            }
        };
        addSubscribe(mHttpClient.unStarThread(tid)
                .map(transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
