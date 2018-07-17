package com.twtstudio.bbs.bdpqchen.bbs.main.rank

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.ItemAdapter
import cn.edu.twt.retrox.recyclerviewdsl.ItemManager
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.main.Rank
import kotterknife.bindView

class WeekRankFragment : SimpleFragment(), RankContract.View {

    private val recyclerView: RecyclerView by bindView(R.id.rank_week_rv)
    private val mPresenter = RankPresenter(this)
    lateinit var itemManager: ItemManager
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun initFragments() {
        itemManager = ItemManager()
        linearLayoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = ItemAdapter(itemManager)
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.layoutManager = linearLayoutManager
        mPresenter.getWeekRank()
    }

    override fun getPerMainFragmentLayoutId() = R.layout.fragment_week_rank

    override fun onGetRankSucces(t: List<Rank>) {
        val list = mutableListOf<RankItem>()
        for (i in 0..9) {
            list.add(RankItem(t[i], mContext, i + 1, RankItem.RANK_WEEK))
        }
        recyclerView.withItems(list)
    }

    override fun onGetRankFail(msg: String) {
        SnackBarUtil.error(mActivity, msg)
    }

}