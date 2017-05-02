package com.twtstudio.bbs.bdpqchen.bbs.replaceUser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.BaseAdapter;

import com.oubowu.slideback.SlideBackHelper;
import com.twtstudio.bbs.bdpqchen.bbs.App;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;

/**
 * Created by bdpqchen on 17-5-2.
 */

public class ReplaceUserActivity extends BaseActivity<ReplaceUserPresenter> implements ReplaceUserContract.View {
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
    protected void supportSwipeBack() {

        SlideBackHelper.attach(this, App.getActivityHelper(), getSlideConfig(), null);
    }

    @Override
    public void replaceUserResults() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
