package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import android.app.Activity;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.main.content.post.PostPresenter;

/**
 * Created by bdpqchen on 17-5-29.
 */

public class PostActivity extends BaseActivity<PostPresenter> {
    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

    @Override
    protected Toolbar getToolbarView() {
        return null;
    }

    @Override
    protected boolean isShowBackArrow() {
        return false;
    }

    @Override
    protected boolean isSupportNightMode() {
        return false;
    }

    @Override
    protected void inject() {

    }

    @Override
    protected Activity supportSlideBack() {
        return null;
    }
}
