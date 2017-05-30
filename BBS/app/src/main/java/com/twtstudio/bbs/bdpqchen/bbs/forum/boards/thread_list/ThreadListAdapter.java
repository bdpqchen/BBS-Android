package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFooterViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bdpqchen on 17-5-20.
 */

public class ThreadListAdapter extends BaseAdapter<ThreadListModel.ThreadBean> {

    private int mPage = 0;

    public ThreadListAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == T_NORMAL) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread_list, parent, false);
            return new ViewHolder(view);
        } else if (viewType == T_FOOTER) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread_list, parent, false);
            return new BaseFooterViewHolder(view);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        if (mDataSet != null && mDataSet.size() > 0) {
            if (viewHolder instanceof ViewHolder) {
                ThreadListModel.ThreadBean thread = mDataSet.get(position);
                ViewHolder holder = (ViewHolder) viewHolder;
                holder.mTvThreadNickname.setText(thread.getAuthor_name());
                holder.mTvThreadContent.setText(thread.getContent());
                holder.mTvThreadDate.setText(StampUtil.getDateByStamp(thread.getT_create()));
                holder.mTvThreadTitle.setText(thread.getTitle());
                holder.mTvThreadPostCount.setText(thread.getC_post() + " 回复");
                holder.itemView.setOnClickListener(v -> {
                    Intent in = new Intent(mContext, ThreadActivity.class);
                    in.putExtra(Constants.INTENT_THREAD_ID, thread.getId());
                    in.putExtra(Constants.INTENT_THREAD_TITLE, thread.getTitle());
                    mContext.startActivity(in);
                });
                ImageUtil.loadAvatarByUid(mContext, thread.getAuthor_id(), holder.mCivThreadAvatar);
            } else if (viewHolder instanceof BaseFooterViewHolder) {

            }
        }
    }

    void addDataList(List<ThreadListModel.ThreadBean> thread, int page) {
        mPage = page;
        mDataSet.addAll(thread);
        notifyDataSetChanged();
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.civ_thread_avatar)
        CircleImageView mCivThreadAvatar;
        @BindView(R.id.tv_thread_nickname)
        TextView mTvThreadNickname;
        @BindView(R.id.ll_thread_user)
        LinearLayout mLlThreadUser;
        @BindView(R.id.tv_thread_title)
        TextView mTvThreadTitle;
        @BindView(R.id.tv_thread_content)
        TextView mTvThreadContent;
        @BindView(R.id.tv_thread_post_count)
        TextView mTvThreadPostCount;
        @BindView(R.id.tv_thread_date)
        TextView mTvThreadDate;

        ViewHolder(View view) {
            super(view);
        }
    }
}
