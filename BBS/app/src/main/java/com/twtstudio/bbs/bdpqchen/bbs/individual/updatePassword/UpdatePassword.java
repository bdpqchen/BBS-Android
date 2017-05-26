package com.twtstudio.bbs.bdpqchen.bbs.individual.updatePassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by bdpqchen on 17-5-6.
 */

public class UpdatePassword extends BaseActivity<UpdatePasswordPresenter> implements UpdatePasswordContract.View {


    UpdatePasswordPresenter updatePasswordPresenter = new UpdatePasswordPresenter(this);
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.updatepassword_oldpassword)
    EditText updatepassword_oldpassword;
    @BindView(R.id.updatepassword_newpassword)
    EditText updatepassword_newpassword;
    @BindView(R.id.updatepassword_confirmpassword)
    EditText updatepassword_confirmpassword;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_update_password;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("修改密码");
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
        mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());

    }

    @OnClick(R.id.cp_btn_identify)
    void onClick() {
        String oldPass = String.valueOf(updatepassword_oldpassword.getText());
        String newPass = String.valueOf(updatepassword_newpassword.getText());
        if (checkInput()) {
            updatePasswordPresenter.sendUpdatePasswordInfo(oldPass, newPass);
        }
    }

    @Override
    public void updatePasswordSuccess() {
        SnackBarUtil.normal(this, "修改成功~");
        startLoginInOneSec();
    }

    @Override
    public void updatePasswordError(String errorMsg) {
        SnackBarUtil.error(this, errorMsg);
    }

    @Override
    public boolean checkInput() {
        String newPass = String.valueOf(updatepassword_newpassword.getText());
        String confirmPass = String.valueOf(updatepassword_confirmpassword.getText());

        if (updatepassword_oldpassword.getText().length() == 0 ||
                updatepassword_newpassword.getText().length() == 0 ||
                updatepassword_confirmpassword.getText().length() == 0) {
            SnackBarUtil.error(this, "三项均不可为空哦~");
            return false;
        } else if (!Objects.equals(newPass, confirmPass)) {
            SnackBarUtil.error(this, "密码不一致");
            return false;
        }
        return true;
    }

    private void startLoginInOneSec() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(UpdatePassword.this, LoginActivity.class);
                intent.putExtra("username", PrefUtil.getAuthUsername());
                UpdatePassword.this.startActivity(intent);
                finishMe();
            }
        };
        timer.schedule(timerTask, 1000);
    }

}
