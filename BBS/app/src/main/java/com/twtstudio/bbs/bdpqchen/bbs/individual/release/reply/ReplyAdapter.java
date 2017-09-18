package com.twtstudio.bbs.bdpqchen.bbs.individual.release.reply;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by bdpqchen on 17-9-18.
 */

public class ReplyAdapter extends BaseAdapter<ReplyEntity> {

    ReplyAdapter(Context context) {
        super(context);
        mContext = context;
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
                holder.mTvDatetime.setText(TextUtil.getPostCountAndTime(entity.getC_post(), entity.getT_create()));
                holder.mTvFloorAnon.setText(TextUtil.getFloorAndAnon(entity.getFloor(), entity.getAnonymous()));
            }
        }
    }

    static class ReplyHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_datetime)
        TextView mTvDatetime;
        @BindView(R.id.tv_post_floor_and_anon)
        TextView mTvFloorAnon;

        ReplyHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
