package com.twtstudio.bbs.bdpqchen.bbs.main.topTen;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.latestPost.LatestPostModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class TopTenFragment extends BaseFragment<TopTenPresenter> implements TopTenContract.View {
    Unbinder unbinder;
    TopTenAdapter TopTenAdapter;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    @BindView(R.id.topten_empty)
    TextView toptenEmpty;
    private LinearLayoutManager linearLayoutManager;

    public static TopTenFragment newInstance() {
        TopTenFragment fragment = new TopTenFragment();
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragmetn_topten;
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
        mPresenter.refreshAnnounce();
        layoutSwipeRefresh.setRefreshing(true);
        recyclerview.setAdapter(TopTenAdapter);
        layoutSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(

        ) {
            @Override
            public void onRefresh() {

                mPresenter.refreshAnnounce();
                layoutSwipeRefresh.setRefreshing(false);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void addAnnounce(List<TopTenModel.DataBean.HotBean> announceBeen) {
        TopTenAdapter.addList(announceBeen);
    }

    @Override
    public void refreshAnnounce(List<TopTenModel.DataBean.HotBean> announceBeen) {
        if (announceBeen.toString() != "[]")
            TopTenAdapter.refreshList(announceBeen);
        else
            toptenEmpty.setText("暂无全站十大");
        layoutSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void failedToGetTopTen(String msg) {

    }


}

