package com.twtstudio.bbs.bdpqchen.bbs.main.hot;

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

public class HotFragment extends BaseFragment<MainPresenter> implements MainContract.View {


    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.rv_hot)
    RecyclerView mRvHot;
    @BindView(R.id.srl_hot)
    SwipeRefreshLayout mSrlHot;
    @BindView(R.id.tv_hot_no_data)
    TextView mTvHotNoData;

    private HotAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean mRefreshing = false;

    public static HotFragment newInstance() {
        return new HotFragment();
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragmetn_hot;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initFragment() {
        mAdapter = new HotAdapter(getActivity());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRvHot.setLayoutManager(mLinearLayoutManager);
        mRvHot.setAdapter(mAdapter);
        mSrlHot.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        mSrlHot.setOnRefreshListener(() -> {
            mPresenter.getDataList();
            mRefreshing = true;
            mSrlHot.setRefreshing(true);
        });
        mPresenter.getDataList();
        LogUtil.d("hot ten init ");

    }

    @Override
    public void onGotDataList(MainModel model) {
        if (model.getHot() != null && model.getHot().size() > 0) {
            if (mRefreshing) {
                mAdapter.refreshList(model.getHot());
            } else {
                mAdapter.addList(model.getHot());
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
        mSrlHot.setRefreshing(b);
    }

    private void hideLoading() {
        if (mPbLoading != null) {
            mPbLoading.setVisibility(View.GONE);
        }
    }


}
