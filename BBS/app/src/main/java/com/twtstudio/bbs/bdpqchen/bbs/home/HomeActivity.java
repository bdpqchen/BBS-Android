package com.twtstudio.bbs.bdpqchen.bbs.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.jaeger.library.StatusBarUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.VersionUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum2.ForumFragment2;
import com.twtstudio.bbs.bdpqchen.bbs.main.mainV3.MainFragmentV3;
import com.twtstudio.bbs.bdpqchen.bbs.individual2.Individual2Fragment;


import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;


public class HomeActivity extends BaseActivity implements InfoContract {

    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;
    BottomBarTab mNearBy;
    @BindView(R.id.fl_main_container)
    FrameLayout mFlMainContainer;
    private SupportFragment[] mFragments = new SupportFragment[3];
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    //    private static final int THIRD = 2;
    private static final int FORTH = 2;
    private int mShowingFragment = FIRST;
    private int mHidingFragment = FIRST;
    private boolean mIsExit = false;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected Toolbar getToolbarView() {
        return null;
    }

    @Override
    protected HomePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        HandlerUtil.postDelay(() -> mSlideBackLayout.lock(true));
//        mPresenter = new HomePresenter(this);
//        LogUtil.dd("current_token", PrefUtil.getAuthToken());

        if (VersionUtil.eaLollipop()) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.BLACK);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        if (savedInstanceState == null) {
            mFragments[FIRST] = MainFragmentV3.Companion.newInstance();
            mFragments[SECOND] = ForumFragment2.Companion.newInstance();
            mFragments[FORTH] = Individual2Fragment.Companion.newInstance();
//          mFragments[FORTH] = MessageFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_main_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
//                    mFragments[THIRD],
                    mFragments[FORTH]);
        } else {
            mFragments[FIRST] = findFragment(MainFragmentV3.class);
            mFragments[SECOND] = findFragment(ForumFragment2.class);
//            mFragments[THIRD] = findFragment(MessageFragment.class);
//            mFragments[FORTH] = findFragment(IndividualFragment.class);
            mFragments[FORTH] = findFragment(Individual2Fragment.class);
        }
        mNearBy = mBottomBar.getTabWithId(R.id.bottom_bar_tab_individual);
        mBottomBar.setOnTabSelectListener(i -> {
            if (PrefUtil.hadLogin()) {
                if (i == R.id.bottom_bar_tab_main) {
                    mShowingFragment = FIRST;
                    clearFullScreen();
                } else if (i == R.id.bottom_bar_tab_forum) {
                    mShowingFragment = SECOND;
                    clearFullScreen();
                } else if (i == R.id.bottom_bar_tab_individual) {
                    mShowingFragment = FORTH;
//                    StatusBarUtil.setTransparent(this);
//                    clearFullScreen();
                    StatusBarUtil.setTranslucentForImageView(this, 0, null);
//                } else if (i == R.id.bottom_bar_tab_message){
//                    mShowingFragment = THIRD;
                }
                loadFragment();
            } else if (i == R.id.bottom_bar_tab_individual && !PrefUtil.hadLogin()) {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        if (PrefUtil.getLastImageStamp() == 0) {
            PrefUtil.setLastImageStamp(System.currentTimeMillis());
        }
        //Clear cached images and set {@LastImageStamp()} to currentTime.
        if (System.currentTimeMillis() - PrefUtil.getLastImageStamp() > 60 * 60 * 24 * 7) {
            PrefUtil.setLastImageStamp(System.currentTimeMillis());
            ImageUtil.clearCachedData(mContext);
        }
        pkTracker();
    }

    private void pkTracker() {
        getTrackerHelper().screen("").title("首页").with(getTracker());
    }

    private void clearFullScreen() {
        StatusBarUtil.setColor(this, ResourceUtil.getColor(this, R.color.colorPrimaryDark)
                , 0);
    }

    private void loadFragment() {
        showHideFragment(mFragments[mShowingFragment], mFragments[mHidingFragment]);
        mHidingFragment = mShowingFragment;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                finishMe();
            } else {
                SnackBarUtil.notice(this, "再按一次退出");
                mIsExit = true;
                new Handler().postDelayed(() -> mIsExit = false, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showUnreadMsg(int count) {
        mNearBy.setBadgeCount(count);
    }


}
