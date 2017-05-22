package com.twtstudio.bbs.bdpqchen.bbs.main.topTen;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.main.latestPost.LatestPostModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class TopTenFragment extends BaseFragment<TopTenPresenter> implements TopTenContract.View {
    TopTenAdapter TopTenAdapter;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;

    public static TopTenFragment newInstance() {
        TopTenFragment fragment = new TopTenFragment();
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_latest_post;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initFragment() {
        TopTenAdapter = new TopTenAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(new RecyclerViewItemDecoration(16));
        recyclerview.setAdapter(TopTenAdapter);

        layoutSwipeRefresh.setRefreshing(true);
        mPresenter.refreshAnnounce();

    }

    @Override
    public void addAnnounce(List<TopTenModel.DataBean.HotBean> announceBeen) {
        TopTenAdapter.addList(announceBeen);
        hideLoading();
    }

    @Override
    public void refreshAnnounce(List<TopTenModel.DataBean.HotBean> announceBeen) {
        TopTenAdapter.refreshList(announceBeen);
        layoutSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void failedToGetTopTen(String msg) {
        hideLoading();
        SnackBarUtil.notice(this.getActivity(), "刷新试试～");
    }

    private void hideLoading(){
        mPbLoading.setVisibility(View.GONE);
    }



}

