package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity.INTENT_BOARD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity.INTENT_BOARD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity.INTENT_THREAD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity.INTENT_THREAD_TITLE;

/**
 * Created by bdpqchen on 17-5-12.
 */

public class ThreadActivity extends BaseActivity<ThreadPresenter> implements ThreadContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private String mThreadTitle = "";
    private int mThreadId = 0;
    private Context mContext;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_thread;
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
        mThreadId = intent.getIntExtra(INTENT_THREAD_ID, 0);
        mThreadTitle = intent.getStringExtra(INTENT_THREAD_TITLE);
        super.onCreate(savedInstanceState);
        mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
        mContext = this;





    }

}
