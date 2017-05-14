package com.twtstudio.bbs.bdpqchen.bbs.main.latestPost;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class LatestPostFragment extends BaseFragment<LatestPostPresenter> implements LatestPostContract.View {
    private static final String ARG_LATESTPOST_TYPE = "ARG_LATESPOST_TYPE";
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    Unbinder unbinder;
    LatestPostAdapter latestPostAdapter;
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
        latestPostAdapter=new LatestPostAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        initData();
        mPresenter.refreshAnnounce();
        recyclerview.setAdapter(latestPostAdapter);
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
<<<<<<< HEAD
    public void addAnnounce(List<LatestPostModel.AnnounceBean> announceBeen) {
=======
    public void addLatestPostList(List<LatestPostModel.AnnounceBean> announceBeen) {
>>>>>>> e3882c000025967bd9b94f720b4bd3fc38106ef6
        latestPostAdapter.addList(announceBeen);
    }

    @Override
<<<<<<< HEAD
    public void refreshAnnounce(List<LatestPostModel.AnnounceBean> announceBeen) {
=======
    public void refreshLatestPostList(List<LatestPostModel.AnnounceBean> announceBeen) {
>>>>>>> e3882c000025967bd9b94f720b4bd3fc38106ef6
        latestPostAdapter.refreshList(announceBeen);
    }

    @Override
    public void failedToGetLatestPost(String msg) {

    }
<<<<<<< HEAD
    void initData(){
        List<LatestPostModel.AnnounceBean> announceBeens= new ArrayList<>();
        for(int i=1;i<=10;i++){
            LatestPostModel.AnnounceBean announceBean = new LatestPostModel.AnnounceBean();
            announceBean.setTitle("这是标题" +i);
            announceBean.setContent("这是内容"+i);
            announceBeens.add(announceBean);
        }
        latestPostAdapter.addList(announceBeens);
=======
    void initData() {
        List<LatestPostModel> latestPostModels=new ArrayList<>();
>>>>>>> e3882c000025967bd9b94f720b4bd3fc38106ef6
    }
}
