package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_TITLE;


/**
 * Created by bdpqchen on 17-5-20.
 */

public class ThreadListActivity extends BaseActivity<ThreadListPresenter> implements ThreadListContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_thread_list)
    RecyclerView mRecyclerView;

    private String mThreadTitle = "";
    private int mBoardId = 0;
    ThreadListAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    private Context mContext;
    private int mPage = 0;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_thread_list;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle(mThreadTitle);
        return mToolbar;
    }

    @Override
    protected boolean isShowBackArrow() {
        return true;
    }

    @Override
    protected boolean isSupportNightMode() {
        return true;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected Activity supportSlideBack() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mBoardId = intent.getIntExtra(INTENT_BOARD_ID, 0);
        mThreadTitle = intent.getStringExtra(INTENT_BOARD_TITLE);
        LogUtil.dd(mThreadTitle);
        super.onCreate(savedInstanceState);
        mToolbar.setTitle(mThreadTitle);
        mContext = this;
        mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
        mPresenter.getThreadList(mBoardId, mPage);
        mAdapter = new ThreadListAdapter(this);
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(16));
//        mAdapter.setOnItemClickListener(((view, id, title) -> {
//            Intent in = new Intent(mContext, ThreadActivity.class);
//            in.putExtra(Constants.INTENT_THREAD_ID, id);
//            in.putExtra(Constants.INTENT_THREAD_TITLE, title);
//            startActivity(in);
//        }));



    }

    @Override
    public void setThreadList(ThreadListModel threadListModel) {
        mAdapter.addList(threadListModel.getThread());

    }

    @Override
    public void showErrorMessage(String msg) {
        SnackBarUtil.error(this, msg);
    }
}
