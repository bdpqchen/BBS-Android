package com.twtstudio.bbs.bdpqchen.bbs.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.jaeger.library.StatusBarUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.manager.ActivityManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.AuthUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumFragment;
import com.twtstudio.bbs.bdpqchen.bbs.individual.IndividualFragment;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;


public class HomeActivity extends BaseActivity implements HomeContract.View {

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
    private HomePresenter mPresenter;
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
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HandlerUtil.postDelay(() -> mSlideBackLayout.lock(true));
        Context context = this;
        mPresenter = new HomePresenter(this);
        LogUtil.dd("current_token", PrefUtil.getAuthToken());
        PrefUtil.setHadLogin(true);
        if (savedInstanceState == null) {
            mFragments[FIRST] = com.twtstudio.bbs.bdpqchen.bbs.main.MainFragment.Companion.newInstance();
            mFragments[SECOND] = ForumFragment.newInstance();
            mFragments[FORTH] = IndividualFragment.newInstance();
//            mFragments[FORTH] = MessageFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_main_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
//                    mFragments[THIRD],
                    mFragments[FORTH]);
        } else {
            mFragments[FIRST] = findFragment(com.twtstudio.bbs.bdpqchen.bbs.main.MainFragment.class);
            mFragments[SECOND] = findFragment(ForumFragment.class);
//            mFragments[THIRD] = findFragment(MessageFragment.class);
            mFragments[FORTH] = findFragment(IndividualFragment.class);
        }

        mNearBy = mBottomBar.getTabWithId(R.id.bottom_bar_tab_individual);
        mBottomBar.setOnTabSelectListener(i -> {
//            LogUtil.dd("onTabSelected()", String.valueOf(i));
            if (PrefUtil.hadLogin()) {
                if (i == R.id.bottom_bar_tab_main) {
                    mShowingFragment = FIRST;
                    clearFullScreen();
                } else if (i == R.id.bottom_bar_tab_forum) {
                    mShowingFragment = SECOND;
                    clearFullScreen();
                } else if (i == R.id.bottom_bar_tab_individual) {
                    mShowingFragment = FORTH;
                    StatusBarUtil.setTranslucentForImageView(this, 0, null);

//                } else if (i == R.id.bottom_bar_tab_message){
//                    mShowingFragment = THIRD;
                }
                loadFragment();
            } else if (i == R.id.bottom_bar_tab_individual && !PrefUtil.hadLogin()) {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        if (PrefUtil.thisVersionFirstOpen()) {
            // TODO: 17-7-6 统一清理缓存
            ImageUtil.clearMemory(context);
            ImageUtil.clearDiskCache(context);
            PrefUtil.setIsThisVersionFirstOpen(false);
            PrefUtil.setIsSimpleBoardList(true);
        }

        pkTracker();
    }

    private void pkTracker() {
        getTrackerHelper().screen("").title("首页").with(getTracker());
    }

    private void clearFullScreen() {
        StatusBarUtil.setColor(this, ResourceUtil.getColor(this, R.color.colorPrimaryDark), 0);
    }

    private void loadFragment() {
        showHideFragment(mFragments[mShowingFragment], mFragments[mHidingFragment]);
        mHidingFragment = mShowingFragment;
    }

    @Override
    public void onGotMessageCount(final int count) {
        int c = count > 0 ? count : 0;
        mNearBy.setBadgeCount(c);
    }

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
    public void onGetMessageFailed(String m) {
//        LogUtil.dd("onGetMessageFailed()");
        if (PrefUtil.isNoAccountUser()) {
            return;
        }
        if (m.contains("token") || m.contains("UID") || m.contains("过期") || m.contains("无效")) {
            SnackBarUtil.error(mActivity, "当前账户的登录信息已过期，请重新登录", true);
            HandlerUtil.postDelay(() -> {
                AuthUtil.logout();
                startActivity(IntentUtil.toLogin(mContext));
                ActivityManager.getActivityManager().finishAllActivity();
            }, 3000);
        }
    }

}
