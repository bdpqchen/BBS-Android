package com.twtstudio.bbs.bdpqchen.bbs.main.latestPost;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.main.TimeUtils;


import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.R.drawable.avatar1;

/**
 * Created by zhangyulong on 5/12/17.
 */

public class LatestPostAdapter extends BaseAdapter<LatestPostModel.DataBean.LatestBean>{
    LatestPostContract.ItemClickListener mItemClickListener;

    public void setOnItemClickListener(LatestPostContract.ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public LatestPostAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LatestPostViewHolder holder = new LatestPostViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.activity_latest_post_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        LatestPostViewHolder mholder=(LatestPostViewHolder) holder;
        mholder.itemView.setTag(position);
        mholder.announceBean= mDataSet.get(position);
        String avatarurl="https://bbs.twtstudio.com/api/user/"+mholder.announceBean.getAuthor_id()+"/avatar";
        mholder.title.setText(mholder.announceBean.getTitle());
        mholder.author.setText(mholder.announceBean.getAuthor_name());
        mholder.create_time.setText(TimeUtils.getStandardDate(mholder.announceBean.getT_create()));
        mholder.collection.setAlpha(0x000);
        ImageUtil.loadItemAvatar(mContext,mholder.avatar,avatarurl);

    }

    class LatestPostViewHolder extends BaseViewHolder implements View.OnClickListener{
        @BindView(R.id.main_collection_delete)
        ImageView collection;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.create_time)
        TextView create_time;
        @BindView(R.id.item_civ_avatar)
        CircleImageView avatar;
        LatestPostModel.DataBean.LatestBean announceBean;
        public LatestPostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.OnItemClick((LatestPostContract.View) v,(Integer) itemView.getTag());
            }
            Intent intent = new Intent(mContext,LatestPostContent.class);
            mContext.startActivity(intent);
        }
    }

}
