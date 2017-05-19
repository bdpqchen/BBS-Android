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
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.main.EndLessOnScrollListener;

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
    public LatestPostFragment(){
        LogUtil.dd("getItem");
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
        latestPostAdapter=new LatestPostAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        //initData();
        mPresenter.refreshAnnounce();
        layoutSwipeRefresh.setRefreshing(true);

        recyclerview.setAdapter(latestPostAdapter);
        recyclerview.setOnScrollListener(new EndLessOnScrollListener(linearLayoutManager){
            public void onLoadMore(int currentPage) {
                mPresenter.addAnnounce();
            }
        });
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

    public void addAnnounce(List<LatestPostModel.DataBean.LatestBean> announceBeen) {



        latestPostAdapter.addList(announceBeen);
    }

    @Override

    public void refreshAnnounce(List<LatestPostModel.DataBean.LatestBean> announceBeen) {
        latestPostAdapter.refreshList(announceBeen);
        layoutSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void failedToGetLatestPost(String msg) {

    }

    void initData () {
            List<LatestPostModel.DataBean.LatestBean> announceBeens = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                LatestPostModel.DataBean.LatestBean announceBean = new LatestPostModel.DataBean.LatestBean();
                announceBean.setTitle("这是标题" + i);
                announceBean.setAuthor_nickname("这是内容" + i);
                announceBeens.add(announceBean);
            }
            latestPostAdapter.addList(announceBeens);


            List<LatestPostModel> latestPostModels = new ArrayList<>();

        }
}
