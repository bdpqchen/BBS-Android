package com.twtstudio.bbs.bdpqchen.bbs.forum.boards;

import android.content.Context;
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
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_FORUM_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_FORUM_TITLE;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class BoardsActivity extends BaseActivity implements BoardsContract.View {

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
    private BoardsPresenter mPresenter;

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
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mForumId = getIntent().getIntExtra(INTENT_FORUM_ID, 0);
        mForumTitle = getIntent().getStringExtra(INTENT_FORUM_TITLE);
        super.onCreate(savedInstanceState);
        mContext = this;
        mPresenter = new BoardsPresenter(this);
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
            startActivity(IntentUtil.toCreateThread(mContext, mForumId, mForumTitle));
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
        mSrlBoardList.setRefreshing(b);
    }

    private void hideProgressBar() {
        mRefreshing = false;
        mProgressBar.setVisibility(View.GONE);
    }
}
