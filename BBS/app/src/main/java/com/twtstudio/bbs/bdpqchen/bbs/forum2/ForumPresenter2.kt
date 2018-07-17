package com.twtstudio.bbs.bdpqchen.bbs.forum2

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumBoardModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ForumPresenter2(val mView: ForumContract2.View) : RxPresenter(), ForumContract2.Presenter {

    override fun getBoardList() {
        val observer = object : SimpleObserver<ForumBoardModel>() {

            private val list = mutableListOf<ForumBoardModel>()

            override fun _onError(msg: String) {
                mView.onGetListFail(msg)
            }

            override fun _onNext(t: ForumBoardModel) {
                list.add(t)
            }

            override fun onComplete() {
                super.onComplete()
                mView.onGetListSuccess(list)
            }
        }

        addSubscribe(sHttpClient.forumList
                .map { it.data }
                .flatMap { Observable.fromIterable(it) }
                .concatMap { sHttpClient.getBoardList(it.id) }
                .map { it.data }
                .map { t -> ForumBoardModel(t.forum.id, t.forum.name, t.boards.map { board -> ForumBoardModel.BoardModel(board.id, board.name, board.anonymous) }) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )

    }

}