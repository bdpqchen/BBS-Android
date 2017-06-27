package com.twtstudio.bbs.bdpqchen.bbs.forum.boards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.create_thread.CreateThreadActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_FORUM_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_FORUM_TITLE;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class BoardsActivity extends BaseActivity<BoardsPresenter> implements BoardsContract.View {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_board_list)
    RecyclerView mRvBoardList;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.srl_board_list)
    SwipeRefreshLayout mSrlBoardList;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    int mForumId;
    boolean mRefreshing = false;
    Context mContext;
    BoardsAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    private String mForumTitle = "";
    private boolean isSimpleBoardList = PrefUtil.isSimpleBoardList();
    private List<PreviewThreadModel> mPreviewThreadModel = new ArrayList<>();
    private BoardsModel mBoardsModel = new BoardsModel();

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_board;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle(mForumTitle);
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
        mForumId = getIntent().getIntExtra(INTENT_FORUM_ID, 0);
        mForumTitle = getIntent().getStringExtra(INTENT_FORUM_TITLE);
        super.onCreate(savedInstanceState);
        mContext = this;
        if (isSimpleBoardList){
            mAdapter = new BoardsAdapter(mContext, mBoardsModel);
        }else{
            mAdapter = new BoardsAdapter(mContext, mPreviewThreadModel);
        }
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvBoardList.addItemDecoration(new RecyclerViewItemDecoration(16));
        mRvBoardList.setLayoutManager(mLayoutManager);
        mRvBoardList.setAdapter(mAdapter);

        mSrlBoardList.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        mSrlBoardList.setOnRefreshListener(() -> {
            mRefreshing = true;
            mPresenter.getBoardList(mForumId);
        });

        mFab.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateThreadActivity.class);
            intent.putExtra(INTENT_FORUM_ID, mForumId);
            intent.putExtra(INTENT_FORUM_TITLE, mForumTitle);
            startActivity(intent);
        });
        if (PrefUtil.isSimpleBoardList()){
            mPresenter.getSimpleBoardList(mForumId);
        }else{
            mPresenter.getBoardList(mForumId);
        }

    }

    @Override
    public void setBoardList(PreviewThreadModel previewThreadList) {
        if (mRefreshing) {
            mAdapter.clearAll();
            mRefreshing = false;
        }
        if (previewThreadList != null) {
            mAdapter.addData(previewThreadList);
        }
        hideProgressBar();
        setRefreshing(false);
    }

    @Override
    public void failedToGetBoardList(String msg) {
        SnackBarUtil.error(this, msg, true);
        hideProgressBar();
        setRefreshing(false);
    }

    @Override
    public void onFailedGetSimpleList(String m) {
        failedToGetBoardList(m);
    }

    @Override
    public void onGotSimpleList(BoardsModel model) {
        if (mRefreshing){
            mAdapter.clearAllSimple();
            mRefreshing = false;
        }
        if (model != null && model.getBoards().size() > 0){
            mAdapter.addSimpleData(model);
        }
        hideProgressBar();
        setRefreshing(false);
    }

    void setRefreshing(boolean b){
        if (mSrlBoardList != null){
            mSrlBoardList.setRefreshing(b);
        }
    }

    private void hideProgressBar() {
        mRefreshing = false;
        if (mProgressBar != null){
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
