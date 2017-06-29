package com.twtstudio.bbs.bdpqchen.bbs.individual.star;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ANONYMOUS_NAME;

/**
 * Created by bdpqchen on 17-6-29.
 */

public class StarAdapter extends BaseAdapter<StarModel> {

    StarPresenter mStarPresenter;

    public StarAdapter(Context context, StarPresenter presenter) {
        super(context);
        mContext = context;
        mStarPresenter = presenter;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_star, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder0, int position) {
        if (mDataSet != null && mDataSet.size() > 0){
            if (holder0 instanceof ViewHolder){
                ViewHolder holder = (ViewHolder) holder0;
                StarModel model = mDataSet.get(position);
                if (model.getAnonymous() == 1){
                    model.setAuthor_name(ANONYMOUS_NAME);
                    holder.mTvStarName.setText(model.getAuthor_name());
                    ImageUtil.loadAnonAvatar(mContext, holder.mCivStarAvatar);
                }else{
                    ImageUtil.loadAvatarAsBitmapByUidWithLeft(mContext, model.getAuthor_id(), holder.mCivStarAvatar);
                    holder.mTvStarName.setText(TextUtil.getTwoNames(model.getAuthor_name(), model.getAuthor_nickname()));
                }
                holder.mTvStarCreateTime.setText(StampUtil.getDatetimeByStamp(model.getT_create()));
                holder.mTvStarTitle.setText(model.getTitle());
                ImageUtil.loadIconAsBitmap(mContext, R.drawable.ic_star_yellow_24dp, holder.mIvStar);
                holder.mIvStar.setOnClickListener(v -> {
                    mStarPresenter.unStarThread(model.getId());
                    holder.mIvUnStar.setVisibility(View.GONE);
                    holder.mIvStar.setVisibility(View.VISIBLE);
                });
                holder.mIvUnStar.setOnClickListener(v -> {
                    mStarPresenter.starThread(model.getId());
                    holder.mIvUnStar.setVisibility(View.GONE);
                    holder.mIvStar.setVisibility(View.VISIBLE);
                });

            }

        }


    }

    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.civ_star_avatar)
        CircleImageView mCivStarAvatar;
        @BindView(R.id.tv_star_name)
        TextView mTvStarName;
        @BindView(R.id.iv_star)
        ImageView mIvStar;
        @BindView(R.id.iv_un_star)
        ImageView mIvUnStar;
        @BindView(R.id.tv_star_title)
        TextView mTvStarTitle;
        @BindView(R.id.tv_star_create_time)
        TextView mTvStarCreateTime;
        ViewHolder(View view) {
            super(view);
        }
    }
}
