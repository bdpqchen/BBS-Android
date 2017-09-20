package com.twtstudio.bbs.bdpqchen.bbs.individual.release.publish;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
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

public class PublishFragment extends BaseFragment<PublishPresenter> implements PublishContract.View,
        SwipeRefreshLayout.OnRefreshListener, OnReleaseItemClickListener {

    @BindView(R.id.tv_none_publish)
    TextView mTvNonePublish;
    @BindView(R.id.rv_publish)
    RecyclerView mRvPublish;
    @BindView(R.id.srl_publish)
    SwipeRefreshLayout mSrlPublish;

    private PublishAdapter mAdapter;
    private int mPage = 0;
    private boolean mRefreshing = false;
    private MaterialDialog mDialog;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_release_publish;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initFragment() {
        mAdapter = new PublishAdapter(mContext, this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvPublish.setLayoutManager(manager);
        mRvPublish.setAdapter(mAdapter);
        mSrlPublish.setOnRefreshListener(this);
        mRvPublish.addItemDecoration(new RecyclerViewItemDecoration(5));
        mRvPublish.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore() {
                mPage++;
                getDataList();
            }
        });
        getDataList();
    }

    public static PublishFragment newInstance() {
        return new PublishFragment();
    }

    private void getDataList() {
        mPresenter.getPublishList(mPage);
    }

    @Override
    public void onRefresh() {
        mPage = 0;
        mRefreshing = true;
        getDataList();
    }

    @Override
    public void onGetPublishList(List<PublishEntity> entityList) {
        if (mRefreshing) {
            mAdapter.clearAll();
        }
        mAdapter.addList(entityList);
        stopRefreshing();
    }

    @Override
    public void onGetPublishFailed(String m) {
        stopRefreshing();
        SnackBarUtil.notice(this.getActivity(), m);
    }

    @Override
    public void onDeleteThread(BaseModel entity, int position) {
        SnackBarUtil.normal(mActivity, "一去不复返矣");
        mAdapter.deleteThread(position);
        stopProgress();
    }

    @Override
    public void onDeleteThreadFailed(String m) {
        stopProgress();
        SnackBarUtil.error(mActivity, m);
    }

    private void stopRefreshing() {
        if (mSrlPublish != null) {
            mSrlPublish.setRefreshing(false);
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
                    deleteThread(position);
                }, null);
    }

    private void deleteThread(int position) {
        mPresenter.deleteThread(mAdapter.getThreadId(position), position);
        mDialog = DialogUtil.showProgressDialog(mContext, "正在删除, 稍后..");

    }

    private void stopProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

}
