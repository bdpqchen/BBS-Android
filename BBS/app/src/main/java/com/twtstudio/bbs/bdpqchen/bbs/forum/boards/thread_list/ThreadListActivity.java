package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_CAN_ANON;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PK_THREAD_LIST_OF_ONE_BOARD;


/**
 * Created by bdpqchen on 17-5-20.
 */

public class ThreadListActivity extends BaseActivity<ThreadListPresenter> implements ThreadListContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_thread_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_thread_list)
    SwipeRefreshLayout mSrlThreadList;
    @BindView(R.id.fb_thread_list_create)
    FloatingActionButton mFbThreadListCreate;

    private String mBoardTitle = "";
    private int mBoardId = 0;
    ThreadListAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    private Context mContext;
    private int mPage = 0;
    private boolean mIsLoadingMore = false;
    private int lastVisibleItemPosition = 0;
    private boolean mRefreshing = false;
    private int mCanAnon = 0;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_thread_list;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle(mBoardTitle);
        return mToolbar;
    }

    @Override
    protected boolean isShowBackArrow() {
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
        mBoardTitle = intent.getStringExtra(INTENT_BOARD_TITLE);
        mCanAnon = intent.getIntExtra(INTENT_BOARD_CAN_ANON, 0);
//        LogUtil.dd(mBoardTitle);
        super.onCreate(savedInstanceState);
        mContext = this;
        mPresenter.getThreadList(mBoardId, mPage);
        mAdapter = new ThreadListAdapter(this);
        mAdapter.setShowFooter(true);
        mAdapter.setBoardTitle(mBoardTitle);
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(10));
        mSrlThreadList.setRefreshing(true);
        mSrlThreadList.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        mSrlThreadList.setOnRefreshListener(() -> {
            mRefreshing = true;
            mPresenter.getThreadList(mBoardId, 0);
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    mPage++;
                    mPresenter.getThreadList(mBoardId, mPage);
                    mIsLoadingMore = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        mFbThreadListCreate.setOnClickListener(v -> {
            startActivity(IntentUtil.toCreateThread(mContext, mBoardId, mBoardTitle, mCanAnon));
        });
    }

    @Override
    public void setThreadList(ThreadListModel threadListModel) {
        mSrlThreadList.setRefreshing(false);
        if (threadListModel == null || threadListModel.getThread() == null && threadListModel.getBoard() == null) {
            return;
        }
        mCanAnon = threadListModel.getBoard().getAnonymous();
        if (mIsLoadingMore) {
            mAdapter.addDataList(threadListModel.getThread());
        } else {
            if (mRefreshing) {
                mAdapter.refreshList(threadListModel.getThread());
                mRefreshing = false;
            } else {
                mAdapter.addList(threadListModel.getThread());
            }
        }
        pkTracker();
    }

    private void pkTracker(){
        getTrackerHelper().screen(PK_THREAD_LIST_OF_ONE_BOARD + mBoardId + "/all/page/1/");
//        getTrackerHelper().event(PK_CATEGORY_AJAX, "")
    }

    @Override
    public void showErrorMessage(String msg) {
        mSrlThreadList.setRefreshing(false);
        mRefreshing = false;
        SnackBarUtil.error(this, msg);
        if (mIsLoadingMore) {
            mIsLoadingMore = false;
            mPage--;
        }
    }

}
