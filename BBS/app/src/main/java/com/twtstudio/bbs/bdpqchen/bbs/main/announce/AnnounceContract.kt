package com.twtstudio.bbs.bdpqchen.bbs.main.announce

interface AnnounceContract {

    interface Presenter {
        fun getAnnounce()
    }

    interface View {
        fun onGetAnnounceSuccess(announceList: List<AnnounceEntity>)
        fun onGetAnnounceFail(msg: String)
    }

}