package com.twtstudio.bbs.bdpqchen.bbs.individual.release.reply;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.EndlessRecyclerOnScrollListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-9-18.
 */

public class ReplyFragment extends BaseFragment<ReplyPresenter> implements ReplyContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_none_reply)
    TextView mTvNoneReply;
    @BindView(R.id.rv_reply)
    RecyclerView mRvReply;
    @BindView(R.id.srl_reply)
    SwipeRefreshLayout mSrlReply;

    private ReplyAdapter mAdapter;
    private int mPage;
    private boolean mRefreshing;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_release_reply;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initFragment() {
        mAdapter = new ReplyAdapter(mContext);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvReply.setLayoutManager(manager);
        mRvReply.setAdapter(mAdapter);
        mSrlReply.setOnRefreshListener(this);
        mRvReply.addItemDecoration(new RecyclerViewItemDecoration(5));
        mRvReply.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore() {
                mPage++;
                getDataList();
            }
        });
        getDataList();
    }

    public static ReplyFragment newInstance() {
        return new ReplyFragment();
    }

    private void getDataList() {
        mPresenter.getReplyList(mPage);
    }

    @Override
    public void onRefresh() {
        mPage = 0;
        mRefreshing = true;
        getDataList();
    }

    @Override
    public void onGetReplyList(List<ReplyEntity> entityList) {
        if (mRefreshing) {
            mAdapter.clearAll();
        }
        mAdapter.addList(entityList);
        stopRefreshing();
    }

    @Override
    public void onGetReplyFailed(String m) {
        stopRefreshing();
        SnackBarUtil.notice(this.getActivity(), m);
    }

    private void stopRefreshing() {
        if (mSrlReply != null) {
            mSrlReply.setRefreshing(false);
            mRefreshing = false;
        }
    }

}
