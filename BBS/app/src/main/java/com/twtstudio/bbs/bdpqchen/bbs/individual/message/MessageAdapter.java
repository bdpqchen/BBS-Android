package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;

import butterknife.BindView;

/**
 * Created by Ricky on 2017/5/19.
 */

public class MessageAdapter extends BaseAdapter<MessageModel> {

    LayoutInflater inflater;

    public MessageAdapter(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageItemViewHolder(inflater.inflate(R.layout.item_rv_message, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        MessageModel item = mDataSet.get(position);
        MessageItemViewHolder holderConverted = (MessageItemViewHolder) holder;

        ImageUtil.loadAvatarByUid(mContext, item.getAuthor_id(), holderConverted.ivAvatar);
        holderConverted.tvFrom.setText(item.getAuthor_nickname());
        holderConverted.tvTime.setText(StampUtil.getDatetimeByStamp(item.getT_create()));
        holderConverted.tvSummary.setText(item.getContent());
    }

    static class MessageItemViewHolder extends BaseViewHolder {


        @BindView(R.id.iv_avatar)
        public ImageView ivAvatar;
        @BindView(R.id.tv_from)
        TextView tvFrom;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_summary)
        TextView tvSummary;

        public MessageItemViewHolder(View itemView) {
            super(itemView);
        }
    }

}
