package com.twtstudio.bbs.bdpqchen.bbs.individual2

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by linjiaxin on 2018/7/12.
 */
class Individual2Presenter(view: Individual2Contract.View?) : RxPresenter(), Individual2Contract.Presenter {
    private var mView: Individual2Contract.View? = view
    override fun initIndividualInfo() {
        val observer: SimpleObserver<IndividualInfoModel> = object : SimpleObserver<IndividualInfoModel>() {
            override fun _onError(msg: String?) {
                mView?.getInfoFailed(msg!!)
            }

            override fun _onNext(individualInfoModel: IndividualInfoModel?) {
                mView?.gotInfo(individualInfoModel!!)
            }
        }
        addSubscribe(sHttpClient.individualInfo
                .map(ResponseTransformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )

    }
}