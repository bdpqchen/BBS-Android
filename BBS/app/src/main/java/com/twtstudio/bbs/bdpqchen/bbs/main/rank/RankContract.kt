package com.twtstudio.bbs.bdpqchen.bbs.main.rank

import com.twtstudio.bbs.bdpqchen.bbs.main.Rank

interface RankContract {

    interface Presenter {
        fun getWeekRank()
        fun getMonthRank()
    }

    interface View {
        fun onGetRankSucces(t: List<Rank>)
        fun onGetRankFail(msg: String)
    }

}