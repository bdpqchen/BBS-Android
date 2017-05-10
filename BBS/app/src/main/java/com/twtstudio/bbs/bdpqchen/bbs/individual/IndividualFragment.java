package com.twtstudio.bbs.bdpqchen.bbs.individual;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.ScrollProblemHelper;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualListModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-5-3.
 */

public class IndividualFragment extends BaseFragment<IndividualPresenter> implements IndividualContract.View {

    @BindView(R.id.lv_individual)
    ListView mListView;
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

    private IndividualListViewAdapter mListViewAdapter;
    private List<IndividualListModel> mDataSets = new ArrayList<>();


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
        setDataSets();

        mListViewAdapter = new IndividualListViewAdapter(this.getContext(), mDataSets);
        mListView.setAdapter(mListViewAdapter);
        ScrollProblemHelper.setListViewHeightBasedOnChildren(mListView);

        mRlSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SettingsActivity.class));
            }
        });

        setPastDays(PrefUtil.getInfoCreate());
        mTvPostCount.setText(PrefUtil.getInfoPost() + "");
        mTvNickname.setText(PrefUtil.getInfoNickname());
        mTvSignature.setText(PrefUtil.getInfoSignature());
        mTvPoints.setText(PrefUtil.getInfoPoints() + "");

    }

    public void setPastDays(int pastDays) {
        String days = StampUtil.getDaysFromCreateToNow(pastDays) + "天";
        int daysLength = days.length();
        SpannableString styledText = new SpannableString(days);
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.tvTextSizeNormal), 0, daysLength - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.tvTextSizeVeryLittle), daysLength - 1, daysLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvPastDays.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    private void setDataSets() {

        int unread = PrefUtil.getInfoUnread();

        for (int i = 0; i < 4; i++) {
            IndividualListModel temp = new IndividualListModel();
            temp.iconEnd = R.drawable.ic_keyboard_arrow_right_black_24dp;
            if (i == 0) {
                temp.title = "我的消息";
                if (unread + 1 > 0) {
                    temp.hasTag = true;
                    temp.tagNum = unread;
                }
                temp.iconStart = R.drawable.icon_ll_my_message;
            } else {
                temp.hasTag = false;
//                temp.tagNum = 0;
            }
            mDataSets.add(temp);
        }
        mDataSets.get(1).iconStart = R.drawable.icon_my_collection;
        mDataSets.get(2).iconStart = R.drawable.icon_my_published;
        mDataSets.get(3).iconStart = R.drawable.icon_my_detail_update;
        mDataSets.get(1).title = "我的收藏";
        mDataSets.get(2).title = "我的发布";
        mDataSets.get(3).title = "编辑资料";
    }

    @Override
    public void onResume() {
        super.onResume();
        setDataSets();
        mListViewAdapter.notifyDataSetChanged();
        mTvNickname.setText(PrefUtil.getInfoNickname());
        mTvSignature.setText(PrefUtil.getInfoSignature());
        if (PrefUtil.hasUnSyncInfo()){
            mPresenter.doUpdateInfo(getUnSyncInfoBundle());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
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
        PrefUtil.setHasUnSyncInfo(false);
        mTvSignature.setText(PrefUtil.getInfoSignature());
        mTvNickname.setText(PrefUtil.getInfoNickname());
        SnackBarUtil.normal(this.getActivity(), "个人信息同步成功");

    }

    @Override
    public void updateInfoFailed() {
        PrefUtil.setHasUnSyncInfo(false);
        SnackBarUtil.error(this.getActivity(), "个人信息同步失败，请点击同步", "同步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.doUpdateInfo(getUnSyncInfoBundle());
            }
        });
    }

    private Bundle getUnSyncInfoBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_SIGNATURE, PrefUtil.getInfoSignature());
        bundle.putString(Constants.BUNDLE_NICKNAME, PrefUtil.getInfoNickname());
        return bundle;
    }
}
