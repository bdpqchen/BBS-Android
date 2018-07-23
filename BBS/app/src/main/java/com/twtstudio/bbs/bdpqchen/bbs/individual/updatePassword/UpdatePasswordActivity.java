package com.twtstudio.bbs.bdpqchen.bbs.individual.updatePassword;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.jaeger.library.StatusBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.view.ProgressButton;

import butterknife.BindView;
import butterknife.OnClick;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.USERNAME;


/**
 * Created by bdpqchen on 17-5-6.
 */

public class UpdatePasswordActivity extends BaseActivity implements UpdatePasswordContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_old)
    EditText mEtOld;
    @BindView(R.id.et_new)
    EditText mEtNew;
    @BindView(R.id.et_again)
    EditText mEtAgain;
    @BindView(R.id.cpb_identify)
    ProgressButton mCpbIdentify;

    private UpdatePasswordPresenter mPresenter;
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
    protected UpdatePasswordPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new UpdatePasswordPresenter(this);
        StatusBarUtil.setColor(this, Color.WHITE,0);
        enableLightStatusBarMode(true);
    }

    void checkInput() {
        String newPass = String.valueOf(mEtNew.getText());
        String confirmPass = String.valueOf(mEtAgain.getText());
        String oldPass = String.valueOf(mEtOld.getText());
        String err = "输入不正确";
        if (newPass.length() < 1){
            mEtNew.setError(err);
            return;
        }
        if (confirmPass.length() < 1){
            mEtAgain.setError(err);
            return;
        }
        if (oldPass.length() < 1){
            mEtOld.setError(err);
            return;
        }
        if (!newPass.equals(confirmPass)){
            mEtAgain.setError("两次不一致");
            return;
        }
        mCpbIdentify.start();
        mPresenter.doUpdatePass(newPass, oldPass);
    }

    @OnClick(R.id.cpb_identify)
    public void onViewClicked() {
        checkInput();
    }

    @Override
    public void onUpdated(BaseModel model) {
        mCpbIdentify.done();
        SnackBarUtil.normal(this, "修改成功，将要进行重新登录");
        HandlerUtil.postDelay(() -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(USERNAME, PrefUtil.getAuthUsername());
            startActivity(intent);
            finishMe();
        }, 2000);
    }

    @Override
    public void onUpdateFailed(String errorMsg) {
        mCpbIdentify.error();
        SnackBarUtil.error(this, errorMsg);
    }
}
