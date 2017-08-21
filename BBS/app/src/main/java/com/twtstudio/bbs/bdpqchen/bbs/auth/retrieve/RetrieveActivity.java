package com.twtstudio.bbs.bdpqchen.bbs.auth.retrieve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.appeal.AppealPassportActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.CastUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.CID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PASSWORD;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.REAL_NAME;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.STUNUM;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.TOKEN;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.UID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.USERNAME;

/**
 * Created by bdpqchen on 17-5-21.
 */

public class RetrieveActivity extends BaseActivity<RetrievePresenter> implements RetrieveContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.cp_btn_retrieve)
    CircularProgressButton mCpBtnRetrieve;
    @BindView(R.id.et_stu_or_username)
    EditText mEtStuOrUsername;
    @BindView(R.id.et_real_name)
    EditText mEtRealName;
    @BindView(R.id.et_cid)
    EditText mEtCid;
    @BindView(R.id.et_new_password)
    EditText mEtNewPassword;

    private String mNewPassword = "";
    private String mCid = "";
    private String mRealName = "";
    private String mStuNum = "";
    private String mUsername = "";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_retrieve;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("找回用户名");
        return mToolbar;
    }

    @Override
    protected boolean isShowBackArrow() {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.cp_btn_retrieve)
    public void onViewClicked() {
        getInputtedData();
        if (isInputtedNotNull()) {
            mCpBtnRetrieve.startAnimation();
            mPresenter.doRetrievePassword(getRetrieveData());
        }
    }

    private Bundle getRetrieveData() {
        Bundle bundle = new Bundle();
        bundle.putString(USERNAME, mUsername);
        bundle.putString(STUNUM, mStuNum);
        bundle.putString(CID, mCid);
        bundle.putString(REAL_NAME, mRealName);
        return bundle;
    }

    private boolean isInputtedNotNull() {
        if (mRealName.length() == 0
                || mCid.length() == 0
                || mNewPassword.length() < 6
                || (mUsername.length() == 0 && mStuNum.length() == 0)) {
            return false;
        }
        return true;
    }

    private void getInputtedData() {
        mRealName = mEtRealName.getText().toString();
        mCid = mEtCid.getText().toString();
        mNewPassword = mEtNewPassword.getText().toString();
        String stuOrUser = mEtStuOrUsername.getText().toString();
        if (isStuNum(stuOrUser)) {
            mStuNum = stuOrUser;
            mUsername = "";
        } else {
            mUsername = stuOrUser;
            mStuNum = "";
        }
    }

    private boolean isStuNum(String stuOrUser) {
        return CastUtil.isNumeric(stuOrUser);
    }

    private void gotoResetPassword() {
        Intent intent = new Intent(this, AppealPassportActivity.class);
        intent.putExtra(Constants.BUNDLE_REGISTER_USERNAME, mUsername);
        startActivity(intent);
    }

    @Override
    public void retrieveFailed(String msg) {
        requestFailed(msg);
    }

    private void requestFailed(String msg) {
        SnackBarUtil.error(this, msg, "点击申诉", v -> gotoResetPassword());
        mCpBtnRetrieve.revertAnimation();
    }

    @Override
    public void retrieveSuccess(RetrieveModel model) {
        Bundle bundle = new Bundle();
        bundle.putString(UID, model.getUid());
        bundle.putString(TOKEN, model.getToken());
        bundle.putString(PASSWORD, mNewPassword);
        mPresenter.resetPassword(bundle);
        // 用于返回至登录页
        mUsername = model.getUsername();
    }

    @Override
    public void resetFailed(String msg) {
        requestFailed(msg);
    }

    @Override
    public void resetSuccess(BaseModel model) {
        mCpBtnRetrieve.revertAnimation();
        Intent intentData = new Intent();
        intentData.putExtra(USERNAME, mUsername);
        setResult(Constants.RESULT_CODE_AVATAR, intentData);
        SnackBarUtil.normal(mActivity, "重置成功,使用新密码登录");
        HandlerUtil.postDelay(this::finish, 2000);
    }

}
