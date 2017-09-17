package com.twtstudio.bbs.bdpqchen.bbs.main.latest;

import android.content.Context;
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
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ANONYMOUS_NAME;


/**
 * Created by bdpqchen on 17-6-5.
 */

public class LatestAdapter extends BaseAdapter<LatestEntity> {


    public LatestAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LatestViewHolder holder = new LatestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_latest, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder0, int position) {

        if (mDataSet != null && mDataSet.size() > 0) {
            if (holder0 instanceof LatestViewHolder) {
                LatestViewHolder holder = (LatestViewHolder) holder0;
                LatestEntity model = mDataSet.get(position);
                if (model.getAnonymous() == 1) {
                    model.setAuthor_name(ANONYMOUS_NAME);
                    ImageUtil.loadIconAsBitmap(mContext, R.drawable.avatar_anonymous_left, holder.mCivLatestAvatar);
                    holder.mLlLayerHeader.setOnClickListener(null);
                } else {
                    holder.mLlLayerHeader.setOnClickListener(v -> {
                        mContext.startActivity(IntentUtil.toPeople(mContext, model.getAuthor_id()));
                    });
                    ImageUtil.loadAvatarAsBitmapByUidWithLeft(mContext, model.getAuthor_id(), holder.mCivLatestAvatar);
                }
                holder.mTvUsername.setText(model.getAuthor_name());
                holder.mTvBoardName.setText(TextUtil.getBoardName(model.getBoard_name()));
                holder.mTvThreadTitle.setText(model.getTitle());
                holder.mTvPostCount.setText(String.valueOf(model.getC_post()));
                holder.mTvLatestTime.setText(StampUtil.getTimeFromNow(model.getT_create(), model.getT_reply()));
                holder.mTvBoardName.setOnClickListener(v -> {
                    mContext.startActivity(IntentUtil.toThreadList(mContext, model.getBoard_id(), model.getBoard_name(), model.getAnonymous()));
                });
                holder.mLlLatestBody.setOnClickListener(v -> {
                    mContext.startActivity(IntentUtil.toThread(mContext, model.getId(), model.getTitle(), model.getBoard_id(), model.getBoard_name()));
                });
            }
        }

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
}
