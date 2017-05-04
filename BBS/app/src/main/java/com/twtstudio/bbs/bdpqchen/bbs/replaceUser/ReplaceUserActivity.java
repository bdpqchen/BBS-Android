package com.twtstudio.bbs.bdpqchen.bbs.replaceUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.replaceUser.replacePassword.ReplacePasswordActivity;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bdpqchen on 17-5-2.
 */

public class ReplaceUserActivity extends BaseActivity<ReplaceUserPresenter> implements ReplaceUserContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.oldAccount)
    EditText mOldAccount;
    @BindView(R.id.oldName)
    EditText mOldName;
    @BindView(R.id.oldPIN)
    EditText mOldPIN;
    @BindView(R.id.cp_btn_identify)
    CircularProgressButton mCpBtnIdentify;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_replace_user;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("老用户认证");
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
    public void replaceUserResults() {
        startActivity(new Intent(this, ReplacePasswordActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick({R.id.oldAccount, R.id.oldName, R.id.oldPIN, R.id.cp_btn_identify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.oldAccount:
                break;
            case R.id.oldName:
                break;
            case R.id.oldPIN:
                break;
            case R.id.cp_btn_identify:
                mCpBtnIdentify.startAnimation();
                replaceUserResults();
                break;
        }
    }
}
