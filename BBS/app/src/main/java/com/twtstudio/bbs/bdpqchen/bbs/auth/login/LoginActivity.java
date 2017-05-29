package com.twtstudio.bbs.bdpqchen.bbs.auth.login;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.replaceUser.ReplaceUserActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.manager.ActivityManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;
import com.twtstudio.bbs.bdpqchen.bbs.home.HomeActivity;
import com.twtstudio.bbs.bdpqchen.bbs.welcome.WelcomeActivity;

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

    @BindView(R.id.cl_container)
    CoordinatorLayout mClContainer;


    private static final String LOGIN_ERROR_TEXT = "哈哈？搞笑。";

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //键盘挡住输入框
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageView(this, 0, mNeedOffset);

        //自动填写用户名
        Intent intent = getIntent();
        String usernameToSet = intent.getStringExtra("username");
        mEtAccount.setText(usernameToSet);

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
                loginWithNoUsername();
                break;
            case R.id.cp_btn_login:
                String username = mEtAccount.getText() + "";
                String password = mEtPassword.getText() + "";
                if (username.length() == 0) {
                    mEtAccount.setError(LOGIN_ERROR_TEXT);
                } else if (password.length() == 0) {
                    mEtPassword.setError(LOGIN_ERROR_TEXT);
                } else {
                    mCircularProgressButton.startAnimation();
                    mPresenter.doLogin(username, password);
                    PrefUtil.setAuthUsername(username);
                }
                break;
        }
    }

    private void loginWithNoUsername() {
        // TODO: 17-5-9 游客登录逻辑
        PrefUtil.setIsNoAccountUser(true);
        PrefUtil.setHadLogin(false);
        startActivity(new Intent(this, HomeActivity.class));
        finishMe();
    }

    @Override
    public void loginSuccess(LoginModel loginModel) {
        PrefUtil.setFirstOpen(false);
        ActivityManager.getActivityManager().finishNamedActivity(WelcomeActivity.class);
        mCircularProgressButton.doneLoadingAnimation(R.color.material_green_600, ResourceUtil.getBitmapFromResource(this, R.drawable.ic_done_white_48dp));
        PrefUtil.setAuthToken(loginModel.getToken());
        PrefUtil.setAuthGroup(loginModel.getGroup());
        PrefUtil.setAuthUid(loginModel.getUid());
        ActivityOptions activityOptions = null;
        Intent intent = new Intent(this, HomeActivity.class);
        PrefUtil.setIsNoAccountUser(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, new Pair<>(findViewById(R.id.cp_btn_login), "transition"));
        }
        if (activityOptions != null) {
            ActivityOptions finalActivityOptions = activityOptions;
            HandlerUtil.postDelay(() -> {
                startActivity(intent, finalActivityOptions.toBundle());
            }, 200);
//            LogUtil.d("start activity with options");
        } else {
//            LogUtil.d("start activity with none");
            HandlerUtil.postDelay(() -> {
                startActivity(intent);
            }, 200);
        }
        HandlerUtil.postDelay(this::finishMe, 2000);
    }

    @Override
    public void loginFailed(String msg) {
        LogUtil.d("loginFailed()");
        mCircularProgressButton.doneLoadingAnimation(R.color.material_red_700, ResourceUtil.getBitmapFromResource(this, R.drawable.ic_clear_white_24dp));
        HandlerUtil.postDelay(() -> mCircularProgressButton.revertAnimation(), 3000);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    // TODO: 17-5-10 登录成功后跳转优化，主线程太多任务了

}

