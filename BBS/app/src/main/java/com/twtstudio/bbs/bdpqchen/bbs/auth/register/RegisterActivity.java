package com.twtstudio.bbs.bdpqchen.bbs.auth.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PK_CATEGORY_SIGN;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PK_SIGN_UP;

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
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.cp_btn_register)
    CircularProgressButton mCpBtnRegister;
    @BindView(R.id.et_password_again)
    EditText mEtPasswordAgain;

    private String mRealName;
    private String mStuNum;
    private String mCid;
    private String mUsername;
    private String mPassword;
    private String mPasswordAgain;

    private Context mContext;
    private Activity mActivity;

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
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        pkTracker();
    }

    private void pkTracker(){
        getTrackerHelper().screen(PK_SIGN_UP).title("注册").with(getTracker());
        getTrackerHelper().event(PK_CATEGORY_SIGN, "Register").with(getTracker());
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
            bundle.putString(Constants.BUNDLE_REGISTER_REAL_NAME, mRealName);
            bundle.putString(Constants.BUNDLE_REGISTER_STU_NUM, mStuNum);
            bundle.putString(Constants.BUNDLE_REGISTER_PASSWORD, mPassword);
            bundle.putString(Constants.BUNDLE_REGISTER_USERNAME, mUsername);
            mPresenter.doRegister(bundle);
        }
    }

    @Override
    public void registerSuccess() {
        SnackBarUtil.normal(this, "注册成功，请登录");
        HandlerUtil.postDelay(() -> {
            finishMe();
            startActivity(new Intent(this, LoginActivity.class));
        }, 2000);

    }

    @Override
    public void registerFailed(String errorMessage) {
        SnackBarUtil.error(this, errorMessage);
    }

    private boolean checkNotNull() {
        boolean isPerfect = true;
        String errorPwd = "密码不太安全";
        String error = "输入不正确";
        mCid = mEtCid.getText().toString();
        mRealName = mEtRealName.getText().toString();
        mStuNum =  mEtStuNum.getText().toString();
        mPassword = mEtPassword.getText().toString();
        mPasswordAgain = mEtPasswordAgain.getText().toString();
        mUsername = mEtUsername.getText().toString();
        if (mRealName.length() == 0) {
            mEtStuNum.setError(error);
            isPerfect = false;
        }
        if (mStuNum.length() < 2) {
            mEtStuNum.setError(error);
            isPerfect = false;
        }
        if (mCid.length() < 1) {
            mEtCid.setError(error);
            isPerfect = false;
        }
        if (!mPassword.equals(mPasswordAgain)){
            isPerfect = false;
            String err = "两次密码输入不一致";
            mEtPasswordAgain.setError(err);
            mEtPassword.setError(err);
        }
        if (mPassword.length() < 4){
            mEtPassword.setError(errorPwd);
            isPerfect = false;
        }
        if (mPasswordAgain.length() < 4){
            isPerfect = false;
            mEtPasswordAgain.setError(errorPwd);
        }
        return isPerfect;
    }

}
