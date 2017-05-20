package com.twtstudio.bbs.bdpqchen.bbs.main.topTen;

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

public class TopTenAdapter extends BaseAdapter<TopTenModel.DataBean.HotBean> {
    public TopTenAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       TopTenViewHolder holder = new TopTenViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.activity_latest_post_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TopTenViewHolder mholder=(TopTenViewHolder) holder;
        mholder.announceBean= mDataSet.get(position);
        mholder.title.setText(mholder.announceBean.getTitle());
        //mholder.content.setText(mholder.announceBean.getContent());
    }

    class TopTenViewHolder extends BaseViewHolder{
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.author)
        TextView content;
        TopTenModel.DataBean.HotBean announceBean;
        public TopTenViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
