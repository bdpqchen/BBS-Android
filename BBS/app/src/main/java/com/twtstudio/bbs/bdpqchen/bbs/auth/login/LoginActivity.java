package com.twtstudio.bbs.bdpqchen.bbs.auth.login;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;
import com.twtstudio.bbs.bdpqchen.bbs.home.HomeActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.replaceUser.ReplaceUserActivity;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bdpqchen on 17-5-2.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.tx_forgot_password)
    TextView mTxForgotPassword;
    @BindView(R.id.tv_goto_register)
    TextView mTvGotoRegister;
    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_goto_replace_user)
    TextView mTvGotoReplaceUser;
    @BindView(R.id.tv_no_account_user)
    TextView mTvNoAccountUser;
    @BindView(R.id.cp_btn_login)
    CircularProgressButton mCircularProgressButton;
    @BindView(R.id.iv_login_banner)
    ImageView mIvBanner;
    @BindView(R.id.view_need_offset)
    LinearLayout mNeedOffset;


    private static final String LOGIN_ERROR_TEXT = "这个没有输入如何登录？";


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected Toolbar getToolbarView() {
        return null;
    }

    @Override
    protected boolean isShowBackArrow() {
        return false;
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
        return null;
    }

    @Override
    public void loginResults() {
       /* switch (results){
            case 0:{

            }
        }*/
//        mLoadingBtnLogin.loadingFailed();
//        mLoadingBtnLogin.loadingSuccessful();
//        SnackBarUtil.error(this, "登录失败，请检查用户名与密码");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //键盘挡住输入框
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageView(this, 38, mNeedOffset);


    }

    @OnClick({R.id.tx_forgot_password, R.id.tv_goto_register,
            R.id.et_account, R.id.et_password, R.id.tv_goto_replace_user, R.id.tv_no_account_user, R.id.cp_btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tx_forgot_password:
                mCircularProgressButton.revertAnimation();
                break;
            case R.id.tv_goto_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.et_account:
                break;
            case R.id.et_password:
                break;
            case R.id.tv_goto_replace_user:
                startActivity(new Intent(this, ReplaceUserActivity.class));
                break;
            case R.id.tv_no_account_user:

                loginSuccess();
                mCircularProgressButton.doneLoadingAnimation(R.color.colorPrimaryDark, ResourceUtil.getBitmapFromResource(this, R.drawable.ic_done_white_48dp));
//                mCircularProgressButton.stopAnimation();
                break;
            case R.id.cp_btn_login:
                String username = mEtAccount.getText() + "";
                String password = mEtPassword.getText() + "";
                if (username.length() == 0){
                    mEtAccount.setError(LOGIN_ERROR_TEXT);
                }else if (password.length() == 0){
                    mEtAccount.setError(LOGIN_ERROR_TEXT);
                }else{
                    mCircularProgressButton.startAnimation();
                    mPresenter.doLogin(username, password);
                }

                break;

        }

    }

    public void loginSuccess() {
//        mCircularProgressButton.doneLoadingAnimation(R.color.material_amber_accent_400, ResourceUtil.getBitmapFromResource(this, R.drawable.ic_done_white_48dp));
//        PrefUtil.setHadLogin(true);
        mCircularProgressButton.stopAnimation();
        ActivityOptions activityOptions = null;
        Intent intent = new Intent(this, HomeActivity.class);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, new Pair<>(findViewById(R.id.cp_btn_login), "transition"));
        }

        if (activityOptions != null) {
            startActivity(intent, activityOptions.toBundle());
//            LogUtil.d("start activity with options");
        } else {
            startActivity(intent);
        }
    }
}

