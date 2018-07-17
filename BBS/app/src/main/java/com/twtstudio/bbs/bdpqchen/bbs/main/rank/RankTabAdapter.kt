package com.twtstudio.bbs.bdpqchen.bbs.main.rank

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class RankTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val titles = mutableListOf("周榜", "月榜")

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return WeekRankFragment()
            1 -> return MonthRankFragment()
        }
        return WeekRankFragment()
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}