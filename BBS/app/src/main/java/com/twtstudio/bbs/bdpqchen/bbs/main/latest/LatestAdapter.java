package com.twtstudio.bbs.bdpqchen.bbs.main.latest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IsUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.RandomUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TransUtil;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ANONYMOUS_NAME;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_CREATE_THREAD;


/**
 * Created by bdpqchen on 17-6-5.
 */

public class LatestAdapter extends BaseAdapter<LatestEntity> {

    LatestAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_CREATE_THREAD) {
            return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_header, parent, false));
        } else {
            return new LatestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_latest, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder0, int position) {
//        LogUtil.dd("position--->", String.valueOf(position));
        if (mDataSet != null && mDataSet.size() > 0) {
            if (holder0 instanceof LatestViewHolder) {
                LatestViewHolder holder = (LatestViewHolder) holder0;
                LatestEntity model = mDataSet.get(position);
                if (IsUtil.isAnon(model.getAnonymous())) {
                    model.setAuthor_id(0);
                    model.setAuthor_name(ANONYMOUS_NAME);
                    holder.mLlLayerHeader.setOnClickListener(null);
                } else {
                    holder.mLlLayerHeader.setOnClickListener(v -> {
                        Intent intent = IntentUtil.toPeople(mContext, model.getAuthor_id());
                        mContext.startActivity(intent, TransUtil.getAvatarTransOptions(mContext, holder.mCivLatestAvatar));
                    });
                }
                ImageUtil.loadAvatarButAnon(mContext, model.getAuthor_id(), holder.mCivLatestAvatar);
                holder.mTvLikeCount.setText(String.valueOf(model.getLike()));
                holder.mTvUsername.setText(model.getAuthor_name());
                holder.mTvBoardName.setText(TextUtil.getBoardName(model.getBoard_name()));
                holder.mTvThreadTitle.setText(model.getTitle());
                holder.mTvPostCount.setText(String.valueOf(model.getC_post()));
                holder.mTvLatestTime.setText(StampUtil.getTimeFromNow(model.getT_create(), model.getT_reply()));
                holder.itemView.setOnClickListener(v -> {
                    mContext.startActivity(IntentUtil.toThread(mContext, model.getId(), model.getTitle(), model.getBoard_id(), model.getBoard_name()));
                });
                holder.mTvBoardName.setOnClickListener(v -> {
                    mContext.startActivity(IntentUtil.toThreadList(mContext, model.getBoard_id(), model.getBoard_name(), model.getAnonymous()));
                });
            } else if (holder0 instanceof HeaderHolder) {
                HeaderHolder holder = (HeaderHolder) holder0;
                holder.mTvUsername.setText(PrefUtil.getAuthUsername());
                ImageUtil.loadMyAvatar(mContext, holder.mCivMyAvatar);
                holder.mTvInduceCreate.setText(RandomUtil.getInduceCreateText());
                holder.itemView.setOnClickListener(v -> {
                    mContext.startActivity(IntentUtil.toCreateThread(mContext));
                });
            }

        }
    }

    void notifyHeader(){
        notifyItemChanged(0);
    }

    class LatestViewHolder extends BaseViewHolder {

        @BindView(R.id.civ_latest_avatar)
        CircleImageView mCivLatestAvatar;
        @BindView(R.id.tv_username)
        TextView mTvUsername;
        @BindView(R.id.tv_board_name)
        TextView mTvBoardName;
        @BindView(R.id.tv_thread_title)
        TextView mTvThreadTitle;
        @BindView(R.id.tv_post_count)
        TextView mTvPostCount;
        @BindView(R.id.tv_like_count)
        TextView mTvLikeCount;
        @BindView(R.id.tv_latest_time)
        TextView mTvLatestTime;
        @BindView(R.id.ll_layer_header)
        LinearLayout mLlLayerHeader;
        @BindView(R.id.ll_latest_body)
        LinearLayout mLlLatestBody;

        LatestViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class HeaderHolder extends BaseViewHolder {
        @BindView(R.id.civ_my_avatar)
        CircleImageView mCivMyAvatar;
        @BindView(R.id.tv_username)
        TextView mTvUsername;
        @BindView(R.id.tv_induce_to_create)
        TextView mTvInduceCreate;

        HeaderHolder(View view) {
            super(view);
        }
    }
}
