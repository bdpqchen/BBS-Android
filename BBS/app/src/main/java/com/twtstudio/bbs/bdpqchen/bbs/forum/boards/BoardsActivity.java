package com.twtstudio.bbs.bdpqchen.bbs.forum.boards;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumFragment;

import java.util.List;

import butterknife.BindView;

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

    public static final String  INTENT_BOARD_TITLE = "intent_board_title";
    public static final String  INTENT_BOARD_ID = "intent_board_id";

    int mForumId;
    Context mContext;
    Activity mActivity;
    BoardsAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    private String mForumTitle = "";

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
        mContext = this;
        mForumId = getIntent().getIntExtra(ForumFragment.INTENT_FORUM_ID, 0);
        mForumTitle = getIntent().getStringExtra(ForumFragment.INTENT_FORUM_TITLE);
        super.onCreate(savedInstanceState);

        mPresenter.getBoardList(mForumId);
        mAdapter = new BoardsAdapter(mContext);

        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvBoardList.addItemDecoration(new RecyclerViewItemDecoration(16));
        mRvBoardList.setLayoutManager(mLayoutManager);
        mRvBoardList.setAdapter(mAdapter);

    }

    @Override
    public void setBoardList(List<PreviewThreadModel> previewThreadList) {
//        LogUtil.dd("pre thread list size", String.valueOf(previewThreadList.size()));
        mAdapter.addList(previewThreadList);
        hideProgressBar();
    }

    @Override
    public void failedToGetBoardList(String msg) {
        SnackBarUtil.error(this, msg, true);
        hideProgressBar();
    }


    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

}
