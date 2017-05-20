package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-12.
 */

public class ThreadActivity extends BaseActivity<ThreadPresenter> implements ThreadContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_thread_list)
    RecyclerView mRvThreadList;

    private String mThreadTitle = "";
    private int mThreadId = 0;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_thread;
    }

    @Override
    protected Toolbar getToolbarView() {
        // TODO: 17-5-12 获取到的名称，要提前传过来
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

        super.onCreate(savedInstanceState);
        mSlideBackLayout.lock(PrefUtil.isSlideBackMode());


    }

}
