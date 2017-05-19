package com.twtstudio.bbs.bdpqchen.bbs.main.topTen;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.topTen.TopTenAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.main.topTen.TopTenContract;
import com.twtstudio.bbs.bdpqchen.bbs.main.topTen.TopTenModel;
import com.twtstudio.bbs.bdpqchen.bbs.main.topTen.TopTenPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class TopTenFragment extends BaseFragment<TopTenPresenter> implements TopTenContract.View {
    private static final String ARG_TopTen_TYPE = "ARG_LATESPOST_TYPE";
    /*@BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;*/
    Unbinder unbinder;
    TopTenAdapter TopTenAdapter;
    private LinearLayoutManager linearLayoutManager;

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
        TopTenAdapter=new TopTenAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        //recyclerview.setLayoutManager(linearLayoutManager);
        //initData();
       // mPresenter.refreshAnnounce();
        //recyclerview.setAdapter(TopTenAdapter);
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
    public void addAnnounce(List<TopTenModel.DataBean.LatestBean> announceBeen) {
        TopTenAdapter.addList(announceBeen);
    }

    @Override
    public void refreshAnnounce(List<TopTenModel.DataBean.LatestBean> announceBeen) {
        TopTenAdapter.refreshList(announceBeen);
    }

    @Override
    public void failedToGetTopTen(String msg) {

    }
   
    void initData(){
        List<TopTenModel.DataBean.LatestBean> announceBeens= new ArrayList<>();
        for(int i=1;i<=10;i++){
            TopTenModel.DataBean.LatestBean announceBean = new TopTenModel.DataBean.LatestBean();
            announceBean.setTitle("全站十大这是标题" +i);
            //announceBean.setContent("全站十大这是内容"+i);
            announceBeens.add(announceBean);
        }
        TopTenAdapter.addList(announceBeens);
    }
}

