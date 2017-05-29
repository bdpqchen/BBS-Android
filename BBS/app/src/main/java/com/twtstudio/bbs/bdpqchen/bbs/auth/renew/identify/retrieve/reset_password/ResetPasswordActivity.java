package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.retrieve.reset_password;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bdpqchen on 17-5-21.
 */

public class ResetPasswordActivity extends BaseActivity<ResetPasswordPresenter> implements ResetPasswordContract.View {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_new_password)
    EditText mEtNewPassword;
    @BindView(R.id.cp_btn_reset_password)
    CircularProgressButton mCpBtnResetPassword;

    private String mPassword = "";
    private String mUid = "";
    private String mToken = "";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_reset_password;
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
    public void resetFailed(String m) {
        SnackBarUtil.error(this, m, true);
    }

    @Override
    public void resetSuccess(BaseModel response) {
        SnackBarUtil.normal(this, "已重置密码，欢迎老用户归来");
        HandlerUtil.postDelay(() -> startActivity(new Intent(this, LoginActivity.class)), 3000);
        finishMe();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mToken = intent.getStringExtra(Constants.BUNDLE_TOKEN);
        mUid = intent.getStringExtra(Constants.BUNDLE_UID);

    }

    @OnClick(R.id.cp_btn_reset_password)
    public void onViewClicked() {
        checkInput();
    }

    private void checkInput() {
        mPassword = mCpBtnResetPassword.getText().toString();
        if (mPassword.length() < 5){
            mEtNewPassword.setError("密码输入不正确");
            return;
        }
        mCpBtnResetPassword.startAnimation();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_UID, mUid);
        bundle.putString(Constants.BUNDLE_TOKEN, mToken);
        bundle.putString(Constants.PASSWORD, mPassword);
        mPresenter.resetPassword(bundle);

    }
}
