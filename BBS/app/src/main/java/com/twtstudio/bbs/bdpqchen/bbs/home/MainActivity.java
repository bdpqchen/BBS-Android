package com.twtstudio.bbs.bdpqchen.bbs.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.oubowu.slideback.SlideBackHelper;
import com.twtstudio.bbs.bdpqchen.bbs.App;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.component.ActivityComponent;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.test.SecondActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_to_second)
    Button btnToSecond;
    @BindView(R.id.btn_snack_normal)
    Button btnSnackNormal;
    @BindView(R.id.btn_snack_yellow)
    Button btnSnackYellow;
    @BindView(R.id.btn_snack_error)
    Button btnSnackError;
    @BindView(R.id.btn_snack_yellow_action)
    Button btnSnackYellowAction;

    private static final String TEXT_SNACK_BAR = "提示提示提示换行jjjjjjjjjj";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected Toolbar getToolbarView() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("main");
        return mToolbar;
    }

    @Override
    protected boolean isShowBackArrow() {
        return false;
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
        btnToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
//        btnToSecond.setOnClickListener();
        mPresenter.checkUpdate(1);


    }

    @OnClick({R.id.btn_snack_normal, R.id.btn_snack_yellow, R.id.btn_snack_error, R.id.btn_snack_yellow_action})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_snack_normal:
                SnackBarUtil.normal(this, TEXT_SNACK_BAR);
                break;
            case R.id.btn_snack_yellow:
                SnackBarUtil.notice(this, TEXT_SNACK_BAR);
                break;
            case R.id.btn_snack_error:
                SnackBarUtil.error(this, TEXT_SNACK_BAR);
                break;
            case R.id.btn_snack_yellow_action:
                SnackBarUtil.normal(this, TEXT_SNACK_BAR);
                break;
        }
    }

    @Override
    public void showUpdateDialog(int versionCode) {

    }
}
