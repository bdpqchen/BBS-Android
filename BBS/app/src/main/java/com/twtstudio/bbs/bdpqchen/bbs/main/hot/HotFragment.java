package com.twtstudio.bbs.bdpqchen.bbs.main.hot;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bdpqchen.diffutilpractice.DiffChecker2;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainContract;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.main.latest.LatestEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-6-5.
 */

public class HotFragment extends BaseFragment implements MainContract.View {

    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.rv_hot)
    RecyclerView mRvHot;
    @BindView(R.id.srl_hot)
    SwipeRefreshLayout mSrlHot;
    @BindView(R.id.tv_hot_no_data)
    TextView mTvHotNoData;

    private HotAdapter mAdapter;
    private boolean mRefreshing = false;
    private MainPresenter mPresenter;

    public static HotFragment newInstance() {
        return new HotFragment();
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragmetn_hot;
    }

    @Override
    protected void initFragment() {
        mPresenter = new MainPresenter(this);
        mAdapter = new HotAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvHot.setLayoutManager(linearLayoutManager);
        mRvHot.setAdapter(mAdapter);
        mSrlHot.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        mSrlHot.setOnRefreshListener(() -> {
            getDataList();
            mRefreshing = true;
            mSrlHot.setRefreshing(true);
        });
        getDataList();
    }

    @Override
    protected MainPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onGetLatestList(List<LatestEntity> list) {
    }

    @Override
    public void onGetHotList(List<HotEntity> list) {
        if (list != null && list.size() > 0) {
            if (mRefreshing) {
                List<HotEntity> oldList = mAdapter.getDataSets();
                DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffChecker2<HotEntity>(oldList, list) {
                    @Override
                    public boolean _areContentsTheSame(int oldItemPosition, int newItemPosition) {
                        return oldList.get(oldItemPosition).getC_post() == list.get(newItemPosition).getC_post();
                    }

                    @Override
                    public boolean _areItemsTheSame(int oldItemPosition, int newItemPosition) {
                        return oldList.get(oldItemPosition).getId() == list.get(newItemPosition).getId();
                    }
                }, true);
                mAdapter.replaceDataSets(list);
                result.dispatchUpdatesTo(mAdapter);
            } else {
                mAdapter.addList(list);
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

    private void getDataList() {
        mPresenter.getHotList();
    }

    void setRefreshing(boolean b) {
        mRefreshing = b;
        if (mSrlHot != null)
            mSrlHot.setRefreshing(b);
    }

    private void hideLoading() {
        if (mPbLoading != null)
            mPbLoading.setVisibility(View.GONE);
    }


}
