package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.retrieve;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bdpqchen on 17-5-21.
 */

public class RetrieveActivity extends BaseActivity<RetrievePresenter> implements RetrieveContract.View {

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
    private String mRealname = "";
    private String mStuNum = "";


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_find_username;
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

    private void inputCheck(){
        mRealname = mEtRetrieveRealName.getText().toString();
        mCid = mEtRetrieveCid.getText().toString();
        mStuNum = mEtRetrieveStuNum.getText().toString();
        mUsername = mEtRetrieveRealName.getText().toString();

        String err = "输入错误";
        if (mRealname.length() < 4){
            mEtRetrieveStuNum.setError(err);
            return;
        }
        if (mUsername.length() == 0 && mStuNum.length() == 0){
            String e = "学号和用户名不能同时为空";
            mEtRetrieveUsername.setError(e);
            mEtRetrieveStuNum.setError(e);
            return;
        }
        if (mCid.length() < 14){
            mEtRetrieveCid.setError(err);
            return;
        }
        mCpBtnRetrieve.startAnimation();

        // TODO: 17-5-21 添加认证网络请求

    }


}
