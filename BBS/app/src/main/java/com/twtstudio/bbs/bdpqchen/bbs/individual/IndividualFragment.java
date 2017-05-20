package com.twtstudio.bbs.bdpqchen.bbs.individual;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.collection.CollectionActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.settings.SettingsActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.updateInfo.UpdateInfoActivity;
import com.twtstudio.bbs.bdpqchen.bbs.test.MyReleaseActivity;

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
    Unbinder unbinder;
    @BindView(R.id.civ_avatar)
    CircleImageView mCivAvatar;
    @BindView(R.id.rl_avatar)
    RelativeLayout mRlAvatar;
    @BindView(R.id.ll_nickname)
    LinearLayout mLlNickname;
    @BindView(R.id.iv_individual_message_icon)
    ImageView mIvIndividualMessageIcon;
    @BindView(R.id.tv_individual_message_title)
    TextView mTvIndividualMessageTitle;
    @BindView(R.id.tv_individual_unread)
    TextView mTvIndividualUnread;
    @BindView(R.id.iv_individual_icon_end)
    ImageView mIvIndividualIconEnd;
    @BindView(R.id.rl_individual_item_message)
    RelativeLayout mRlIndividualItemMessage;
    @BindView(R.id.iv_individual_collection_icon)
    ImageView mIvIndividualCollectionIcon;
    @BindView(R.id.tv_individual_collection_title)
    TextView mTvIndividualCollectionTitle;
    @BindView(R.id.iv_individual_collection_end)
    ImageView mIvIndividualCollectionEnd;
    @BindView(R.id.rl_individual_item_collection)
    RelativeLayout mRlIndividualItemCollection;
    @BindView(R.id.iv_individual_publish_icon)
    ImageView mIvIndividualPublishIcon;
    @BindView(R.id.tv_individual_publish_title)
    TextView mTvIndividualPublishTitle;
    @BindView(R.id.iv_individual_publish_end)
    ImageView mIvIndividualPublishEnd;
    @BindView(R.id.rl_individual_item_publish)
    RelativeLayout mRlIndividualItemPublish;
    @BindView(R.id.iv_individual_update_info_icon)
    ImageView mIvIndividualUpdateInfoIcon;
    @BindView(R.id.tv_individual_update_info_title)
    TextView mTvIndividualUpdateInfoTitle;
    @BindView(R.id.iv_individual_update_info_end)
    ImageView mIvIndividualUpdateInfoEnd;
    @BindView(R.id.rl_individual_item_update_info)
    RelativeLayout mRlIndividualItemUpdateInfo;
    @BindView(R.id.individual_item_iv_icon_start)
    ImageView mIndividualItemIvIconStart;
    @BindView(R.id.individual_item_tv_title)
    TextView mIndividualItemTvTitle;
    @BindView(R.id.individual_item_iv_icon_end)
    ImageView mIndividualItemIvIconEnd;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_individual;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initFragment() {
        setUnread();
        setPastDays(PrefUtil.getInfoCreate());
        mTvPostCount.setText(PrefUtil.getInfoPost() + "");
        mTvNickname.setText(PrefUtil.getInfoNickname());
        mTvSignature.setText(PrefUtil.getInfoSignature());
        mTvPoints.setText(PrefUtil.getInfoPoints() + "");
        ImageUtil.loadMyAvatar(mContext, mCivAvatar);

        mRlIndividualItemMessage.setOnClickListener(v -> startItemActivity(1));
        mRlIndividualItemCollection.setOnClickListener(v -> startItemActivity(2));
        mRlIndividualItemPublish.setOnClickListener(v -> startItemActivity(3));
        mRlIndividualItemUpdateInfo.setOnClickListener(v -> startItemActivity(4));
        mRlSettings.setOnClickListener(v -> startItemActivity(5));


    }

    private void startItemActivity(int index) {
//        Class clazz = MessageActivity.class;
        Class clazz = CollectionActivity.class;
        switch (index){
            case 2:
                clazz = CollectionActivity.class;
                break;
            case 3:
                clazz = MyReleaseActivity.class;
                break;
            case 4:
                clazz = UpdateInfoActivity.class;
                break;
            case 5:
                clazz = SettingsActivity.class;
                break;
        }
        Intent intent = new Intent(mContext, clazz);
        startActivity(intent);
    }

    private void setUnread() {
        int unread = PrefUtil.getInfoUnread();
        if (unread != 0){
            mTvIndividualUnread.setText(unread + "");
            mTvIndividualUnread.setVisibility(View.VISIBLE);
        }else{
            mTvIndividualUnread.setVisibility(View.GONE);
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
        setUnread();
        mTvNickname.setText(PrefUtil.getInfoNickname());
        mTvSignature.setText(PrefUtil.getInfoSignature());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void updateInfoSuccess() {
        mTvSignature.setText(PrefUtil.getInfoSignature());
        mTvNickname.setText(PrefUtil.getInfoNickname());
        showSuccess();
    }

    @Override
    public void updateInfoFailed() {
        PrefUtil.setHasUnSyncInfo(true);
        SnackBarUtil.error(this.getActivity(), "个人信息同步失败，请点击同步", "同步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdate();
            }
        });
    }

    private Bundle getUnSyncInfoBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_SIGNATURE, PrefUtil.getInfoSignature());
        bundle.putString(Constants.BUNDLE_NICKNAME, PrefUtil.getInfoNickname());
        return bundle;
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);

    }

    public void doUpdate() {
        mPresenter.doUpdateInfo(getUnSyncInfoBundle());
    }

    public void showSuccess() {
        PrefUtil.setHasUnSyncInfo(false);
        SnackBarUtil.normal(this.getActivity(), "个人信息同步成功");
    }

}
