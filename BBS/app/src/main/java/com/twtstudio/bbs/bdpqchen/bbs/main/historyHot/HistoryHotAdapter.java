package com.twtstudio.bbs.bdpqchen.bbs.main.historyHot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangyulong on 5/13/17.
 */

public class HistoryHotAdapter extends BaseAdapter<HistoryHotModel.DataBean.LatestBean> {
    public HistoryHotAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       HistoryHotViewHolder holder = new HistoryHotViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.activity_latest_post_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        HistoryHotViewHolder mholder=(HistoryHotViewHolder) holder;
        mholder.announceBean= mDataSet.get(position);
        mholder.title.setText(mholder.announceBean.getTitle());
        mholder.content.setText(mholder.announceBean.getAuthor_nickname());
    }

    class HistoryHotViewHolder extends BaseViewHolder{
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.author)
        TextView content;
        HistoryHotModel.DataBean.LatestBean announceBean;
        public HistoryHotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
