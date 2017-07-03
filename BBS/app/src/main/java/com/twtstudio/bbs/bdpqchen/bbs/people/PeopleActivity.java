package com.twtstudio.bbs.bdpqchen.bbs.people;

import android.app.Activity;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.health.UidHealthStats;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.UID;

/**
 * Created by bdpqchen on 17-7-3.
 */

public class PeopleActivity extends BaseActivity<PeoplePresenter> implements PeopleContract.View {
    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.civ_avatar)
    CircleImageView mCivAvatar;
    @BindView(R.id.rl_avatar)
    RelativeLayout mRlAvatar;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_signature)
    TextView mTvSignature;
    @BindView(R.id.tv_points)
    TextView mTvPoints;
    @BindView(R.id.tv_post_count)
    TextView mTvPostCount;
    @BindView(R.id.info_past_day)
    TextView mInfoPastDay;
    @BindView(R.id.gl_count_data)
    GridLayout mGlCountData;
    @BindView(R.id.ll_nickname)
    LinearLayout mLlNickname;
    @BindView(R.id.ll_data)
    LinearLayout mLlData;
    @BindView(R.id.rv_people)
    RecyclerView mRvPeople;
    private int mUid = 0;
    private PeopleAdapter mAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_people;
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
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
        mUid = getIntent().getIntExtra(UID, 0);
        mPresenter.getUserInfo(mUid);
        ImageUtil.loadAvatarByUid(this, mUid, mCivAvatar);
        ImageUtil.loadBgByUid(this, mUid, mIvBg);
        mAdapter = new PeopleAdapter(mContext);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvPeople.setLayoutManager(manager);
        mRvPeople.setAdapter(mAdapter);
        mRvPeople.addItemDecoration(new RecyclerViewItemDecoration(1));
        mRvPeople.setNestedScrollingEnabled(false);



    }

    @Override
    public void onGetUserInfo(PeopleModel model) {
        if (model != null){
            mTvPostCount.setText(model.getC_post() + "");
            mTvNickname.setText(model.getNickname());
            mTvSignature.setText(model.getSignature());
            mTvPoints.setText(model.getPoints() + "");
            mInfoPastDay.setText(TextUtil.getPastDays(mContext, model.getT_create()), TextView.BufferType.SPANNABLE);
            LogUtil.dd("Will notify", String.valueOf(model.getRecent().size()));
            mAdapter.addList(model.getRecent());

        }
    }

    @Override
    public void onGetUserInfoFailed(String m) {
        SnackBarUtil.error(this, m);
    }
}
