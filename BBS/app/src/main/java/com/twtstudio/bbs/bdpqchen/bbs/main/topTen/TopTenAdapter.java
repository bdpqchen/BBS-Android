package com.twtstudio.bbs.bdpqchen.bbs.main.topTen;

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
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity;
import com.twtstudio.bbs.bdpqchen.bbs.main.model.MainModel;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_TITLE;


/**
 * Created by zhangyulong on 5/13/17.
 */

public class TopTenAdapter extends BaseAdapter<MainModel.HotBean> {

    public TopTenAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main_latest_post, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mDataSet != null && mDataSet.size() > 0) {
            ViewHolder mHolder = (ViewHolder) holder;
            MainModel.HotBean hotList = mDataSet.get(position);
            mHolder.mTitle.setText(hotList.getTitle());
            mHolder.mAuthor.setText(hotList.getAuthor_name());
            mHolder.mCreateTime.setText(StampUtil.getDateByStamp(hotList.getT_create()));
            ImageUtil.loadAvatarAsBitmapByUid(mContext, hotList.getAuthor_id(), mHolder.mItemCivAvatar);
            mHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, ThreadActivity.class);
                intent.putExtra(INTENT_THREAD_ID, hotList.getId());
                intent.putExtra(INTENT_THREAD_TITLE, hotList.getTitle());
                mContext.startActivity(intent);
            });
        }
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.item_civ_avatar)
        CircleImageView mItemCivAvatar;
        @BindView(R.id.author)
        TextView mAuthor;
        @BindView(R.id.release)
        TextView mRelease;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.create_time)
        TextView mCreateTime;

        ViewHolder(View view) {
            super(view);
        }
    }
}
