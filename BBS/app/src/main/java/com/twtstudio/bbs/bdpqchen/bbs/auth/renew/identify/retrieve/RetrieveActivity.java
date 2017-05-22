package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.retrieve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.appeal.AppealPassportActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.retrieve.reset_password.ResetPasswordActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bdpqchen on 17-5-21.
 */

public class RetrieveActivity extends BaseActivity<RetrievePresenter> implements RetrieveContract.View {

    public static final String BUNDLE_REAL_NAME = "real_name";
    public static final String BUNDLE_CID = "cid";
    public static final String BUNDLE_USERNAME = "username";
    public static final String BUNDLE_STU_NUM = "stu_num";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_retrieve_stu_num)
    EditText mEtRetrieveStuNum;
    @BindView(R.id.et_retrieve_username)
    EditText mEtRetrieveUsername;
    @BindView(R.id.et_retrieve_real_name)
    EditText mEtRetrieveRealName;
    @BindView(R.id.et_retrieve_cid)
    EditText mEtRetrieveCid;
    @BindView(R.id.cp_btn_retrieve)
    CircularProgressButton mCpBtnRetrieve;

    private String mUsername = "";
    private String mCid = "";
    private String mRealName = "";
    private String mStuNum = "";


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.cp_btn_retrieve)
    public void onViewClicked() {
        inputCheck();
    }

    private void inputCheck() {
        mRealName = mEtRetrieveRealName.getText().toString();
        mCid = mEtRetrieveCid.getText().toString();
        mStuNum = mEtRetrieveStuNum.getText().toString();
        mUsername = mEtRetrieveUsername.getText().toString();

        String err = "输入错误";
        if (mRealName.length() < 4) {
            mEtRetrieveRealName.setError(err);
            return;
        }
        if (mUsername.length() == 0 && mStuNum.length() == 0) {
            String e = "学号和用户名不能同时为空";
            mEtRetrieveUsername.setError(e);
            mEtRetrieveStuNum.setError(e);
            return;
        }
        if (mCid.length() < 4) {
            mEtRetrieveCid.setError(err);
            return;
        }
        mCpBtnRetrieve.startAnimation();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_USERNAME, mUsername);
        bundle.putString(BUNDLE_STU_NUM, mStuNum);
        bundle.putString(BUNDLE_CID, mCid);
        bundle.putString(BUNDLE_REAL_NAME, mRealName);
        mPresenter.doRetrieveUsername(bundle);

    }

    private void gotoResetPassword(){
        Intent intent = new Intent(this, AppealPassportActivity.class);
        intent.putExtra(Constants.BUNDLE_REGISTER_USERNAME, mUsername);
        startActivity(intent);
    }

    @Override
    public void retrieveFailed(String msg) {
        SnackBarUtil.error(this, msg, "点击申诉", v -> gotoResetPassword());
        mCpBtnRetrieve.revertAnimation();
    }

    @Override
    public void retrieveSuccess(RetrieveModel model) {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        intent.putExtra(Constants.BUNDLE_TOKEN, model.getToken());
        intent.putExtra(Constants.BUNDLE_UID, model.getUid());
        startActivity(intent);
        mCpBtnRetrieve.stopAnimation();
        finish();

    }
}
