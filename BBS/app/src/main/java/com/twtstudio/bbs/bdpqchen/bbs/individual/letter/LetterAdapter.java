package com.twtstudio.bbs.bdpqchen.bbs.individual.letter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by bdpqchen on 17-7-4.
 */

public class LetterAdapter extends BaseAdapter<LetterModel> {

    LetterAdapter(Context context) {
        super(context);
        mContext = context;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_letter_left, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder0, int position) {

        if (mDataSet != null && mDataSet.size() > 0){
            LogUtil.dd("position", String.valueOf(position));
            if (holder0 instanceof ViewHolder){
                ViewHolder holder = (ViewHolder) holder0;
                LetterModel model = mDataSet.get(position);
                LogUtil.dd("model", model.getContent());
                holder.mTvContent.setText(model.getContent());



            }
        }
    }



    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.civ_avatar_letter)
        CircleImageView mCivAvatarLetter;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        ViewHolder(View view) {
            super(view);
        }
    }
}
