package com.twtstudio.bbs.bdpqchen.bbs.main.historyHot;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class HistoryHotFragment extends BaseFragment<HistoryHotPresenter> implements HistoryHotContract.View {
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    Unbinder unbinder;
    com.twtstudio.bbs.bdpqchen.bbs.main.historyHot.HistoryHotAdapter HistoryHotAdapter;
    @BindView(R.id.empty)
    TextView empty;
    private LinearLayoutManager linearLayoutManager;

    public static HistoryHotFragment newInstance() {
        HistoryHotFragment fragment = new HistoryHotFragment();
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_history_hot;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initFragment() {
        HistoryHotAdapter = new HistoryHotAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        //layoutSwipeRefresh.setRefreshing(true);
        empty.setText("暂无历史热门");
        mPresenter.refreshAnnounce();
        recyclerview.setAdapter(HistoryHotAdapter);
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
    public void addAnnounce(List<HistoryHotModel.DataBean.HistoryhotBean> announceBeen) {
        HistoryHotAdapter.addList(announceBeen);
    }

    @Override
    public void refreshAnnounce(List<HistoryHotModel.DataBean.HistoryhotBean> announceBeen) {
        if (announceBeen.toString() != "[]")
            HistoryHotAdapter.refreshList(announceBeen);
        else
            empty.setText("暂无评论");
        layoutSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void failedToGetHistoryHot(String msg) {

    }

}

