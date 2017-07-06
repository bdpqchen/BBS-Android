package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseFooterViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_FOOTER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_NORMAL;

/**
 * Created by bdpqchen on 17-5-20.
 */

public class ThreadListAdapter extends BaseAdapter<ThreadListModel.ThreadBean> {

    private String mBoardTitle = "";

    public ThreadListAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_NORMAL) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread_list, parent, false);
            return new ViewHolder(view);
        } else if (viewType == ITEM_FOOTER) {
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
//                ThreadListModel.BoardBean board = mDataSet.get(position);
                ViewHolder holder = (ViewHolder) viewHolder;
                if (thread.getAnonymous() == 1){
                    thread.setAuthor_name("匿名用户");
                    ImageUtil.loadIconAsBitmap(mContext, R.drawable.avatar_anonymous_left, holder.mCivThreadAvatar);
                }else{
                    holder.mCivThreadAvatar.setOnClickListener(v -> {
                        mContext.startActivity(IntentUtil.toPeople(mContext, thread.getAuthor_id()));
                    });
                    ImageUtil.loadAvatarAsBitmapByUid(mContext, thread.getAuthor_id(), holder.mCivThreadAvatar);
                }
                holder.mTvThreadNickname.setText(thread.getAuthor_name());
                holder.mTvThreadContent.setText(thread.getContent());
                holder.mTvThreadDate.setText(StampUtil.getDatetimeByStamp(thread.getT_create()));
                holder.mTvThreadTitle.setText(thread.getTitle());
                holder.mTvThreadPostCount.setText(thread.getC_post() + " 回复");
                holder.itemView.setOnClickListener(v -> {
                    mContext.startActivity(IntentUtil.toThread(mContext, thread.getId(), thread.getTitle(), 0,
                            thread.getBoard_id(),mBoardTitle));
                });
            } else if (viewHolder instanceof BaseFooterViewHolder) {

            }
        }
    }

    void addDataList(List<ThreadListModel.ThreadBean> thread) {
        mDataSet.addAll(thread);
        notifyDataSetChanged();
    }

    void setBoardTitle(String s){
        mBoardTitle = s;
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
