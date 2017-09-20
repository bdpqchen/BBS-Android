package com.twtstudio.bbs.bdpqchen.bbs.main.latest;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainContract;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.main.hot.HotEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-6-5.
 */

public class LatestFragment extends BaseFragment<MainPresenter> implements MainContract.View {

    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.rv_latest)
    RecyclerView mRvLatest;
    @BindView(R.id.srl_latest)
    SwipeRefreshLayout mSrlLatest;
    @BindView(R.id.tv_latest_no_data)
    TextView mTvLatestNoData;

    private LatestAdapter mAdapter;
    private boolean mRefreshing = false;

    public static LatestFragment newInstance() {
        return new LatestFragment();
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragmetn_latest;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initFragment() {
        mAdapter = new LatestAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvLatest.setLayoutManager(linearLayoutManager);
        mRvLatest.addItemDecoration(new RecyclerViewItemDecoration(16));
        mAdapter.setNoDataHeader(true);
        mRvLatest.setAdapter(mAdapter);
        mSrlLatest.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        mSrlLatest.setOnRefreshListener(() -> {
            getDataList();
            mRefreshing = true;
            mSrlLatest.setRefreshing(true);
        });
        getDataList();
    }

    @Override
    public void onGetLatestList(List<LatestEntity> list) {
        if (list != null && list.size() > 0) {
            // add the header view data
            if (mRefreshing) {
                mAdapter.clearAll();
            }
            mAdapter.addFirst(new LatestEntity());
            mAdapter.addList(list);
        }
        setRefreshing(false);
        hideLoading();
    }

    @Override
    public void onGetHotList(List<HotEntity> list) {

    }

    @Override
    public void onGotDataFailed(String msg) {
        hideLoading();
        setRefreshing(false);
        SnackBarUtil.notice(this.getActivity(), msg + "\n刷新试试");
    }

    void setRefreshing(boolean b) {
        mRefreshing = b;
        mSrlLatest.setRefreshing(b);
    }

    private void hideLoading() {
        if (mPbLoading != null) {
            mPbLoading.setVisibility(View.GONE);
        }
    }

    public void getDataList() {
        mPresenter.getLatestList();
    }
}
