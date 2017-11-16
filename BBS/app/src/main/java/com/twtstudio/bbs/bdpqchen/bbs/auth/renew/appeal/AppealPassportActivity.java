package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.appeal;

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
import com.twtstudio.bbs.bdpqchen.bbs.commons.view.ProgressButton;

import butterknife.BindView;
import butterknife.OnClick;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PK_APPEAL;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PK_CATEGORY_AJAX;

/**
 * Created by bdpqchen on 17-5-21.
 */

public class AppealPassportActivity extends BaseActivity implements AppealPassportContract.View {

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
    ProgressButton mCpBtnSubmitAppeal;

    private String mUsername;
    private String mCaptchaId;
    private String mCaptchaValue;
    private AppealPassportPresenter mPresenter;

    public AppealPassportActivity() {
    }

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
    protected AppealPassportPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AppealPassportPresenter(this);
        mUsername = getIntent().getStringExtra(Constants.BUNDLE_REGISTER_USERNAME);

    }

    @OnClick(R.id.cp_btn_submit_appeal)
    public void onViewClicked() {
        checkInput();
    }

    private void checkInput() {
        String email = mEtEmail.getText().toString();
        mUsername = mEtAccount.getText().toString();
        String cid = mEtCid.getText().toString();
        String comment = mEtComment.getText().toString();
        String stuNum = mEtStuNum.getText().toString();
        String realName = mEtRealName.getText().toString();
        String err = "输入不正确";
        if (email.length() < 3) {
            mEtEmail.setError(err);
            return;
        }
        if (mUsername.length() < 4) {
            mEtAccount.setError("输入不正确");
            return;
        }
        if (realName.length() < 1) {
            mEtRealName.setError(err);
            return;
        }
        if (cid.length() < 4) {
            mEtCid.setError(err);
            return;
        }
        if (stuNum.length() < 4) {
            mEtStuNum.setError(err);
            return;
        }
        if (comment.length() < 4) {
            mEtComment.setError(err);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_REGISTER_CID, cid);
        bundle.putString(Constants.BUNDLE_REGISTER_REAL_NAME, realName);
        bundle.putString(Constants.BUNDLE_REGISTER_STU_NUM, stuNum);
        bundle.putString(Constants.BUNDLE_EMAIL, email);
        bundle.putString(Constants.BUNDLE_MESSAGE, comment);
        bundle.putString(Constants.CAPTCHA_ID, mCaptchaId);
        bundle.putString(Constants.CAPTCHA_VALUE, mCaptchaValue);
        mCpBtnSubmitAppeal.start();
        mPresenter.appealPassport(bundle);
    }

    @Override
    public void sendFailed(String s) {
        SnackBarUtil.error(this, s, true);
        mCpBtnSubmitAppeal.error();
    }

    @Override
    public void sendSuccess() {
        mCpBtnSubmitAppeal.done();
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
