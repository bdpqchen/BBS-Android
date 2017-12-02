package com.twtstudio.bbs.bdpqchen.bbs.forum.boards;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_CAN_ANON;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_NORMAL;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_SIMPLE;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class BoardsAdapter extends RecyclerView.Adapter {

    private static final String AUTHOR_PRE = "作者：";
    private final boolean isSimple = PrefUtil.isSimpleBoardList();
    private BoardsModel mBoards = new BoardsModel();
    private Context mContext;
    private List<PreviewThreadModel> mDataSet = new ArrayList<>();

    public BoardsAdapter(Context context, List<PreviewThreadModel> previewThreadModel) {
        this.mContext = context;
        mDataSet = previewThreadModel;
    }

    public BoardsAdapter(Context context, BoardsModel boardsModel) {
        this.mContext = context;
        mBoards = boardsModel;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_NORMAL) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_board_thread, parent, false);
            return new ViewHolder(view);
        } else if (viewType == ITEM_SIMPLE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_simple_board_list, parent, false);
            return new SimpleViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (isSimple) {
            return ITEM_SIMPLE;
        } else {
            return ITEM_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        if (isSimple){
            if (mBoards != null && mBoards.getBoards() != null){
                return mBoards.getBoards().size();
            }
            return 0;
        }else{
            if(mDataSet != null){
                return mDataSet.size();
            }
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        LogUtil.dd("onBindViewHolder BoardList", String.valueOf(position));
        if (isSimple) {
            if (mBoards != null && mBoards.getBoards().size() > 0) {
                BoardsModel.BoardsBean board = mBoards.getBoards().get(position);
                if (viewHolder instanceof SimpleViewHolder) {
                    SimpleViewHolder holder = (SimpleViewHolder) viewHolder;
                    holder.mTvPreviewBoardTitle.setText(board.getName());
                    holder.itemView.setOnClickListener(v -> {
                        Intent intent = new Intent(mContext, ThreadListActivity.class);
//                        LogUtil.dd("boardname_in adapter", board.getName());
                        intent.putExtra(INTENT_BOARD_ID, board.getId());
                        intent.putExtra(INTENT_BOARD_TITLE, board.getName());
                        intent.putExtra(INTENT_BOARD_CAN_ANON, board.getAnonymous());
                        mContext.startActivity(intent);
                    });
                }
            }
        } else {
            if (mDataSet != null && mDataSet.size() > 0) {
                if (viewHolder instanceof ViewHolder) {
                    final ViewHolder holder = (ViewHolder) viewHolder;
                    PreviewThreadModel previewThread = mDataSet.get(position);
                    holder.mTvPreviewBoardTitle.setText(previewThread.getBoard().getName());
                    holder.mRlBoardTitle.setOnClickListener(v -> {
                        Intent intent = new Intent(mContext, ThreadListActivity.class);
                        LogUtil.dd("board name_in adapter", previewThread.getBoard().getName());
                        intent.putExtra(INTENT_BOARD_ID, previewThread.getBoard().getId());
                        intent.putExtra(INTENT_BOARD_TITLE, previewThread.getBoard().getName());
                        mContext.startActivity(intent);
                    });
                    holder.mLlThreadList.setVisibility(View.VISIBLE);
                    if (mDataSet.get(position).getThreadList() != null) {
                        Intent intent = new Intent(mContext, ThreadActivity.class);
                        intent.putExtra(INTENT_BOARD_ID, previewThread.getBoard().getId());
                        intent.putExtra(INTENT_BOARD_TITLE, previewThread.getBoard().getName());
                        int threadSize = mDataSet.get(position).getThreadList().size();
                        if (threadSize == 0) {
                            holder.mLlThreadList.setVisibility(View.GONE);
                        }
                        if (threadSize >= 1) {
                            ThreadListModel.ThreadBean thread1 = previewThread.getThreadList().get(0);
//                    LogUtil.dd("on create view 1", thread1.getAuthor_name());
                            holder.mTvPreviewThreadTitle1.setText(thread1.getTitle());
                            holder.mTvPreviewThreadAuthor1.setText(AUTHOR_PRE + thread1.getAuthor_name());
                            holder.mTvPreviewThreadTime1.setText(StampUtil.getDatetimeByStamp(thread1.getT_create()));
                            holder.mTvPreviewThreadContent1.setText(thread1.getContent());
                            holder.mLlBoardContainedThread1.setOnClickListener(v -> {
                                intent.putExtra(INTENT_THREAD_ID, thread1.getId());
                                intent.putExtra(INTENT_THREAD_TITLE, thread1.getTitle());
                                mContext.startActivity(intent);
                            });
                        }
                        if (threadSize == 1) {
                            holder.mLine.setVisibility(View.GONE);
                            holder.mLlBoardContainedThread2.setVisibility(View.GONE);
                        } else if (threadSize == 2) {
                            ThreadListModel.ThreadBean thread2 = previewThread.getThreadList().get(1);
//                    LogUtil.dd("on create view 2", thread2.getAuthor_name());
                            holder.mTvPreviewThreadTitle2.setText(thread2.getTitle());
                            holder.mTvPreviewThreadAuthor2.setText(AUTHOR_PRE + thread2.getAuthor_name());
                            holder.mTvPreviewThreadTime2.setText(StampUtil.getDatetimeByStamp(thread2.getT_create()));
                            holder.mTvPreviewThreadContent2.setText(thread2.getContent());
                            holder.mLlBoardContainedThread2.setOnClickListener(v -> {
                                intent.putExtra(INTENT_THREAD_ID, thread2.getId());
                                intent.putExtra(INTENT_THREAD_TITLE, thread2.getTitle());
                                mContext.startActivity(intent);
                            });
                        }
                    } else {
//                LogUtil.dd("this is empty", String.valueOf(position));
                        holder.mLlThreadList.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    public void clearAll() {
        mDataSet.clear();
        notifyDataSetChanged();
    }

    public void addData(PreviewThreadModel previewThreadList) {
        mDataSet.add(previewThreadList);
        notifyDataSetChanged();
    }

    public void addSimpleData(BoardsModel model) {
        if (mBoards != null){
            mBoards.setBoards(model.getBoards());
            notifyDataSetChanged();
        }
    }

    public void clearAllSimple() {
        if (mBoards != null && mBoards.getBoards() != null){
            mBoards.getBoards().clear();
            notifyDataSetChanged();
        }
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_tag)
        View mViewTag;
        @BindView(R.id.tv_preview_board_title)
        TextView mTvPreviewBoardTitle;
        @BindView(R.id.rl_board_title)
        RelativeLayout mRlBoardTitle;

        SimpleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_tag)
        View mViewTag;
        @BindView(R.id.rl_board_title)
        RelativeLayout mRlBoardTitle;
        @BindView(R.id.ll_board_contained_thread_1)
        LinearLayout mLlBoardContainedThread1;
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
        @BindView(R.id.ll_thread_list)
        LinearLayout mLlThreadList;
        @BindView(R.id.divider_line)
        View mLine;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }


}
