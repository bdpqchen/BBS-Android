package com.twtstudio.bbs.bdpqchen.bbs.individual.release.reply;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.DialogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.EndlessRecyclerOnScrollListener;
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.OnReleaseItemClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-9-18.
 */

public class ReplyFragment extends BaseFragment implements ReplyContract.View, SwipeRefreshLayout.OnRefreshListener, OnReleaseItemClickListener {

    @BindView(R.id.tv_none_reply)
    TextView mTvNoneReply;
    @BindView(R.id.rv_reply)
    RecyclerView mRvReply;
    @BindView(R.id.srl_reply)
    SwipeRefreshLayout mSrlReply;

    private MaterialDialog mDialog;
    private ReplyAdapter mAdapter;
    private int mPage;
    private boolean mRefreshing;
    private ReplyPresenter mPresenter;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_release_reply;
    }

    @Override
    protected void initFragment() {
        mPresenter = new ReplyPresenter(this);
        mAdapter = new ReplyAdapter(mContext, this);
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

    @Override
    protected ReplyPresenter getPresenter() {
        return mPresenter;
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

    @Override
    public void onDeletePost(BaseModel entity, int position) {
        SnackBarUtil.normal(mActivity, "一去不复返矣");
        mAdapter.deletePost(position);
        stopProgress();
    }

    @Override
    public void onDeleteFailed(String m) {
        stopProgress();
        SnackBarUtil.error(mActivity, m);
    }

    private void stopRefreshing() {
        if (mSrlReply != null) {
            mSrlReply.setRefreshing(false);
            mRefreshing = false;
        }
    }

    @Override
    public void onDeleteClick(int position) {
        DialogUtil.alertDialog(mContext,
                "最后的警告: 是否要删除本条记录?",
                "是",
                "否",
                (dialog, which) -> {
                    deletePost(position);
                }, null);
    }

    private void deletePost(int position) {
        mPresenter.deletePost(mAdapter.getPostId(position), position);
        mDialog = DialogUtil.showProgressDialog(mContext, "正在删除, 稍后..");
    }

    private void stopProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

}
