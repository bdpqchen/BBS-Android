package com.twtstudio.bbs.bdpqchen.bbs.forum.boards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumAdapter;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class BoardsAdapter extends BaseAdapter<ThreadModel> implements View.OnClickListener {

    private ForumAdapter.OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {

    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(ForumAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public int getItemForumId(int position) {
//        return mDataSet.get(position).getId();
        return 1;
    }


    public BoardsAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread, parent, false);
        view.setOnClickListener(this);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.iv_content)
        ImageView mIvContent;
        @BindView(R.id.iv_content_1)
        ImageView mIvContent1;

        ViewHolder(View view) {
            super(view);
        }
    }
}
