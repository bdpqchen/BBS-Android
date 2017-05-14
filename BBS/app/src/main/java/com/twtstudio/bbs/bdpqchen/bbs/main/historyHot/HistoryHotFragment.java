package com.twtstudio.bbs.bdpqchen.bbs.main.historyHot;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class HistoryHotFragment extends BaseFragment<HistoryHotPresenter> implements HistoryHotContract.View {
    private static final String ARG_HistoryHot_TYPE = "ARG_LATESPOST_TYPE";
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    Unbinder unbinder;
    com.twtstudio.bbs.bdpqchen.bbs.main.historyHot.HistoryHotAdapter HistoryHotAdapter;
    private LinearLayoutManager linearLayoutManager;

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
        HistoryHotAdapter=new HistoryHotAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        initData();
        mPresenter.refreshAnnounce();
        recyclerview.setAdapter(HistoryHotAdapter);
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
    public void addAnnounce(List<HistoryHotModel.AnnounceBean> announceBeen) {
        HistoryHotAdapter.addList(announceBeen);
    }

    @Override
    public void refreshAnnounce(List<HistoryHotModel.AnnounceBean> announceBeen) {
        HistoryHotAdapter.refreshList(announceBeen);
    }

    @Override
    public void failedToGetHistoryHot(String msg) {

    }
   
    void initData(){
        List<HistoryHotModel.AnnounceBean> announceBeens= new ArrayList<>();
        for(int i=1;i<=10;i++){
            HistoryHotModel.AnnounceBean announceBean = new HistoryHotModel.AnnounceBean();
            announceBean.setTitle("历史热门这是标题" +i);
            announceBean.setContent("历史热门这是内容"+i);
            announceBeens.add(announceBean);
        }
        HistoryHotAdapter.addList(announceBeens);
    }
}

