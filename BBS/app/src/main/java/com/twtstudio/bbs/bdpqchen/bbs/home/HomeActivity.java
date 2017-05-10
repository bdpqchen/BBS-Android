package com.twtstudio.bbs.bdpqchen.bbs.home;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumFragment;
import com.twtstudio.bbs.bdpqchen.bbs.individual.IndividualFragment;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainFragment;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;


public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {


    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;
    @BindView(R.id.mask_home)
    View mMask;

    MainFragment mMainFragment;
    ForumFragment mForumFragment;
    IndividualFragment mIndividualFragment;
    BottomBarTab mNearBy;



    private int mShowingFragment = Constants.FRAGMENT_MAIN;
    private int mHidingFragment = Constants.FRAGMENT_MAIN;
    public static final int CODE_RESULT_FOR_UPDATE_INFO = 1;
    public static final String CODE_RESULT_FOR_UPDATE_INFO_TAG = "hasUnSyncInfo";
    private static final String TEXT_SNACK_BAR = "提示提示提示换行jjjjjjjjjj";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected Toolbar getToolbarView() {
//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.setTitle("main");
//        return mToolbar;
        return null;
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
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.dd("token", PrefUtil.getAuthToken());

        // TODO: 17-5-3 非登录后跳转到这里，是否渐变
        // 登录后的渐变,
        if (!PrefUtil.hadLogin()) {
            mMask.setVisibility(View.VISIBLE);
            PrefUtil.setHadLogin(true);
            ObjectAnimator animator = ObjectAnimator.ofFloat(findViewById(R.id.mask_home),
                    "alpha", 0f).setDuration(600);
            animator.setStartDelay(400);
            animator.start();
        }

        mMainFragment = new MainFragment();
        mForumFragment = new ForumFragment();
        mIndividualFragment = new IndividualFragment();
        loadMultipleRootFragment(R.id.fl_main_container, 0, mMainFragment, mForumFragment, mIndividualFragment);
        mNearBy = mBottomBar.getTabWithId(R.id.bottom_bar_tab_individual);

        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int i) {
                LogUtil.d("onTabSelected()");
                if (i == R.id.bottom_bar_tab_main) {

                    mShowingFragment = Constants.FRAGMENT_MAIN;
                } else if (i == R.id.bottom_bar_tab_forum) {
                    mShowingFragment = Constants.FRAGMENT_FORUM;
                } else if (i == R.id.bottom_bar_tab_individual) {
                    LogUtil.d("selected the individual ");
                    mShowingFragment = Constants.FRAGMENT_INDIVIDUAL;
                }
                loadFragment();
            }
        });

        mPresenter.initIndividualInfo();
        mPresenter.checkUpdate(1);


    }

    private void loadFragment() {
        showHideFragment(getTargetFragment(mShowingFragment), getTargetFragment(mHidingFragment));
        mHidingFragment = mShowingFragment;
    }

    private SupportFragment getTargetFragment(int type) {
        switch (type) {
            case Constants.FRAGMENT_MAIN:
                return mMainFragment;
            case Constants.FRAGMENT_FORUM:
                return mForumFragment;
            case Constants.FRAGMENT_INDIVIDUAL:
                return mIndividualFragment;
        }
        return mMainFragment;
    }

    @Override
    public void showUpdateDialog(int versionCode) {

    }

    @Override
    public void showIndividualInfo(IndividualInfoModel info) {


        //设置个人信息，在IndividualFragment 里可直接获取，需判断是否为最新getIsLatestInfo()
        PrefUtil.setInfoNickname(info.getNickname());
        PrefUtil.setInfoSignature(info.getSignature());
        PrefUtil.setInfoOnline(info.getC_online());
        PrefUtil.setInfoPost(info.getC_post());
        PrefUtil.setInfoPoints(info.getPoints());
        PrefUtil.setInfoCreate(info.getT_create());
        PrefUtil.setInfoGroup(info.getGroup());
        PrefUtil.setInfoLevel(info.getLevel());
        PrefUtil.setIsLatestInfo(true);
        int unRead = info.getC_unread();
        PrefUtil.setInfoUnread(unRead);
        LogUtil.d(unRead);
        // TODO: 17-5-10 为了测试
        mNearBy.setBadgeCount(unRead + 1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        int count = PrefUtil.getInfoUnread();
        if (count > 0) {
            mNearBy.setBadgeCount(count);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            LogUtil.dd("received the result", String.valueOf(data.getBooleanExtra(CODE_RESULT_FOR_UPDATE_INFO_TAG, false)));
            if (requestCode == CODE_RESULT_FOR_UPDATE_INFO) {
                if (data.getBooleanExtra(CODE_RESULT_FOR_UPDATE_INFO_TAG, false)) {
                    mIndividualFragment.doUpdate();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




}
