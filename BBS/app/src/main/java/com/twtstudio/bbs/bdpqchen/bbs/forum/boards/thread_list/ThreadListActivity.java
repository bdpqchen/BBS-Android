package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bdpqchen on 17-5-20.
 */

public class ThreadListActivity extends BaseActivity<ThreadListPresenter> implements ThreadListContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private String mThreadTitle = "";
    private int mThreadId = 0;

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
        mThreadId = intent.getIntExtra(BoardsActivity.INTENT_BOARD_ID, 0);
        mThreadTitle = intent.getStringExtra(BoardsActivity.INTENT_BOARD_TITLE);
//        LogUtil.dd(mThreadTitle);
        super.onCreate(savedInstanceState);
        mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());



    }
}
