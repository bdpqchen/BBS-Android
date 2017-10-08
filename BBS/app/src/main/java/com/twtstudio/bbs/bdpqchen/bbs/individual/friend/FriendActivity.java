package com.twtstudio.bbs.bdpqchen.bbs.individual.friend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-6-29.
 */

public class FriendActivity extends BaseActivity<FriendPresenter> implements FriendContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_friend)
    RecyclerView mRvFriend;
    @BindView(R.id.srl_friend)
    SwipeRefreshLayout mSrlFriend;

    private FriendAdapter mAdapter;
    private boolean mRefreshing = false;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_friend;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("我的好友");
        return mToolbar;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FriendAdapter(this);
        mRvFriend.setAdapter(mAdapter);
        mRvFriend.addItemDecoration(new RecyclerViewItemDecoration(1));
        mRvFriend.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mSrlFriend.setOnRefreshListener(() -> {
            mRefreshing = true;
            mPresenter.getFriendList();
            setRefresh();
        });
        mRefreshing = true;
        setRefresh();
        mPresenter.getFriendList();
    }

    private void setRefresh() {
        mSrlFriend.setRefreshing(mRefreshing);
    }

    @Override
    public void onGetFriendList(List<FriendModel> list) {
        LogUtil.dd("onGetFriendList");
        if (list != null && list.size() > 0) {
            if (mRefreshing) {
                mRefreshing = false;
                setRefresh();
                mAdapter.refreshList(list);
            } else {
                mAdapter.addList(list);
            }
        }
    }

    @Override
    public void onGetFriendFailed(String s) {

    }


}
