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
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.create_thread.CreateThreadActivity;

import java.util.ArrayList;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_IDS;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_NAMES;
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
    private ArrayList<String> mBoardNames = new ArrayList<>();
    private ArrayList<Integer> mBoardIds = new ArrayList<>();
    private ArrayList<Integer> mAnonymous = new ArrayList<>();

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
        mAdapter = new BoardsAdapter(mContext);
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
            intent.putStringArrayListExtra(INTENT_BOARD_NAMES, getBoardNames());
            intent.putIntegerArrayListExtra(INTENT_BOARD_IDS, getBoardIds());
//            intent.putIntegerArrayListExtra(INTENT_BOARD_IS_ANONY, getBoardIds());

            startActivity(intent);
        });
        mBoardIds.add(0);
        mBoardNames.add(".....");
        mPresenter.getBoardList(mForumId);

    }

    @Override
    public void setBoardList(PreviewThreadModel previewThreadList) {
        if (mRefreshing) {
            mAdapter.clearAll();
            mRefreshing = false;
        }
        if (previewThreadList != null) {
            mAdapter.addData(previewThreadList);
            mBoardNames.add(previewThreadList.getBoard().getName());
            mBoardIds.add(previewThreadList.getBoard().getId());
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

    public ArrayList<String> getBoardNames() {
        return mBoardNames;
    }

    public void setBoardNames(ArrayList<String> boardNames) {
        mBoardNames = boardNames;
    }

    public ArrayList<Integer> getBoardIds() {
        return mBoardIds;
    }

    public void setBoardIds(ArrayList<Integer> boardIds) {
        mBoardIds = boardIds;
    }
}
