package com.twtstudio.bbs.bdpqchen.bbs.main.latestPost;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class LatestPostFragment extends BaseFragment<LatestPostPresenter> implements LatestPostContract.View {
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    LatestPostAdapter latestPostAdapter;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    private LinearLayoutManager linearLayoutManager;

    public static LatestPostFragment newInstance() {
        LogUtil.dd("new instance latest fragment");
        return new LatestPostFragment();
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
        latestPostAdapter = new LatestPostAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        //initData();
        mPresenter.refreshAnnounce();
        recyclerview.setAdapter(latestPostAdapter);
//        recyclerview.addItemDecoration(new RecyclerViewItemDecoration(10));
        layoutSwipeRefresh.setOnRefreshListener(() -> {
            mPresenter.refreshAnnounce();
            layoutSwipeRefresh.setRefreshing(false);

        });
    }

    @Override
    public void addAnnounce(List<LatestPostModel.DataBean.LatestBean> announceBeen) {
        latestPostAdapter.addList(announceBeen);
        hideLoading();
    }

    @Override
    public void refreshAnnounce(List<LatestPostModel.DataBean.LatestBean> announceBeen) {
        latestPostAdapter.refreshList(announceBeen);
        hideLoading();
    }

    @Override
    public void failedToGetLatestPost(String msg) {
        hideLoading();
        SnackBarUtil.notice(this.getActivity(), "加载失败，刷新试试～");
    }

    void initData() {
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

    private void hideLoading(){
        mPbLoading.setVisibility(View.GONE);
    }
}
