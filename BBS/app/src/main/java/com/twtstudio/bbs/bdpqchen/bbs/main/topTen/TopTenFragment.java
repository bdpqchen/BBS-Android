package com.twtstudio.bbs.bdpqchen.bbs.main.topTen;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.main.model.MainModel;

import butterknife.BindView;
/**
 * Created by bdpqchen on 17-5-11.
 */

public class TopTenFragment extends BaseFragment<TopTenPresenter> implements MainContract.View {
    TopTenAdapter mAdapter;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.topten_empty)
    TextView toptenEmpty;
    private LinearLayoutManager linearLayoutManager;
    private boolean mRefreshing = false;

    public static TopTenFragment newInstance() {
        return new TopTenFragment();
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
        mAdapter = new TopTenAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(new RecyclerViewItemDecoration(5));
        recyclerview.setAdapter(mAdapter);
        layoutSwipeRefresh.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        layoutSwipeRefresh.setOnRefreshListener(() -> {
            mPresenter.getHomeDataList();
            mRefreshing = true;
            layoutSwipeRefresh.setRefreshing(true);
        });
        mPresenter.getHomeDataList();
        LogUtil.d("top ten init ");

    }

    @Override
    public void onGotHomeData(MainModel mainModel) {
        if (mainModel.getHot() != null && mainModel.getHot().size() > 0){
            if (mRefreshing) {
                mAdapter.refreshList(mainModel.getHot());
            }else{
                mAdapter.addList(mainModel.getHot());
            }
        }else{
            toptenEmpty.setText("暂无全站十大");
            toptenEmpty.setVisibility(View.VISIBLE);
        }
        setRefreshing(false);
        hideLoading();
    }

    @Override
    public void onFailedGetHomeData(String msg) {
        hideLoading();
        setRefreshing(false);
        SnackBarUtil.notice(this.getActivity(), msg + "\n刷新试试～");
    }

    private void hideLoading() {
        if (mPbLoading != null){
            mPbLoading.setVisibility(View.GONE);
        }
    }

    void setRefreshing(boolean b){
        if (layoutSwipeRefresh != null){
            mRefreshing = b;
            layoutSwipeRefresh.setRefreshing(b);
        }
    }

}

