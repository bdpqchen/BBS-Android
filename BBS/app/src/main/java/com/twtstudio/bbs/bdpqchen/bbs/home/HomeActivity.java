package com.twtstudio.bbs.bdpqchen.bbs.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.twtstudio.bbs.bdpqchen.bbs.BuildConfig;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.manager.ActivityManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.AuthUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.forum.ForumFragment;
import com.twtstudio.bbs.bdpqchen.bbs.individual.IndividualFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainFragment;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

import static com.pgyersdk.update.UpdateManagerListener.startDownloadTask;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.USERNAME;


public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {

    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;
//    @BindView(R.id.mask_home)
//    View mMask;

    BottomBarTab mNearBy;

    private Context mContext;
    private HomeActivity mHomeActivity;
    private SupportFragment[] mFragments = new SupportFragment[3];
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private int mShowingFragment = FIRST;
    private int mHidingFragment = FIRST;

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
        mHomeActivity = this;
        mContext = this;
        LogUtil.dd("token", PrefUtil.getAuthToken());
        // TODO: 17-5-3 非登录后跳转到这里，是否渐变
        // 登录后的渐变,
        if (!PrefUtil.isNoAccountUser()) {
            PrefUtil.setHadLogin(true);
        }
//        mMask.setVisibility(View.VISIBLE);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(findViewById(R.id.mask_home), "alpha", 0f).setDuration(600);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(findViewById(R.id.mask_home), "alpha", 0f).setDuration(600);
//        animator.setStartDelay(400);
//        animator.start();

        if (savedInstanceState == null) {
            mFragments[FIRST] = MainFragment.newInstance();
            mFragments[SECOND] = ForumFragment.newInstance();
            mFragments[THIRD] = IndividualFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_main_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            mFragments[FIRST] = findFragment(MainFragment.class);
            mFragments[SECOND] = findFragment(ForumFragment.class);
            mFragments[THIRD] = findFragment(IndividualFragment.class);
        }

        mNearBy = mBottomBar.getTabWithId(R.id.bottom_bar_tab_individual);
        mBottomBar.setOnTabSelectListener(i -> {
            LogUtil.dd("onTabSelected()", String.valueOf(i));
            if (PrefUtil.hadLogin()) {
                if (i == R.id.bottom_bar_tab_main) {
                    mShowingFragment = FIRST;
                } else if (i == R.id.bottom_bar_tab_forum) {
                    mShowingFragment = SECOND;
                } else if (i == R.id.bottom_bar_tab_individual) {
                    mShowingFragment = THIRD;
                }
                loadFragment();
            } else if (i == R.id.bottom_bar_tab_individual && !PrefUtil.hadLogin()) {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        autoCheckUpdate();

//        mPresenter.getUnreadMessageCount();

    }

    private void startDownload(AppBean appBean) {
        startDownloadTask(HomeActivity.this, appBean.getDownloadURL());
    }

    private void hasPermission(AppBean appBean) {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        LogUtil.dd("onPermissionGranted");
                        startDownload(appBean);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        LogUtil.dd("onPermissionDenied");
                        SnackBarUtil.error(mHomeActivity, "请赋予我读取存储器内容的权限");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        SnackBarUtil.error(mHomeActivity, "请赋予我读取存储器内容的权限");
                    }
                })
                .check();

    }

    private void loadFragment() {
        showHideFragment(mFragments[mShowingFragment], mFragments[mHidingFragment]);
        mHidingFragment = mShowingFragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyUpdateManager.unregister();
    }

    @Override
    public void onGotMessageCount(int count) {
        if (count > 0) {
            mNearBy.setBadgeCount(count);
        } else {
            mNearBy.setBadgeCount(0);
        }
    }

    @Override
    public void onGetMessageFailed(String m) {
        LogUtil.dd("onGetMessageFailed()");
        if (m.contains("token") || m.contains("UID") || m.contains("过期") || m.contains("无效")) {
            SnackBarUtil.error(mActivity, "当前账户的登录信息已过期，请重新登录", true);
            HandlerUtil.postDelay(() -> {
                AuthUtil.logout();
                Intent intent = new Intent(mActivity, LoginActivity.class);
                intent.putExtra(USERNAME, PrefUtil.getAuthUsername());
                startActivity(intent);
                ActivityManager.getActivityManager().finishAllActivity();
            }, 3000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.dd("home onResume", "getUnreadMessage");
        if (mPresenter != null) {
            mPresenter.getUnreadMessageCount();
        }
    }

    private void autoCheckUpdate() {
        if (!BuildConfig.DEBUG) {
            HandlerUtil.postDelay(() -> {
                PgyUpdateManager.register(this, "9981",
                        new UpdateManagerListener() {
                            @Override
                            public void onNoUpdateAvailable() {
                                LogUtil.dd("not update available");
                            }

                            @Override
                            public void onUpdateAvailable(final String result) {
                                // 将新版本信息封装到AppBean中
                                final AppBean appBean = getAppBeanFromString(result);
                                new MaterialDialog.Builder(mContext)
                                        .cancelable(false)
                                        .title("最最最新版本更新")
                                        .content(appBean.getReleaseNote())
                                        .positiveText("下载(校园网免流)")
                                        .positiveColor(ResourceUtil.getColor(mContext, R.color.colorPrimary))
                                        .onPositive((new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                                hasPermission(appBean);
                                            }
                                        }))
                                        .negativeText("立即不下载")
                                        .show();
                            }
                        });
            }, 2000);
        }
    }

}
