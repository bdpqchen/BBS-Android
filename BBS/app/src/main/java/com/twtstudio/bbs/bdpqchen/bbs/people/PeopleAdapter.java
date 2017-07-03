package com.twtstudio.bbs.bdpqchen.bbs.people;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-6-29.
 */

public class PeopleAdapter extends BaseAdapter<PeopleModel.RecentBean> {

    public PeopleAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_people_recent, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder0, int position) {
        if (mDataSet != null && mDataSet.size() > 0) {

            if (holder0 instanceof ViewHolder) {
                ViewHolder holder = (ViewHolder) holder0;
                PeopleModel.RecentBean model = mDataSet.get(position);
//                holder.mtvCreate.setText(StampUtil.getDatetimeByStamp(model.getT_create()));
                holder.mtvTitle.setText(model.getTitle());
                holder.itemView.setOnClickListener(v -> {
                    mContext.startActivity(IntentUtil.toThread(mContext, model.getId(), model.getTitle(), 0));
                });

            }
        }
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_people_recent_create)
        TextView mtvCreate;
        @BindView(R.id.tv_people_recent_title)
        TextView mtvTitle;
        ViewHolder(View view) {
            super(view);
        }
    }
}
