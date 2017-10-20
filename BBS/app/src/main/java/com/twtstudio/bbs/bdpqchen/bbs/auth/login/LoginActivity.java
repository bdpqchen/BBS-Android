package com.twtstudio.bbs.bdpqchen.bbs.auth.login;

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
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.RandomUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.VersionUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.view.ProgressButton;
import com.twtstudio.bbs.bdpqchen.bbs.home.HomeActivity;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PK_CATEGORY_SIGN;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PK_LOGIN;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.REQUEST_CODE_RETRIEVE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.USERNAME;

/**
 * Created by bdpqchen on 17-5-2.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.tx_forgot_password)
    TextView mTxForgotPassword;
    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_goto_replace_user)
    TextView mTvGotoReplaceUser;
    @BindView(R.id.tv_to_register)
    TextView mTvGotoRegister;
    @BindView(R.id.iv_login_banner)
    ImageView mIvBanner;
    @BindView(R.id.view_need_offset)
    LinearLayout mNeedOffset;
    @BindView(R.id.civ_avatar)
    CircleImageView mCivAvatar;
    @BindView(R.id.p_btn_login)
    ProgressButton mPBtnLogin;

    private static final String LOGIN_ERROR_TEXT = "哈哈?搞笑..";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected Toolbar getToolbarView() {
        return null;
    }

    @Override
    protected BasePresenter<BaseView> inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //键盘挡住输入框
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageView(this, 0, mNeedOffset);
        //自动填写用户名
        Intent intent = getIntent();
        String usernameToSet = intent.getStringExtra(USERNAME);
        if (usernameToSet == null || usernameToSet.length() == 0) {
            usernameToSet = PrefUtil.getAuthUsername();
        }
        if (usernameToSet != null && usernameToSet.length() > 0) {
            mEtPassword.requestFocus();
        }
        mEtAccount.setText(usernameToSet);
        if (PrefUtil.getAuthUid() == 0) {
            ImageUtil.loadDrawable(this, R.drawable.avatar_default_left, mCivAvatar);
        } else {
            ImageUtil.loadAvatarAsBitmapByUid(this, PrefUtil.getAuthUid(), mCivAvatar);
        }
        ImageUtil.loadLoginCover(this, RandomUtil.getForumIdRandom(), mIvBanner);
        pkTracker();
    }

    @OnClick({R.id.tx_forgot_password, R.id.tv_to_register, R.id.p_btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tx_forgot_password:
                startActivityForResult(IntentUtil.toRetrieve(mContext), REQUEST_CODE_RETRIEVE);
                break;
            case R.id.tv_to_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.p_btn_login:
                String username = mEtAccount.getText().toString();
                String password = mEtPassword.getText().toString();
                if (username.length() == 0) {
                    mEtAccount.setError(LOGIN_ERROR_TEXT);
                } else if (password.length() == 0) {
                    mEtPassword.setError(LOGIN_ERROR_TEXT);
                } else {
                    mPBtnLogin.start();
                    PrefUtil.setAuthUsername(username);
                    mPresenter.doLogin(username, password);
                }
                break;
        }
    }

    private void pkTracker() {
        getTrackerHelper().screen(PK_LOGIN).title("登录").with(getTracker());
        getTrackerHelper().event(PK_CATEGORY_SIGN, "Login").with(getTracker());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_RETRIEVE && resultCode == RESULT_OK) {
            if (data != null) {
                mEtAccount.setText(data.getStringExtra(USERNAME));
            }
        }
    }

    @Override
    public void loginSuccess(LoginModel loginModel) {
        PrefUtil.setFirstOpen(false);
        mPBtnLogin.done();

        PrefUtil.setAuthToken(loginModel.getToken());
        PrefUtil.setAuthGroup(loginModel.getGroup());
        PrefUtil.setAuthUid(loginModel.getUid());
        ActivityOptions activityOptions = null;
        Intent intent = new Intent(this, HomeActivity.class);
        PrefUtil.setIsNoAccountUser(false);
        if (VersionUtil.eaLollipop()) {
            activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, new Pair<>(findViewById(R.id.p_btn_login), "transition"));
        }
        if (activityOptions != null) {
            ActivityOptions finalActivityOptions = activityOptions;
            HandlerUtil.postDelay(() -> {
                startActivity(intent, finalActivityOptions.toBundle());
            }, 400);
        } else {
            HandlerUtil.postDelay(() -> {
                startActivity(intent);
            }, 400);
        }
        HandlerUtil.postDelay(this::finishMe, 2000);
    }

    @Override
    public void loginFailed(String msg) {
        mPBtnLogin.error();
//        SnackBarUtil.error(this, msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}

