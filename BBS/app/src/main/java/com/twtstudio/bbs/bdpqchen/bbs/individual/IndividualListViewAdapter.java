package com.twtstudio.bbs.bdpqchen.bbs.individual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdpqchen on 17-5-4.
 */

public class IndividualListViewAdapter extends BaseAdapter{

    private Context mContext;

    private List<IndividualListModel> mDataSets = new ArrayList<>();

    public IndividualListViewAdapter(Context context, List<IndividualListModel> list){
        mContext = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        IndividualListModel model = (IndividualListModel) getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_view_individual, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.individual_item_tv_title);
        textView.setText(model.title);
//        ImageView imageView = (ImageView) convertView.findViewById(R.id.individual_item_iv_icon_end);
        ImageView imageViewStart = (ImageView) convertView.findViewById(R.id.individual_item_iv_icon_start);
        imageViewStart.setImageDrawable(ResourceUtil.getDrawable(mContext, model.iconStart));
//        ImageUtil.load(mContext, model.iconStart, imageViewStart);
//        ImageUtil.load(mContext, R.drawable.ic_keyboard_arrow_right_black_24dp, imageView);
        if (model.hasTag){
            TextView tvTitle = (TextView) convertView.findViewById(R.id.individual_item_tv_tag);
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(model.tagNum + "");
        }

        View line = convertView.findViewById(R.id.individual_item_v_line);
        if (position == getCount() - 1){
            line.setVisibility(View.GONE);
        }else{
            line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }


}
