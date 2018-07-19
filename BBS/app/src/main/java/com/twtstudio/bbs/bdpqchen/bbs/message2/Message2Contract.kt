package com.twtstudio.bbs.bdpqchen.bbs.message2

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel

/**
 * Created by linjiaxin on 2018/7/18.
 */
interface Message2Contract {
    interface View : BaseView {
        fun onGetMessageFailed(m: String)
        fun showMessageList(messageList: List<MessageModel>)
        fun onCleared()
        fun onClearFailed(msg: String)
    }

    interface Presenter : BasePresenter {
        fun getMessageList(page: Int)
        fun doClearUnreadMessage()
    }
}