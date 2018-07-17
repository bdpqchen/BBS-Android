package com.twtstudio.bbs.bdpqchen.bbs.main.announce

import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel
import com.twtstudio.bbs.bdpqchen.bbs.main.AnnounceEntity

interface AnnounceContract {

    interface Presenter {
        fun getAnnounce()
    }

    interface View {
        fun onGetAnnounceSuccess(announceList: List<AnnounceEntity>)
        fun onGetAnnounceDetailSucceess(thread: List<ThreadModel.ThreadBean>)
        fun onGetAnnounceFail(msg: String)
    }

}