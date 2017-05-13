package com.twtstudio.bbs.bdpqchen.bbs.individual;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;
import com.twtstudio.bbs.bdpqchen.bbs.home.HomeActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualListModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.updateInfo.UpdateInfoActivity;
import com.twtstudio.bbs.bdpqchen.bbs.test.MyReleaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bdpqchen on 17-5-4.
 */

public class IndividualListViewAdapter extends BaseAdapter {

    private Context mContext;
    private Activity mActivity;

    private List<IndividualListModel> mDataSets = new ArrayList<>();

    public IndividualListViewAdapter(Activity activity, List<IndividualListModel> list) {
        mContext = activity;
        this.mActivity = activity;
        mDataSets = list;

    }


    @Override
    public int getCount() {
        return mDataSets.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder holder;
        IndividualListModel item = (IndividualListModel) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ll_individual, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mIndividualItemTvTitle.setText(item.title);
        holder.mIndividualItemIvIconStart.setImageDrawable(ResourceUtil.getDrawable(mContext, item.iconStart));

//        ImageUtil.load(mContext, item.iconStart, imageViewStart);
//        ImageUtil.load(mContext, R.drawable.ic_keyboard_arrow_right_black_24dp, imageView);
        if (item.hasTag) {
            holder.mIndividualItemTvTag.setVisibility(View.VISIBLE);
            holder.mIndividualItemTvTag.setText(item.tagNum + "");
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 2:
                        mActivity.startActivity(new Intent(mContext, MyReleaseActivity.class));
                        break;
                    case 3:
                        mActivity.startActivityForResult(new Intent(mContext, UpdateInfoActivity.class), HomeActivity.CODE_RESULT_FOR_UPDATE_INFO);
                        break;
                }
            }
        });

        if (position == getCount() - 1) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.individual_item_iv_icon_start)
        ImageView mIndividualItemIvIconStart;
        @BindView(R.id.individual_item_tv_title)
        TextView mIndividualItemTvTitle;
        @BindView(R.id.individual_item_tv_tag)
        TextView mIndividualItemTvTag;
        @BindView(R.id.individual_item_iv_icon_end)
        ImageView mIndividualItemIvIconEnd;
        @BindView(R.id.individual_item_v_line)
        View line;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
