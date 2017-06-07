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
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumFragment;
import com.twtstudio.bbs.bdpqchen.bbs.individual.IndividualFragment;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainFragment;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

import static com.pgyersdk.update.UpdateManagerListener.startDownloadTask;


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

        // TODO: 17-5-22 解决夜间模式View的空指针问题
        /*if (PrefUtil.hadLogin()) {
            mPresenter.initIndividualInfo();
        }*/

        if (!BuildConfig.DEBUG){
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
                                        .title("最新版本更新")
                                        .content(appBean.getReleaseNote())
                                        .positiveText("立即下载")
                                        .positiveColor(ResourceUtil.getColor(mContext, R.color.colorPrimary))
                                        .onPositive((new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                                hasPermission(appBean);
                                            }
                                        }))
                                        .negativeText("再说吧")
                                        .show();
                            }
                        });
            }, 1000);
        }

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
    public void showUpdateDialog(int versionCode) {
    }

    @Override
    public void showIndividualInfo(IndividualInfoModel info) {
        LogUtil.d("receive a response");
        if (info != null) {
            LogUtil.dd("on");
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
            LogUtil.dd("unread", String.valueOf(unRead));
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
