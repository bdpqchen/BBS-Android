package com.twtstudio.bbs.bdpqchen.bbs.individual.friend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bdpqchen on 17-6-29.
 */

public class FriendAdapter extends BaseAdapter<FriendModel> {

    public FriendAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder0, int position) {
        if (mDataSet != null && mDataSet.size() > 0) {
            if (holder0 instanceof ViewHolder) {
                ViewHolder holder = (ViewHolder) holder0;
                FriendModel model = mDataSet.get(position);
                holder.mTvFriendName.setText(TextUtil.getTwoNames(model.getName(), model.getNickname()));
                holder.mTvFriendSignature.setText(model.getSignature());
                if (model.getStatus() != 1){
                    holder.mTvConfirmStatus.setVisibility(View.VISIBLE);
                }
                ImageUtil.loadAvatarAsBitmapByUidWithLeft(mContext, model.getUid(), holder.mCivFriendAvatar);
                holder.mCivFriendAvatar.setOnClickListener(v -> {
                    mContext.startActivity(IntentUtil.toPeople(mContext, model.getUid()));
                });
                holder.itemView.setOnClickListener(v -> {
                    mContext.startActivity(IntentUtil.toLetter(mContext, model.getUid()));

                });
            }
        }
    }

    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.civ_friend_avatar)
        CircleImageView mCivFriendAvatar;
        @BindView(R.id.tv_friend_name)
        TextView mTvFriendName;
        @BindView(R.id.tv_confirm_status)
        TextView mTvConfirmStatus;
        @BindView(R.id.tv_friend_signature)
        TextView mTvFriendSignature;
        ViewHolder(View view) {
            super(view);
        }
    }
}
