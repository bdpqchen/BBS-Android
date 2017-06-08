package com.twtstudio.bbs.bdpqchen.bbs.main.latest;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainContract;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainModel;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainPresenter;

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
    private LinearLayoutManager mLinearLayoutManager;
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
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRvLatest.setLayoutManager(mLinearLayoutManager);
        mRvLatest.setAdapter(mAdapter);
        mSrlLatest.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        mSrlLatest.setOnRefreshListener(() -> {
            mPresenter.getDataList();
            mRefreshing = true;
            mSrlLatest.setRefreshing(true);
        });
        mPresenter.getDataList();
        LogUtil.d("latest ten init ");

    }

    @Override
    public void onGotDataList(MainModel model) {
        if (model.getLatest() != null && model.getLatest().size() > 0) {
            if (mRefreshing) {
                mAdapter.refreshList(model.getLatest());
            } else {
                mAdapter.addList(model.getLatest());
            }
        }
        setRefreshing(false);
        hideLoading();
    }

    @Override
    public void onGotDataFailed(String msg) {
        hideLoading();
        setRefreshing(false);
        SnackBarUtil.notice(this.getActivity(), msg + "\n刷新试试～");
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

}
