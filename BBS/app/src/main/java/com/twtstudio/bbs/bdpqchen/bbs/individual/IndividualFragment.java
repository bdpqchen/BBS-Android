package com.twtstudio.bbs.bdpqchen.bbs.individual;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.friend.FriendActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.MessageActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.MyReleaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.settings.SettingsActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.star.StarActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.updateInfo.UpdateInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

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
    @BindView(R.id.ll_need_offset)
    LinearLayout mLlNeedOffset;
    Unbinder unbinder;
    private static final int ACT_MSG = 1;
    private static final int ACT_FRIEND = 6;
    private static final int ACT_STAR = 2;
    private static final int ACT_PUBLISH = 3;
    private static final int ACT_UPDATE = 4;
    private static final int ACT_SETS = 5;
    private int beingActivity = 0;

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
        ImageUtil.loadMyAvatar(mContext, mCivAvatar);
        ImageUtil.loadMyBg(mContext, mIvBg);

        mRlIndividualItemMessage.setOnClickListener(v -> startItemActivity(ACT_MSG));
        mRlIndividualItemCollection.setOnClickListener(v -> startItemActivity(ACT_STAR));
        mRlIndividualItemPublish.setOnClickListener(v -> startItemActivity(ACT_PUBLISH));
        mRlIndividualItemUpdateInfo.setOnClickListener(v -> startItemActivity(ACT_UPDATE));
        mRlInfoFriend.setOnClickListener(v -> startItemActivity(ACT_FRIEND));
        mCivAvatar.setOnClickListener(v -> startItemActivity(ACT_UPDATE));
        mTvPostCount.setOnClickListener(v -> startItemActivity(ACT_PUBLISH));
        mRlSettings.setOnClickListener(v -> startItemActivity(ACT_SETS));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.initIndividualInfo();
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
            startActivity(intent);
        }
    }

    private void setUnread(int unread) {
        if (mTvIndividualUnread != null) {
            if (unread > 0) {
                mTvIndividualUnread.setVisibility(View.VISIBLE);
                mTvIndividualUnread.setText(unread + "");
            } else {
                mTvIndividualUnread.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setPastDays(int pastDays) {
        String days = StampUtil.getDaysFromCreateToNow(pastDays) + "天";
        int daysLength = days.length();
        SpannableString styledText = new SpannableString(days);
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.tvTextSizeNormal), 0, daysLength - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.tvTextSizeVeryLittle), daysLength - 1, daysLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvPastDays.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getUnreadMessageCount();
        if (beingActivity == ACT_UPDATE) {
            mTvNickname.setText(PrefUtil.getInfoNickname());
            mTvSignature.setText(PrefUtil.getInfoSignature());
            ImageUtil.refreshMyAvatar(mContext, mCivAvatar);
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
            setPastDays(PrefUtil.getInfoCreate());
            mTvPostCount.setText(PrefUtil.getInfoPost() + "");
            mTvNickname.setText(PrefUtil.getInfoNickname());
            mTvSignature.setText(PrefUtil.getInfoSignature());
            mTvPoints.setText(PrefUtil.getInfoPoints() + "");

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
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this.getActivity(), null);

        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
