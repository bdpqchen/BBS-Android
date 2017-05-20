package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.listener.OnItemClickListener;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bdpqchen on 17-5-20.
 */

public class ThreadListAdapter extends BaseAdapter<ThreadListModel.ThreadBean> implements View.OnClickListener {

    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public ThreadListAdapter(Context context) {
        super(context);
        mContext = context;

    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread_list, parent, false);
        view.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        if (mDataSet != null && mDataSet.size() > 0) {
            ThreadListModel.ThreadBean thread = mDataSet.get(position);
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.mTvThreadNickname.setText(thread.getAuthor_nickname());
            holder.mTvThreadContent.setText(thread.getContent());
            holder.mTvThreadDate.setText(StampUtil.getDateByStamp(thread.getT_create()));
            holder.mTvThreadTitle.setText(thread.getTitle());
            holder.mTvThreadPostCount.setText(thread.getC_post() + " 回复");
            ImageUtil.loadAvatarByUid(mContext, thread.getAuthor_id(), holder.mCivThreadAvatar);
        }
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
