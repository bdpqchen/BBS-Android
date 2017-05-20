package com.twtstudio.bbs.bdpqchen.bbs.auth.register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.scope.ContextLife;
import com.twtstudio.bbs.bdpqchen.bbs.commons.manager.ActivityManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

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
        mContext = this;
        mActivity = this;

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
        HandlerUtil.postDelay(new Runnable() {
            @Override
            public void run() {
                ActivityManager.getActivityManager().finishActivity(mActivity);
            }
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

    public static String chineseToUnicode(String str){
        char[]arChar=str.toCharArray();
        int iValue=0;
        StringBuffer uStr = new StringBuffer();
        for(int i=0;i<arChar.length;i++){
            iValue=(int)str.charAt(i);
            uStr.append("&#"+iValue+";");
        }
        return uStr.toString();
    }

    public static String unicodeToChinese(String str){
        String[] strings = str.split(";");
        StringBuffer aStr = new StringBuffer();
        for(int i=0;i<strings.length;i++){
            String s = strings[i].replace("&#", "");
            aStr.append((char)Integer.parseInt(s));
        }
        return aStr.toString();
    }


}
