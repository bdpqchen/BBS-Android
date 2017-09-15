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
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.friend.FriendActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.MessageActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.MyReleaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.settings.SettingsActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.star.StarActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.updateInfo.UpdateInfoActivity;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_UNREAD;

/**
 * Created by bdpqchen on 17-5-3.
 */

public class IndividualFragment extends BaseFragment<IndividualPresenter> implements IndividualContract.View {

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
    private int beingActivity = 0;
    private int mUnread = 0;
    private boolean isRefreshing = false;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_individual;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    public static IndividualFragment newInstance() {
        return new IndividualFragment();
    }

    @Override
    protected void initFragment() {

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
            mPresenter.getUnreadMessageCount();
        });

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.initIndividualInfo();
            ImageUtil.loadMyAvatar(mContext, mCivAvatar);
            ImageUtil.loadMyBg(mContext, mIvBg);

        }
    }

    private void startItemActivity(int index) {
        if (!PrefUtil.hadLogin()) {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            beingActivity = index;
            Class clazz;
            switch (index) {
                case ACT_MSG:
                    clazz = MessageActivity.class;
                    break;
                case ACT_FRIEND:
                    clazz = FriendActivity.class;
                    break;
                case ACT_STAR:
                    clazz = StarActivity.class;
                    break;
                case ACT_PUBLISH:
                    clazz = MyReleaseActivity.class;
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
            Intent intent = new Intent(mContext, clazz);
            if (index == ACT_MSG){
                intent.putExtra(INTENT_UNREAD, mUnread);
            }
            startActivity(intent);
        }
    }

    private void setUnread(int unread) {
        mUnread = unread;
        if (mTvIndividualUnread != null) {
            if (unread > 0) {
                mTvIndividualUnread.setVisibility(View.VISIBLE);
                mTvIndividualUnread.setText(unread + "");
            } else {
                mTvIndividualUnread.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getUnreadMessageCount();
        if (beingActivity == ACT_UPDATE) {
            mTvNickname.setText(PrefUtil.getInfoNickname());
            mTvSignature.setText(PrefUtil.getInfoSignature());
            ImageUtil.refreshMyAvatar(mContext, mCivAvatar);
            ImageUtil.refreshMyBg(mContext, mIvBg);
            beingActivity = 0;
        }
    }

    @Override
    public void gotInfo(IndividualInfoModel info) {
        if (info != null) {
            PrefUtil.setInfoNickname(info.getNickname());
            PrefUtil.setInfoSignature(info.getSignature());
            PrefUtil.setInfoOnline(info.getC_online());
            PrefUtil.setInfoPost(info.getC_post());
            PrefUtil.setInfoPoints(info.getPoints());
            PrefUtil.setInfoCreate(info.getT_create());
            PrefUtil.setInfoGroup(info.getGroup());
            PrefUtil.setInfoLevel(info.getLevel());
            PrefUtil.setIsLatestInfo(true);
            mTvPastDays.setText(TextUtil.getPastDays(mContext, info.getT_create()), TextView.BufferType.SPANNABLE);
            mTvPostCount.setText(PrefUtil.getInfoPost() + "");
            mTvNickname.setText(PrefUtil.getInfoNickname());
            mTvSignature.setText(PrefUtil.getInfoSignature());
            mTvPoints.setText(PrefUtil.getInfoPoints() + "");
            mTvHonor.setText(TextUtil.getHonor(info.getPoints()));
            if (isRefreshing){
                ImageUtil.loadMyAvatar(mContext, mCivAvatar);
                ImageUtil.loadMyBg(mContext, mIvBg);
                isRefreshing = false;
            }
        }
    }

    @Override
    public void getInfoFailed(String m) {

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
        StatusBarUtil.setTranslucentForImageViewInFragment(this.getActivity(), 255, null);
        return rootView;
    }


}
