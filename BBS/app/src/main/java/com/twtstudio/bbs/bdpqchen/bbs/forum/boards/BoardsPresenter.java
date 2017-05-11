package com.twtstudio.bbs.bdpqchen.bbs.forum.boards;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.BaseResponse;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadModel;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-11.
 */

class BoardsPresenter extends RxPresenter<BoardsContract.View> implements BoardsContract.Presenter {

    private RxDoHttpClient<BoardsModel> mHttpClient;
    public ResponseTransformer<BoardsModel> mTransformer = new ResponseTransformer<>();
    public ResponseTransformer<ThreadModel> mTransformerThread = new ResponseTransformer<>();


    @Inject
    BoardsPresenter(RxDoHttpClient httpClient) {
        mHttpClient = httpClient;

    }


    @Override
    public void getBoardList(final int boardId) {

        SimpleObserver<ThreadModel> observer = new SimpleObserver<ThreadModel>() {

            @Override
            public void _onError(String msg) {
//                mView.failedToGetForum(msg);
                LogUtil.dd("message", msg);
                LogUtil.dd("get board list onError()");
            }

            @Override
            public void _onNext(ThreadModel threadModel) {
                LogUtil.d("OnNext()");
            }

        };


        addSubscribe(mHttpClient.getBoardList(boardId)
                .map(mTransformer)
                .flatMap(new Function<BoardsModel, Observable<BoardsModel.BoardsBean>>() {
                    @Override
                    public Observable<BoardsModel.BoardsBean> apply(@NonNull BoardsModel boardsModel) throws Exception {
                        LogUtil.dd("apply()", String.valueOf(boardsModel.getBoards().size()));
//                        List<Integer> list = new ArrayList<>();
                        return Observable.fromIterable(boardsModel.getBoards());

                    }
                })
                .flatMap(new Function<BoardsModel.BoardsBean, Observable<BaseResponse<ThreadModel>>>() {
                    @Override
                    public Observable<BaseResponse<ThreadModel>> apply(@NonNull BoardsModel.BoardsBean boardsBean) throws Exception {

                        return mHttpClient.getThreadList(boardsBean.getId(), 1);
                    }
                })
                .map(mTransformerThread)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
