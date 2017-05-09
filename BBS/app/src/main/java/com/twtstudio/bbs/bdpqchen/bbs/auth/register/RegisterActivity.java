package com.twtstudio.bbs.bdpqchen.bbs.auth.register;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bdpqchen on 17-5-2.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_real_name)
    EditText mEtRealName;
    @BindView(R.id.et_stu_num)
    EditText mEtStuNum;
    @BindView(R.id.et_cid)
    EditText mEtCid;
    @BindView(R.id.et_username)
    TextInputLayout mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.cp_btn_register)
    CircularProgressButton mCpBtnRegister;
    @BindView(R.id.et_password_again)
    EditText mEtPasswordAgain;

    private String mRealname;
    private String mStuNum;
    private String mCid;
    private String mUsername;
    private String mPassword;
    private String mPasswordAgain;
/*
    public static final String BUNDLE_REGISTER_CID = "cid";
    public static final String BUNDLE_REGISTER_REAL_NAME = "real_name";
    public static final String BUNDLE_REGISTER_STU_NUM = "stu_num";
    public static final String BUNDLE_REGISTER_PASSWORD = "password";
    public static final String BUNDLE_REGISTER_USERNAME = "username";*/

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
        mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
        // TODO: add setContentView(...) invocation

    }

    @Override
    public void registerResults() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
    }


    @OnClick({R.id.et_password, R.id.cp_btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_password:
                break;
            case R.id.cp_btn_register:
                doRegister();
                break;
        }
    }

    private void doRegister() {
        if (checkNotNull()) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_REGISTER_CID, mCid);
            bundle.putString(Constants.BUNDLE_REGISTER_REAL_NAME, mRealname);
            bundle.putString(Constants.BUNDLE_REGISTER_STU_NUM, mStuNum);
            bundle.putString(Constants.BUNDLE_REGISTER_PASSWORD, mPassword);
            bundle.putString(Constants.BUNDLE_REGISTER_USERNAME, mUsername);
            mPresenter.doRegister(bundle);
        }
    }

    private boolean checkNotNull() {
        boolean isPerfect = true;
        String errorPwd = "密码不太安全";
        String error = "输入不正确";
        mCid = mEtCid.getText().toString();
        mRealname = mEtRealName.getText().toString();
        mStuNum =  mEtStuNum.getText().toString();
        mPassword = mEtPassword.getText().toString();
        mPasswordAgain = mEtPasswordAgain.getText().toString();
        mUsername = mEtUsername.toString();
        if (mRealname.length() == 0) {
            mEtStuNum.setError(error);
            isPerfect = false;
        }
        if (mStuNum.length() != 10) {
            mEtStuNum.setError(error);
            isPerfect = false;
        }
        if (mCid.length() != 18) {
            mEtCid.setError(error);
            isPerfect = false;
        }
        if (!mPassword.equals(mPasswordAgain)){
            isPerfect = false;
            String err = "两次密码输入不一致";
            mEtPasswordAgain.setError(err);
            mEtPassword.setError(err);
        }
        if (mPassword.length() < 6){
            mEtPassword.setError(errorPwd);
            isPerfect = false;
        }
        if (mPassword.length() < 6){
            isPerfect = false;
            mEtPasswordAgain.setError(errorPwd);
        }
        return isPerfect;
    }

}
