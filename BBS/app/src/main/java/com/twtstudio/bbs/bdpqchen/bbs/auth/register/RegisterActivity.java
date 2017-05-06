package com.twtstudio.bbs.bdpqchen.bbs.auth.register;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-2.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("新用户注册");
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
        if (!PrefUtil.isSlideBackMode()) return null;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @Override
    public void registerResults() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSlideBackLayout != null){
            mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
        }

    }


}
