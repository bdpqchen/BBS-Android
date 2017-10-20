package com.twtstudio.bbs.bdpqchen.bbs.forum.boards;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-11.
 */

class BoardsPresenter extends RxPresenter implements BoardsContract.Presenter {

    public ResponseTransformer<BoardsModel> mTransformer = new ResponseTransformer<>();
    private BoardsContract.View mView;

    BoardsPresenter(BoardsContract.View view) {
        mView = view;
    }

    @Override
    public void getBoardList(final int forumId) {
        SimpleObserver<PreviewThreadModel> observer = new SimpleObserver<PreviewThreadModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.failedToGetBoardList(msg);
            }

            @Override
            public void _onNext(PreviewThreadModel previewThreadModels) {
                if (mView != null)
                    mView.setBoardList(previewThreadModels);
            }
        };
        addSubscribe(sHttpClient.getBoardList(forumId)
                .map(mTransformer)
                .flatMap(boardsModel -> Observable.fromIterable(boardsModel.getBoards()))
                .flatMap(boardsBean -> sHttpClient.getThreadList(boardsBean.getId(), 0))
                .map(new ResponseTransformer<>())
                .map(threadListModel -> {
                    PreviewThreadModel model = new PreviewThreadModel();
                    model.setBoard(threadListModel.getBoard());
                    if (threadListModel.getThread() != null) {
                        int listSize = threadListModel.getThread().size();
                        List<ThreadListModel.ThreadBean> threadBeanList = new ArrayList<>();
                        if (listSize >= 1) {
                            ThreadListModel.ThreadBean threadBean0 = threadListModel.getThread().get(0);
                            threadBeanList.add(threadBean0);
                        }
                        if (listSize >= 2) {
                            ThreadListModel.ThreadBean threadBean1 = threadListModel.getThread().get(1);
                            threadBeanList.add(threadBean1);
                        }
                        model.setThreadList(threadBeanList);
                    }
                    return model;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    public void getSimpleBoardList(int forumId) {
        SimpleObserver<BoardsModel> observer = new SimpleObserver<BoardsModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onFailedGetSimpleList(msg);
            }

            @Override
            public void _onNext(BoardsModel boards) {
                if (mView != null)
                    mView.onGotSimpleList(boards);
            }
        };
        addSubscribe(sHttpClient.getBoardList(forumId)
                .map(mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
