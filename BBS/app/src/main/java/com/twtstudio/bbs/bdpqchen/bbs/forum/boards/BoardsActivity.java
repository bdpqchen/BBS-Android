package com.twtstudio.bbs.bdpqchen.bbs.forum.boards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumFragment;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class BoardsActivity extends BaseActivity<BoardsPresenter> implements BoardsContract.View {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
//    @BindView(R.id.progress_bar)
//    ProgressBar mProgressBar;

    int mForumId;
    Context mContext;
    Activity mActivity;
    BoardsAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    @BindView(R.id.rv_board_list)
    RecyclerView mRvBoardList;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_board;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("休闲娱乐");
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
        super.onCreate(savedInstanceState);
        mContext = this;
        mForumId = getIntent().getIntExtra(ForumFragment.INTENT_FORUM_ID, 0);

        mPresenter.getBoardList(mForumId);
        mAdapter = new BoardsAdapter(mContext);

        getDataList();
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvBoardList.setLayoutManager(mLayoutManager);
        mRvBoardList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, position) -> {
            startActivity(new Intent(mContext, ThreadActivity.class));
        });


    }

    public void getDataList() {

        List<ThreadModel> list = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            ThreadModel model = new ThreadModel();
            ThreadModel.BoardBean boardBean = new ThreadModel.BoardBean();
            boardBean.setId(i);
            model.setBoard(boardBean);
            list.add(model);
        }

        mAdapter.addList(list);

    }
}
