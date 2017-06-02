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
    public void onBindViewHolder(BaseViewHolder holder0, int position) {
        //Date date=new Date(holder.data.getT_create());
        if (mDataSet != null && mDataSet.size() > 0) {
            LatestPostViewHolder holder = (LatestPostViewHolder) holder0;
            LatestPostModel.DataBean.LatestBean model = mDataSet.get(position);
            if (model.getAnonymous() == 1){
                model.setAuthor_name("匿名用户");
                LogUtil.dd("anony", String.valueOf(position));
                ImageUtil.loadIconAsBitmap(mContext, R.drawable.avatar_anonymous_left, holder.avatar);
            }else{
                ImageUtil.loadAvatarAsBitmapByUidWithLeft(mContext, model.getAuthor_id(), holder.avatar);
            }
            holder.title.setText(model.getTitle());
            holder.author.setText(model.getAuthor_name());
            holder.create_time.setText(StampUtil.getTimeFromNow(model.getT_create()));
            LogUtil.dd("thread id", String.valueOf(model.getId()));
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, ThreadActivity.class);
                intent.putExtra(INTENT_THREAD_ID, model.getId());
                LogUtil.dd("click id ", String.valueOf(model.getId()));
                intent.putExtra(INTENT_THREAD_TITLE, model.getTitle());
                mContext.startActivity(intent);
            });
        }
    }

    class LatestPostViewHolder extends BaseViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.create_time)
        TextView create_time;
        @BindView(R.id.item_civ_avatar)
        CircleImageView avatar;

        LatestPostViewHolder(View itemView) {
            super(itemView);
        }


    }

}
