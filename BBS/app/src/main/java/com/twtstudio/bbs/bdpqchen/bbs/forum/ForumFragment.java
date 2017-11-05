package com.twtstudio.bbs.bdpqchen.bbs.forum;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_BOARD;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_FORUM;

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
//        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvForumList.setLayoutManager(new FlexboxLayoutManager(mContext, FlexDirection.ROW));
        mRvForumList.setAdapter(mAdapter);
//        mSrlForum.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
//        mSrlForum.setOnRefreshListener(() -> {
//            getForumList();
//            mRefreshing = true;
//        });
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
        mAdapter.addOne(new FlexBoardModel(forumBoard.getFid(), forumBoard.getForumName(), ITEM_FORUM, 0));
        List<FlexBoardModel> flexBoardModelList = new ArrayList<>();
        int size = forumBoard.getBoardList().size();
        for (int i = 0; i < size; i++) {
            ForumBoardModel.BoardModel board = forumBoard.getBoardList().get(i);
            flexBoardModelList.add(new FlexBoardModel(board.getBid(), board.getBoardName(), ITEM_BOARD, board.getCanAnon()));
        }
        mAdapter.addList(flexBoardModelList);
        hideProgressBar();
    }

    @Override
    public void getForumBoardFailed(String msg) {
        SnackBarUtil.error(this.getActivity(), msg);
        hideProgressBar();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (mPresenter != null) {
            getForumList();
        }
    }

    private void hideProgressBar() {
        mPbLoadingForum.setVisibility(View.GONE);
    }

}
