package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.find_username;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-21.
 */

public class FindUsernameActivity extends BaseActivity<FindUsernamePresenter> {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_find_username;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("找回用户名");
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

    }
}
