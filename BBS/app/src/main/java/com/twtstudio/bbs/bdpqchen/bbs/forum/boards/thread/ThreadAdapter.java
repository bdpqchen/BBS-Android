package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-12.
 */

public class ThreadAdapter extends BaseAdapter<ThreadModel> {


    public ThreadAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread, parent, false);
//        view.setOnClickListener(this);
        holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_content)
        ImageView mIvContent;

        ViewHolder(View view) {
            super(view);
        }
    }
}
