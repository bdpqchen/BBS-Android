package com.twtstudio.bbs.bdpqchen.bbs.forum;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
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
    public void getForumBoardList() {
        SimpleObserver<ForumBoardModel> observer = new SimpleObserver<ForumBoardModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.getForumBoardFailed(msg);
            }

            @Override
            public void _onNext(ForumBoardModel forumBoardModels) {
                if (mView != null) mView.onGotForumBoard(forumBoardModels);
            }

        };
        addSubscribe(sHttpClient.getForumList()
                        .map(new ResponseTransformer<>())
//                .flatMap((Function<List<ForumModel>, ObservableSource<ForumModel>>) forumModels -> Observable.fromIterable(forumModels))
                        .flatMap(Observable::fromIterable)
                        .flatMap(forumModel -> sHttpClient.getBoardList(forumModel.getId()))
                        .map(new ResponseTransformer<>())
//                .map(new Function<BoardsModel, ForumBoardModel>() {
                        .map(forumList -> {
                            List<BoardsModel.BoardsBean> boards = forumList.getBoards();
                            List<ForumBoardModel.BoardModel> resultBoardList = new ArrayList<>();
                            for (int i = 0; i < boards.size(); i++) {
                                resultBoardList.add(new ForumBoardModel.BoardModel(boards.get(i).getId(), boards.get(i).getName()));
                            }
                            return new ForumBoardModel(forumList.getForum().getId(),
                                    forumList.getForum().getName(),
                                    resultBoardList);

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer)
        );


    }


}
