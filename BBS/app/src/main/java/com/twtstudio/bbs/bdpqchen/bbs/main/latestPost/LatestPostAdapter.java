package com.twtstudio.bbs.bdpqchen.bbs.main.latestPost;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity;
import com.twtstudio.bbs.bdpqchen.bbs.main.TimeUtils;
import com.twtstudio.bbs.bdpqchen.bbs.main.model.LatestPostModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_TITLE;

/**
 * Created by zhangyulong on 5/12/17.
 */

public class LatestPostAdapter extends BaseAdapter<LatestPostModel.DataBean.LatestBean> {
    public LatestPostAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LatestPostViewHolder holder = new LatestPostViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_main_latest_post, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        //Date date=new Date(mholder.data.getT_create());
        if (mDataSet != null && mDataSet.size() > 0) {
            LatestPostViewHolder mholder = (LatestPostViewHolder) holder;
            mholder.data = mDataSet.get(position);
            LatestPostModel.DataBean.LatestBean model = mDataSet.get(position);
            mholder.title.setText(model.getTitle());
            mholder.author.setText(model.getAuthor_name());
            mholder.create_time.setText(StampUtil.getTimeFromNow(model.getT_create()));
            ImageUtil.loadAvatarAsBitmapByUid(mContext, model.getAuthor_id(), mholder.avatar);
            LogUtil.dd("thread id", String.valueOf(model.getId()));
            mholder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, ThreadActivity.class);
                intent.putExtra(INTENT_THREAD_ID, model.getId());
                LogUtil.dd("click id ", String.valueOf(model.getId()));
                intent.putExtra(INTENT_THREAD_TITLE, model.getTitle());
                mContext.startActivity(intent);
            });
        }
    }

    class LatestPostViewHolder extends BaseViewHolder implements View.OnClickListener {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.create_time)
        TextView create_time;
        @BindView(R.id.item_civ_avatar)
        CircleImageView avatar;
        LatestPostModel.DataBean.LatestBean data;

        public LatestPostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


        }
    }

}
