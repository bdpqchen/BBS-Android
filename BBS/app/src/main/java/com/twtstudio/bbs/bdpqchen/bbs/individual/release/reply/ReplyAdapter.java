package com.twtstudio.bbs.bdpqchen.bbs.individual.release.reply;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.OnReleaseItemClickListener;

import butterknife.BindView;


/**
 * Created by bdpqchen on 17-9-18.
 */

public class ReplyAdapter extends BaseAdapter<ReplyEntity> {
    private OnReleaseItemClickListener mListener;

    ReplyAdapter(Context context, OnReleaseItemClickListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_release_reply, parent, false);
        return new ReplyHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder0, int position) {
        if (mDataSet != null && mDataSet.size() > 0) {
            if (holder0 instanceof ReplyHolder) {
                ReplyHolder holder = (ReplyHolder) holder0;
                ReplyEntity entity = mDataSet.get(position);
                holder.mTvTitle.setText(entity.getThread_title());
                holder.mTvContent.setText(entity.getContent());
                holder.mTvDelete.setOnClickListener(v -> mListener.onDeleteClick(holder.getAdapterPosition()));
                holder.mTvBottomInfo.setText(TextUtil.getPostBottomInfo(entity.getC_post(), entity.getT_create(),
                        entity.getFloor(), entity.getAnonymous()));
                holder.itemView.setOnClickListener(v -> {
                    mContext.startActivity(IntentUtil.toThread(mContext, entity.getThread_id(), entity.getThread_title(), entity.getFloor()));
                });
            }
        }
    }

    int getPostId(int position) {
        return mDataSet.get(position).getId();
    }

    void deletePost(int position) {
        // the position must be holder.getAdapterPosition().
        mDataSet.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataSet.size());
    }


    static class ReplyHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_bottom_info)
        TextView mTvBottomInfo;
        @BindView(R.id.tv_delete)
        TextView mTvDelete;

        ReplyHolder(View view) {
            super(view);
        }
    }
}
