package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.appeal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PK_APPEAL;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PK_CATEGORY_AJAX;

/**
 * Created by bdpqchen on 17-5-21.
 */

public class AppealPassportActivity extends BaseActivity<AppealPassportPresenter> implements AppealPassportContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.et_stu_num)
    EditText mEtStuNum;
    @BindView(R.id.et_real_name)
    EditText mEtRealName;
    @BindView(R.id.et_cid)
    EditText mEtCid;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.et_comment)
    EditText mEtComment;
    @BindView(R.id.cp_btn_submit_appeal)
    CircularProgressButton mCpBtnSubmitAppeal;

    private String mUsername;
    private String mStuNum;
    private String mCid;
    private String mEmail;
    private String mRealName;
    private String mComment;
    private String mCaptchaId;
    private String mCaptchaValue;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_appeal_password;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("账号申诉");
        return mToolbar;
    }
    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUsername = getIntent().getStringExtra(Constants.BUNDLE_REGISTER_USERNAME);

    }

    @OnClick(R.id.cp_btn_submit_appeal)
    public void onViewClicked() {
        checkInput();
    }

    private void checkInput() {
        mEmail = mEtEmail.getText().toString();
        mUsername = mEtAccount.getText().toString();
        mCid = mEtCid.getText().toString();
        mComment = mEtComment.getText().toString();
        mStuNum = mEtStuNum.getText().toString();
        mRealName = mEtRealName.getText().toString();
        String err = "输入不正确";
        if (mEmail.length() < 3) {
            mEtEmail.setError(err);
            return;
        }
        if (mUsername.length() < 4) {
            mEtAccount.setError("输入不正确");
            return;
        }
        if (mRealName.length() < 1) {
            mEtRealName.setError(err);
            return;
        }
        if (mCid.length() < 4) {
            mEtCid.setError(err);
            return;
        }
        if (mStuNum.length() < 4) {
            mEtStuNum.setError(err);
            return;
        }
        if (mComment.length() < 4) {
            mEtComment.setError(err);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_REGISTER_CID, mCid);
        bundle.putString(Constants.BUNDLE_REGISTER_REAL_NAME, mRealName);
        bundle.putString(Constants.BUNDLE_REGISTER_STU_NUM, mStuNum);
        bundle.putString(Constants.BUNDLE_EMAIL, mEmail);
        bundle.putString(Constants.BUNDLE_MESSAGE, mComment);
        bundle.putString(Constants.CAPTCHA_ID, mCaptchaId);
        bundle.putString(Constants.CAPTCHA_VALUE, mCaptchaValue);
        mPresenter.appealPassport(bundle);
        mCpBtnSubmitAppeal.startAnimation();
    }

    @Override
    public void sendFailed(String s) {
        SnackBarUtil.error(this, s, true);
        mCpBtnSubmitAppeal.revertAnimation();
    }

    @Override
    public void sendSuccess() {
        mCpBtnSubmitAppeal.revertAnimation();
        SnackBarUtil.normal(this, "提交成功，我们会尽快处理，结果会以邮件的形式通知", true);
        HandlerUtil.postDelay(() -> {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finishMe();
                }, 3000
        );
        pkTracker();
    }

    private void pkTracker(){
        getTrackerHelper().screen(PK_APPEAL).title(mToolbar.getTitle().toString()).with(getTracker());
        getTrackerHelper().event(PK_CATEGORY_AJAX, "appeal").name("POST").with(getTracker());
    }

}
