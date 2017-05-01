package com.twtstudio.bbs.bdpqchen.bbs.auth;

import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;

/**
 * Created by bdpqchen on 17-5-2.
 */

public class LoginActivity<LoginPresenter> extends BaseActivity {
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
    protected void inject() {

    }
}
