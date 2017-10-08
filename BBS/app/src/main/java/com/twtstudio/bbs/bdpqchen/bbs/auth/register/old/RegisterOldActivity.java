package com.twtstudio.bbs.bdpqchen.bbs.auth.register.old;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.IdentifyActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.manager.ActivityManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

import static com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.IdentifyActivity.INTENT_TOKEN;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PK_CATEGORY_SIGN;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PK_OLD_REGISTER;

/**
 * Created by bdpqchen on 17-5-2.
 */

public class RegisterOldActivity extends BaseActivity<RegisterOldPresenter> implements RegisterOldContract.View {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_real_name)
    EditText mEtRealName;
    @BindView(R.id.et_cid)
    EditText mEtCid;
    @BindView(R.id.et_password_again)
    EditText mEtPasswordAgain;
    @BindView(R.id.cp_btn_register)
    CircularProgressButton mCpBtnRegister;

    private String mRealName;
    private String mCid;
    private String mUsername = "";
    private String mPasswordAgain;

    private Context mContext;
    private Activity mActivity;

    private String mToken = "";
    public static final String INTENT_USERNAME = "intent_username";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register_old;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("个人信息认证");
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
        Intent intent = getIntent();
        mUsername = intent.getStringExtra(INTENT_USERNAME);
        mToken = intent.getStringExtra(INTENT_TOKEN);
        mEtUsername.setText(mUsername);
        pkTracker();
    }

    private void doRegister() {
        if (checkNotNull()) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_REGISTER_CID, mCid);
            bundle.putString(Constants.BUNDLE_REGISTER_REAL_NAME, mRealName);
            bundle.putString(Constants.BUNDLE_REGISTER_PASSWORD, mPasswordAgain);
            bundle.putString(Constants.BUNDLE_REGISTER_USERNAME, mUsername);
            bundle.putString(Constants.TOKEN, mToken);
            mPresenter.doRegisterOld(bundle);
        }
    }

    @Override
    public void registerSuccess() {
        SnackBarUtil.normal(this, "认证成功，请登录");
        HandlerUtil.postDelay(() -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(INTENT_USERNAME, mUsername);
            startActivity(intent);
            finishMe();
            ActivityManager.getActivityManager().finishNamedActivity(IdentifyActivity.class);
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
        mPasswordAgain = mEtPasswordAgain.getText().toString();
        mUsername = mEtUsername.getText().toString();
        if (mCid.length() < 10) {
            mEtCid.setError(error);
            isPerfect = false;
        }
        if (mRealName.length() < 1) {
            mEtRealName.setError(error);
            isPerfect = false;
        }
        if (mPasswordAgain.length() < 4) {
            mEtPasswordAgain.setError(errorPwd);
            isPerfect = false;
        }
        return isPerfect;
    }

    private void pkTracker(){
        getTrackerHelper().screen(PK_OLD_REGISTER).title("老用户认证").with(getTracker());
        getTrackerHelper().event(PK_CATEGORY_SIGN, "oldRegister").with(getTracker());
    }

    @OnClick(R.id.cp_btn_register)
    public void onViewClicked() {
        doRegister();
    }
}
