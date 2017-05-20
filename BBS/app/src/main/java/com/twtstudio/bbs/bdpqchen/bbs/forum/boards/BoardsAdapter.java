package com.twtstudio.bbs.bdpqchen.bbs.forum.boards;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadListModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListActivity;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity.INTENT_BOARD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity.INTENT_BOARD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity.INTENT_THREAD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity.INTENT_THREAD_TITLE;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class BoardsAdapter extends BaseAdapter<PreviewThreadModel> implements View.OnClickListener {

    private static final String AUTHOR_PRE = "作者：";

    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public int getItemBoardId(int position) {
        return mDataSet.get(position).getBoard().getId();
    }

    public BoardsAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_board_thread, parent, false);
//        view.setOnClickListener(this);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, int position) {

        if (mDataSet != null && mDataSet.size() > 0) {
            ViewHolder holder = (ViewHolder) viewHolder;
            PreviewThreadModel previewThread = mDataSet.get(position);
            ThreadListModel.ThreadBean thread1 = previewThread.getThreadList().get(0);
            ThreadListModel.ThreadBean thread2 = previewThread.getThreadList().get(1);
            holder.mTvPreviewBoardTitle.setText(previewThread.getBoard().getName());
            holder.mTvPreviewThreadTitle1.setText(thread1.getTitle());
            holder.mTvPreviewThreadTitle2.setText(thread2.getTitle());
            holder.mTvPreviewThreadAuthor1.setText(AUTHOR_PRE + thread1.getAuthor_nickname());
            holder.mTvPreviewThreadAuthor2.setText(AUTHOR_PRE + thread2.getAuthor_nickname());
            holder.mTvPreviewThreadTime1.setText(StampUtil.getDatetimeByStamp(thread1.getT_create()));
            holder.mTvPreviewThreadTime2.setText(StampUtil.getDatetimeByStamp(thread2.getT_create()));
            holder.mTvPreviewThreadContent1.setText(thread1.getContent());
            holder.mTvPreviewThreadContent2.setText(thread2.getContent());

            holder.mRlBoardTitle.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, ThreadListActivity.class);
                LogUtil.dd(previewThread.getBoard().getName());
                LogUtil.dd(String.valueOf(previewThread.getBoard().getId()));
                intent.putExtra(INTENT_BOARD_TITLE, previewThread.getBoard().getName());
                intent.putExtra(BoardsActivity.INTENT_BOARD_ID, previewThread.getBoard().getId());
                mContext.startActivity(intent);
            });
            Intent intent = new Intent(mContext, ThreadActivity.class);
            holder.mLlBoardContainedThread1.setOnClickListener(v -> {
                intent.putExtra(INTENT_THREAD_ID, thread1.getId());
                intent.putExtra(INTENT_THREAD_TITLE, thread1.getTitle());
                mContext.startActivity(intent);
            });
            holder.mLlBoardContainedThread2.setOnClickListener(v -> {
                intent.putExtra(INTENT_THREAD_ID, thread1.getId());
                intent.putExtra(INTENT_THREAD_TITLE, thread2.getTitle());
                mContext.startActivity(intent);
            });

        }

    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.view_tag)
        View mViewTag;
        @BindView(R.id.rl_board_title)
        RelativeLayout mRlBoardTitle;
        @BindView(R.id.iv_content_1)
        ImageView mIvContent1;
        @BindView(R.id.ll_board_contained_thread_1)
        LinearLayout mLlBoardContainedThread1;
        @BindView(R.id.iv_content_2)
        ImageView mIvContent2;
        @BindView(R.id.ll_board_contained_thread_2)
        LinearLayout mLlBoardContainedThread2;
        @BindView(R.id.tv_preview_board_title)
        TextView mTvPreviewBoardTitle;
        @BindView(R.id.tv_preview_thread_title_1)
        TextView mTvPreviewThreadTitle1;
        @BindView(R.id.tv_preview_thread_content_1)
        TextView mTvPreviewThreadContent1;
        @BindView(R.id.tv_preview_thread_time_1)
        TextView mTvPreviewThreadTime1;
        @BindView(R.id.tv_preview_thread_author_1)
        TextView mTvPreviewThreadAuthor1;
        @BindView(R.id.tv_preview_thread_title_2)
        TextView mTvPreviewThreadTitle2;
        @BindView(R.id.tv_preview_thread_content_2)
        TextView mTvPreviewThreadContent2;
        @BindView(R.id.tv_preview_thread_time_2)
        TextView mTvPreviewThreadTime2;
        @BindView(R.id.tv_preview_thread_author_2)
        TextView mTvPreviewThreadAuthor2;

        ViewHolder(View view) {
            super(view);
        }
    }
}
