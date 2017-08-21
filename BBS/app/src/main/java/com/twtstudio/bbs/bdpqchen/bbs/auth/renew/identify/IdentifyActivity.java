package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.old.RegisterOldActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.appeal.AppealPassportActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bdpqchen on 17-5-21.
 */

public class IdentifyActivity extends BaseActivity<IdentifyPresenter> implements IdentifyContract.View {

    private static final String INTENT_ENTERING_USERNAME = "intent_entering_username";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.old_account)
    EditText mOldAccount;
    @BindView(R.id.old_password)
    EditText mOldPassword;
    @BindView(R.id.cp_btn_identify)
    CircularProgressButton mCpBtnIdentify;
    @BindView(R.id.tv_find_username)
    TextView mTvFindUsername;

    public static final String INTENT_USERNAME = "intent_username";
    public static final String INTENT_TOKEN = "intent_token";

    private String mUsername = "";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_identify;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("老用户验证");
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
        mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
    }

    @OnClick({R.id.old_account, R.id.old_password, R.id.cp_btn_identify, R.id.tv_find_username})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.old_account:
                break;
            case R.id.old_password:
                break;
            case R.id.cp_btn_identify:
                inputCheck();
                break;
            case R.id.tv_find_username:
                startActivity(new Intent(this, AppealPassportActivity.class));
                break;
        }
    }

    private void inputCheck() {
        String err = "长度不符合规范";
        mUsername = String.valueOf(mOldAccount.getText());
        String password = String.valueOf(mOldPassword.getText());
        if (mUsername.length() < 1) {
            mOldAccount.setError(err);
            return;
        }
        if (password.length() < 3) {
            mOldPassword.setError(err);
            return;
        }

        mCpBtnIdentify.startAnimation();
        mPresenter.doIdentify(mUsername, password);
    }

    @Override
    public void identifyFailed(String m) {
        mCpBtnIdentify.doneLoadingAnimation(R.color.material_red_700, ResourceUtil.getBitmapFromResource(this, R.drawable.ic_clear_white_24dp));
        HandlerUtil.postDelay(() -> mCpBtnIdentify.revertAnimation(), 3000);
        SnackBarUtil.error(this, m, "账户申诉", v -> {
            Intent intent = new Intent(this, AppealPassportActivity.class);
            intent.putExtra(INTENT_ENTERING_USERNAME, mOldAccount.getText());
            startActivity(intent);
        });

    }

    @Override
    public void identifySuccess(IdentifyModel model) {
        mCpBtnIdentify.doneLoadingAnimation(R.color.material_red_700, ResourceUtil.getBitmapFromResource(this, R.drawable.ic_done_white_48dp));
        Intent intent = new Intent(this, RegisterOldActivity.class);
        intent.putExtra(INTENT_USERNAME, mUsername);
        intent.putExtra(INTENT_TOKEN, model.token);
        startActivity(intent);
        finishMe();
        // TODO: 17-5-21 认证成功后
    }


}
