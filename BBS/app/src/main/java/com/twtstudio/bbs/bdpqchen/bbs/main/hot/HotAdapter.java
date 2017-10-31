package com.twtstudio.bbs.bdpqchen.bbs.main.hot;

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
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TransUtil;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ANONYMOUS_NAME;


/**
 * Created by bdpqchen on 17-6-5.
 */

public class HotAdapter extends BaseAdapter<HotEntity> {

    public HotAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_hot, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder0, int position) {

        if (mDataSet != null && mDataSet.size() > 0) {
            if (holder0 instanceof HotViewHolder) {
                HotViewHolder holder = (HotViewHolder) holder0;
                HotEntity model = mDataSet.get(position);
                if (model.getAnonymous() == 1) {
                    model.setAuthor_name(ANONYMOUS_NAME);
                    ImageUtil.INSTANCE.loadIconAsBitmap(mContext, R.drawable.avatar_anonymous_left, holder.mCivHotAvatar);
                    holder.mCivHotAvatar.setOnClickListener(null);
                } else {
                    holder.mCivHotAvatar.setOnClickListener(v -> {
                        mContext.startActivity(IntentUtil.toPeople(mContext, model.getAuthor_id(), model.getAuthor_name()),
                                TransUtil.getAvatarTransOptions(mContext, holder.mCivHotAvatar));
                    });
                    ImageUtil.INSTANCE.loadAvatarAsBitmapByUidWithLeft(mContext, model.getAuthor_id(), holder.mCivHotAvatar);
                }
                holder.mTvLikeCount.setText(String.valueOf(model.getLike()));
                holder.mTvUsername.setText(model.getAuthor_name());
                holder.mTvBoardName.setText(TextUtil.getBoardName(model.getBoard_name()));
                holder.mTvThreadTitle.setText(model.getTitle());
                holder.mTvPostCount.setText(String.valueOf(model.getC_post()));
                holder.mTvHotTime.setText("发布于 " + StampUtil.getDatetimeByStamp(model.getT_create()));
                holder.mTvHotContent.setText(TextUtil.getReplacedImageContent(model.getContent()));
                holder.mTvBoardName.setOnClickListener(v -> {
                    LogUtil.dd("anonymous", String.valueOf(model.getAnonymous()));
                    mContext.startActivity(IntentUtil.toThreadList(mContext, model.getBoard_id(), model.getBoard_name(), model.getAnonymous()));
                });
                holder.itemView.setOnClickListener(v -> {
                    Intent intent = IntentUtil.toThread(mContext, model.getId(), model.getTitle(), model.getBoard_id(), model.getBoard_name());
                    mContext.startActivity(intent);
                });

            }
        }

    }

    static class HotViewHolder extends BaseViewHolder {
        @BindView(R.id.civ_hot_avatar)
        CircleImageView mCivHotAvatar;
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
        @BindView(R.id.tv_hot_content)
        TextView mTvHotContent;
        @BindView(R.id.tv_hot_time)
        TextView mTvHotTime;
        @BindView(R.id.ll_hot_body)
        LinearLayout mLlHotBody;

        HotViewHolder(View view) {
            super(view);
        }
    }
}
