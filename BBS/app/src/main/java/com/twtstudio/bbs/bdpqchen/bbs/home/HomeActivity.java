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
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.manager.ActivityManager;
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
    IndividualFragment mIndividualFragment = null;
    BottomBarTab mNearBy;

    private int mShowingFragment = Constants.FRAGMENT_MAIN;
    private int mHidingFragment = Constants.FRAGMENT_MAIN;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
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
        if (!PrefUtil.isNoAccountUser()) {
            PrefUtil.setHadLogin(true);
        }
        mMask.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(findViewById(R.id.mask_home),
                "alpha", 0f).setDuration(600);
        animator.setStartDelay(400);
        animator.start();
//        }

        mMainFragment = MainFragment.newInstance();
        mForumFragment = ForumFragment.newInstance();
        mIndividualFragment = IndividualFragment.newInstance();
        loadMultipleRootFragment(R.id.fl_main_container, 0, mMainFragment, mForumFragment, mIndividualFragment);
        mNearBy = mBottomBar.getTabWithId(R.id.bottom_bar_tab_individual);

        mBottomBar.setOnTabSelectListener(i -> {
            LogUtil.dd("onTabSelected()");
            if (PrefUtil.hadLogin()) {
                if (i == R.id.bottom_bar_tab_main) {
                    mShowingFragment = Constants.FRAGMENT_MAIN;
                } else if (i == R.id.bottom_bar_tab_forum) {
                    mShowingFragment = Constants.FRAGMENT_FORUM;
                } else if (i == R.id.bottom_bar_tab_individual) {
                    mShowingFragment = Constants.FRAGMENT_INDIVIDUAL;
                }
                loadFragment();
            } else if (i == R.id.bottom_bar_tab_individual && !PrefUtil.hadLogin()) {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

//        if (mIndividualFragment.isVisible()){
        // TODO: 17-5-22 解决夜间模式View的空指针问题
        if (PrefUtil.hadLogin()) {
            mPresenter.initIndividualInfo();
        }
        LogUtil.dd("send a net request");
//        }
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
        LogUtil.d("receive a response");
        if (info != null) {
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
            LogUtil.dd(String.valueOf(unRead));
            // TODO: 17-5-10 为了测试
//            mNearBy.setBadgeCount(unRead);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int count = PrefUtil.getInfoUnread();
        if (count > 0) {
//            mNearBy.setBadgeCount(count);
        }

    }


}
