package com.twtstudio.bbs.bdpqchen.bbs.individual2

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel

/**
 * Created by linjiaxin on 2018/7/12.
 */
interface Individual2Contract {
    interface View : BaseView {
        fun gotInfo(model: IndividualInfoModel)
        fun getInfoFailed(m: String)

    }

    interface Presenter : BasePresenter {
        fun initIndividualInfo()

    }
}
