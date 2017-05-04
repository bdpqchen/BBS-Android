package com.twtstudio.bbs.bdpqchen.bbs.replaceUser.replacePassword;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-5.
 */

public class ReplacePasswordActivity extends BaseActivity<ReplacePasswordPresenter> implements ReplacePasswordContract.View{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_replace_password;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("重置密码");
        return mToolbar;
    }

    @Override
    protected boolean isShowBackArrow() {
        return true;
    }

    @Override
    protected boolean isSupportNightMode() {
        return false;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
