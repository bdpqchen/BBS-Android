package com.twtstudio.bbs.bdpqchen.bbs.message2

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by linjiaxin on 2018/7/18.
 */
class Message2Presenter(view: Message2Contract.View?) : RxPresenter(), Message2Contract.Presenter {
    private var mView: Message2Contract.View? = view
    override fun getMessageList(page: Int) {
        val observer: SimpleObserver<List<MessageModel>> = object : SimpleObserver<List<MessageModel>>() {
            override fun _onError(msg: String) {
                mView?.onGetMessageFailed(msg!!)
            }

            override fun _onNext(messageModels: List<MessageModel>) {
                mView?.showMessageList(messageModels!!)
            }
        }
        addSubscribe(RxPresenter.sHttpClient.getMessageList(page)
                .map(ResponseTransformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer))
    }

    override fun doClearUnreadMessage() {
        val transformer = ResponseTransformer<BaseModel>()
        val observer = object : SimpleObserver<BaseModel>() {
            override fun _onError(msg: String) {
                mView?.onClearFailed(msg)
            }

            override fun _onNext(baseModel: BaseModel) {
                mView?.onCleared()
            }
        }
        addSubscribe(RxPresenter.sHttpClient.doClearUnreadMessage()
                .map(transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith<SimpleObserver<BaseModel>>(observer)
        )
    }
}