package com.twtstudio.bbs.bdpqchen.bbs.forum;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-3.
 */

public class ForumFragment extends BaseFragment implements ForumContract.View {

    @BindView(R.id.tv_title_toolbar)
    TextView mTvTitleToolbar;
    @BindView(R.id.rv_forum_list)
    RecyclerView mRvForumList;
    ForumAdapter mAdapter;
    Activity mActivity;
    @BindView(R.id.pb_loading_forum)
    ProgressBar mPbLoadingForum;
    @BindView(R.id.srl_forum)
    SwipeRefreshLayout mSrlForum;
    private boolean mRefreshing = false;
    private ForumPresenter mPresenter;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_forum;
    }

    public static ForumFragment newInstance() {
        return new ForumFragment();
    }

    @Override
    protected void initFragment() {
        mActivity = this.getActivity();
        mTvTitleToolbar.setText("论坛区");
        mPresenter = new ForumPresenter(this);
        mAdapter = new ForumAdapter(mContext);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvForumList.setHasFixedSize(true);
        mRvForumList.setLayoutManager(manager);
        mRvForumList.setAdapter(mAdapter);
        mSrlForum.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        mSrlForum.setOnRefreshListener(() -> {
            getForumList();
            mRefreshing = true;
        });
    }

    private void getForumList() {
        mPresenter.getForumBoardList();
    }

    @Override
    protected ForumPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onGotForumBoard(ForumBoardModel forumBoard) {
        LogUtil.dd("board size is", forumBoard.getBoardList().size());
        if (mRefreshing) {
            mRefreshing = false;
            mAdapter.clearAll();
        }
        mAdapter.addOne(forumBoard);
        hideProgressBar();
        setRefreshing(false);

    }

    @Override
    public void getForumBoardFailed(String msg) {
        SnackBarUtil.error(this.getActivity(), msg);
        hideProgressBar();
        setRefreshing(false);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (mPresenter != null) {
            getForumList();
        }
    }

    void setRefreshing(boolean b) {
        mSrlForum.setRefreshing(b);
        mRefreshing = b;
    }

    private void hideProgressBar() {
        mPbLoadingForum.setVisibility(View.GONE);
    }

}
