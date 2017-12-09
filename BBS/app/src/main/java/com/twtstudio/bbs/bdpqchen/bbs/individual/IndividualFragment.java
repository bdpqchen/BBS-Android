package com.twtstudio.bbs.bdpqchen.bbs.individual;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.tools.AuthTool;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.home.InfoContract;
import com.twtstudio.bbs.bdpqchen.bbs.individual.friend.FriendActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.MessageActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.ReleaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.settings.SettingsActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.star.StarActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.updateInfo.UpdateInfoActivity;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_RESULT_UPDATE_INFO;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_UNREAD;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.REQUEST_CODE_UPDATE_INFO;

/**
 * Created by bdpqchen on 17-5-3.
 */

public class IndividualFragment extends BaseFragment implements IndividualContract.View {

    @BindView(R.id.individual_info_past_day_with_you)
    TextView mTvPastDays;
    @BindView(R.id.individual_item_rl)
    RelativeLayout mRlSettings;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_signature)
    TextView mTvSignature;
    @BindView(R.id.tv_points)
    TextView mTvPoints;
    @BindView(R.id.tv_post_count)
    TextView mTvPostCount;
    @BindView(R.id.civ_avatar)
    CircleImageView mCivAvatar;
    @BindView(R.id.rl_avatar)
    RelativeLayout mRlAvatar;
    @BindView(R.id.ll_nickname)
    LinearLayout mLlNickname;
    @BindView(R.id.tv_individual_unread)
    TextView mTvIndividualUnread;
    @BindView(R.id.tv_honor)
    TextView mTvHonor;
    @BindView(R.id.rl_individual_item_message)
    RelativeLayout mRlIndividualItemMessage;
    @BindView(R.id.rl_individual_item_collection)
    RelativeLayout mRlIndividualItemCollection;
    @BindView(R.id.rl_individual_item_publish)
    RelativeLayout mRlIndividualItemPublish;
    @BindView(R.id.rl_individual_item_update_info)
    RelativeLayout mRlIndividualItemUpdateInfo;
    @BindView(R.id.rl_info_friend)
    RelativeLayout mRlInfoFriend;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.iv_refresh_info)
    ImageView mIvRefreshInfo;
    @BindView(R.id.ll_need_offset)
    LinearLayout mLlNeedOffset;

    private static final int ACT_MSG = 1;
    private static final int ACT_FRIEND = 6;
    private static final int ACT_STAR = 2;
    private static final int ACT_PUBLISH = 3;
    private static final int ACT_UPDATE = 4;
    private static final int ACT_SETS = 5;
    private int mUnread = 0;
    private boolean isRefreshing = false;
    private IndividualPresenter mPresenter;
    private static String NICKNAME = PrefUtil.getInfoNickname();

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_individual;
    }

    public static IndividualFragment newInstance() {
        return new IndividualFragment();
    }

    @Override
    protected void initFragment() {
        mPresenter = new IndividualPresenter(this);
        mRlIndividualItemMessage.setOnClickListener(v -> startItemActivity(ACT_MSG));
        mRlIndividualItemCollection.setOnClickListener(v -> startItemActivity(ACT_STAR));
        mRlIndividualItemPublish.setOnClickListener(v -> startItemActivity(ACT_PUBLISH));
        mRlIndividualItemUpdateInfo.setOnClickListener(v -> startItemActivity(ACT_UPDATE));
        mRlInfoFriend.setOnClickListener(v -> startItemActivity(ACT_FRIEND));
        mCivAvatar.setOnClickListener(v -> startItemActivity(ACT_UPDATE));
        mTvPostCount.setOnClickListener(v -> startItemActivity(ACT_PUBLISH));
        mRlSettings.setOnClickListener(v -> startItemActivity(ACT_SETS));
        mIvRefreshInfo.setOnClickListener(v -> {
            mPresenter.initIndividualInfo();
            isRefreshing = true;
            getUnreadMsgCount();
        });

    }

    @Override
    protected IndividualPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (mPresenter != null) {
            getInfo();
            ImageUtil.loadMyAvatar(mContext, mCivAvatar);
            ImageUtil.loadMyBg(mContext, mIvBg);
        }
    }

    private void startItemActivity(int index) {
        if (!PrefUtil.hadLogin()) {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            Intent intent = new Intent();
            Class clazz;
            switch (index) {
                case ACT_MSG:
                    clazz = MessageActivity.class;
                    intent.putExtra(INTENT_UNREAD, mUnread);
                    break;
                case ACT_FRIEND:
                    clazz = FriendActivity.class;
                    break;
                case ACT_STAR:
                    clazz = StarActivity.class;
                    break;
                case ACT_PUBLISH:
                    clazz = ReleaseActivity.class;
                    break;
                case ACT_UPDATE:
                    clazz = UpdateInfoActivity.class;
                    break;
                case ACT_SETS:
                    clazz = SettingsActivity.class;
                    break;
                default:
                    return;
            }
            intent.setClass(mContext, clazz);
            if (ACT_UPDATE == index) {
                startActivityForResult(intent, REQUEST_CODE_UPDATE_INFO);
            } else {
                startActivity(intent);
            }
        }
    }

    private void setUnread(int unread) {
        mUnread = unread;
        if (mTvIndividualUnread != null) {
            getCallback().showUnreadMsg(unread);
            if (unread > 0) {
                mTvIndividualUnread.setVisibility(View.VISIBLE);
                mTvIndividualUnread.setText(String.valueOf(unread));
            } else {
                mTvIndividualUnread.setVisibility(View.INVISIBLE);
            }
        }
    }

    private InfoContract getCallback() {
        return (InfoContract) getActivity();
    }

    private void getInfo() {
        if (mPresenter != null) mPresenter.initIndividualInfo();
    }

    private void getUnreadMsgCount() {
        if (mPresenter != null) mPresenter.getUnreadMessageCount();
    }

    @Override
    public void onResume() {
        super.onResume();
        getUnreadMsgCount();
        if (NICKNAME.length() == 0) {
            NICKNAME = "taken";
            getInfo();
        }
    }

    @Override
    public void gotInfo(IndividualInfoModel info) {
        if (info != null) {
            AuthTool.userInfo(info);
            mTvPastDays.setText(TextUtil.getPastDays(mContext, info.getT_create()), TextView.BufferType.SPANNABLE);
            mTvPostCount.setText(String.valueOf(PrefUtil.getInfoPost()));
            mTvNickname.setText(PrefUtil.getInfoNickname());
            mTvSignature.setText(PrefUtil.getInfoSignature());
            mTvPoints.setText(String.valueOf(PrefUtil.getInfoPoints()));
            mTvHonor.setText(TextUtil.getHonor(info.getPoints()));
            if (isRefreshing) {
                ImageUtil.refreshMyAvatar(mContext, mCivAvatar);
                ImageUtil.refreshMyBg(mContext, mIvBg);
                isRefreshing = false;
            }
        }
    }

    @Override
    public void getInfoFailed(String m) {
        if (m.contains("token") || m.contains("UID") || m.contains("过期") || m.contains("无效")) {
            SnackBarUtil.error(mActivity, "当前账户的登录信息已过期，请重新登录", true);
            HandlerUtil.postDelay(() -> {
                AuthTool.logout(this.getActivity());
            }, 3000);
        }
    }

    @Override
    public void onGotMessageCount(int unread) {
        setUnread(unread);
    }

    @Override
    public void onGetMessageFailed(String m) {
        setUnread(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (this.getActivity() != null)
            StatusBarUtil.setTranslucentForImageViewInFragment(this.getActivity(), 255, null);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == REQUEST_CODE_UPDATE_INFO && resultCode == RESULT_OK
                && data.getBooleanExtra(INTENT_RESULT_UPDATE_INFO, false)) {
            refreshInfo();
        }
    }

    private void refreshInfo() {
        ImageUtil.refreshMyBg(mContext, mIvBg);
        ImageUtil.refreshMyAvatar(mContext, mCivAvatar);
        mTvNickname.setText(PrefUtil.getInfoNickname());
        mTvSignature.setText(PrefUtil.getInfoSignature());
    }

}
