package com.twtstudio.bbs.bdpqchen.bbs.forum.forum;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-5-3.
 */

public class ForumFragment extends BaseFragment<ForumPresenter> implements ForumContract.View {

    @BindView(R.id.tv_title_toolbar)
    TextView mTvTitleToolbar;
    @BindView(R.id.rv_forum_list)
    RecyclerView mRvForumList;

    ForumAdapter mAdapter;
    Unbinder unbinder;
    Activity mActivity;
    @BindView(R.id.pb_loading_forum)
    ProgressBar mPbLoadingForum;
    @BindView(R.id.srl_forum)
    SwipeRefreshLayout mSrlForum;
    private boolean mRefreshing = false;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_forum;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    public static ForumFragment newInstance() {
        return new ForumFragment();
    }

    @Override
    protected void initFragment() {

        mActivity = this.getActivity();
        mTvTitleToolbar.setText("论坛区");
        mAdapter = new ForumAdapter(mContext, this.getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        mRvForumList.setLayoutManager(manager);
        mRvForumList.setAdapter(mAdapter);
        mSrlForum.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        mSrlForum.setOnRefreshListener(() -> {
            mPresenter.getForumList();
            mRefreshing = true;
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void showForumList(List<ForumModel> forumModel) {
        if (forumModel != null && forumModel.size() != 0) {
            mAdapter.addList(forumModel);
            mAdapter.notifyDataSetChanged();
        }
        hideProgressBar();
        setRefreshing(false);
    }

    @Override
    public void failedToGetForum(String msg) {
        SnackBarUtil.error(this.getActivity(), msg);
        hideProgressBar();
        setRefreshing(false);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (mPresenter != null){
            mPresenter.getForumList();

        }
    }

    void setRefreshing(boolean b){
        if (mSrlForum != null){
            mSrlForum.setRefreshing(b);
        }
        mRefreshing = b;
    }
    private void hideProgressBar() {
        if (mPbLoadingForum != null){
            mPbLoadingForum.setVisibility(View.GONE);
        }
    }

}
