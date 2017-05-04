package com.twtstudio.bbs.bdpqchen.bbs.individual;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.ScrollProblemHelper;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

        String days = "222" + "天";
        int daysLength = days.length();
        SpannableString styledText = new SpannableString(days);
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.tvTextSizeNormal), 0, daysLength - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.tvTextSizeVeryLittle), daysLength - 1, daysLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvPastDays.setText(styledText, TextView.BufferType.SPANNABLE);
        mRlSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("hhh clicked ");
            }
        });


    }

    private void setDataSets() {

        for (int i = 0; i < 4; i++){
            IndividualListModel temp = new IndividualListModel();
            temp.iconEnd = R.drawable.ic_keyboard_arrow_right_black_24dp;
            if (i == 0){
                temp.title = "我的消息";
                temp.hasTag = true;
                temp.tagNum = 98;
                temp.iconStart = R.drawable.icon_ll_my_message;
            }else{
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


}
