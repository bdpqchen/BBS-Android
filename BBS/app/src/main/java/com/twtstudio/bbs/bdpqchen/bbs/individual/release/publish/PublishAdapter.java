package com.twtstudio.bbs.bdpqchen.bbs.individual.release.publish;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class PublishAdapter extends BaseAdapter<PublishEntity> {

    private OnReleaseItemClickListener mListener;

    PublishAdapter(Context context, OnReleaseItemClickListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_release_publish, parent, false);
        return new PublishHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder0, int position) {
        if (mDataSet != null && mDataSet.size() > 0) {
            if (holder0 instanceof PublishHolder) {
                PublishHolder holder = (PublishHolder) holder0;
                PublishEntity entity = mDataSet.get(position);
                holder.mTvTitle.setText(entity.getTitle());
                holder.mTvContent.setText(entity.getContent());
                holder.mTvDatetime.setText(TextUtil.getPostCountAndTime(entity.getC_post(), entity.getT_create()));
                holder.mTvDelete.setOnClickListener(v -> mListener.onDeleteClick(holder.getAdapterPosition()));
                holder.itemView.setOnClickListener(v -> {
                    mContext.startActivity(IntentUtil.toThread(mContext, entity.getId(), entity.getTitle(), 0));
                });
            }
        }
    }

    int getThreadId(int position) {
        return mDataSet.get(position).getId();
    }

    void deleteThread(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataSet.size());
    }

    static class PublishHolder extends BaseViewHolder {
        @BindView(R.id.rl_publish)
        ConstraintLayout mRlPublish;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_datetime)
        TextView mTvDatetime;
        @BindView(R.id.tv_delete)
        ImageView mTvDelete;

        PublishHolder(View view) {
            super(view);
        }
    }
}
