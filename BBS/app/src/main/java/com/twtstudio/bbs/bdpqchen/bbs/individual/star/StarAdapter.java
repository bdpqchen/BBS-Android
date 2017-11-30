package com.twtstudio.bbs.bdpqchen.bbs.individual.star;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;
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
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_star, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder0, int position) {
        if (mDataSet != null && mDataSet.size() > 0) {
            if (holder0 instanceof ViewHolder) {
                ViewHolder holder = (ViewHolder) holder0;
                StarModel model = mDataSet.get(position);
                if (model.getAnonymous() == 1) {
                    model.setAuthor_name(ANONYMOUS_NAME);
                    model.setAuthor_id(0);
                    holder.mTvStarName.setText(model.getAuthor_name());
                } else {
                    holder.mTvStarName.setText(TextUtil.getTwoNames(model.getAuthor_name(), model.getAuthor_nickname()));
                }
                ImageUtil.loadAvatarButAnon(mContext, model.getAuthor_id(), holder.mCivStarAvatar);
                holder.mTvStarCreateTime.setText(StampUtil.getDatetimeByStamp(model.getT_create()));
                holder.mTvStarTitle.setText(model.getTitle());

                if (model.getIn_collection() == 0) {
                    holder.mIvStar.setVisibility(View.VISIBLE);
                    holder.mIvUnStar.setVisibility(View.GONE);
//                    ImageUtil.loadIconAsBitmap(mContext, R.drawable.ic_star_yellow_24dp, holder.mIvStar);
                    holder.mIvStar.setOnClickListener(v -> {
                        mStarPresenter.unStarThread(model.getId(), position);
                    });
                } else {
                    holder.mIvUnStar.setVisibility(View.VISIBLE);
                    holder.mIvStar.setVisibility(View.GONE);
//                    ImageUtil.loadIconAsBitmap(mContext, R.drawable.ic_star_border_yellow_24dp, holder.mIvUnStar);
                    holder.mIvUnStar.setOnClickListener(v -> {
                        mStarPresenter.starThread(model.getId(), position);
                    });
                }
                holder.itemView.setOnClickListener(v -> {
                    mContext.startActivity(IntentUtil.toThread(mContext, model.getId(), model.getTitle(), 0,
                            model.getBoard_id(), model.getBoard_name()));
                });
                holder.mCivStarAvatar.setOnClickListener(v -> {
                    mContext.startActivity(IntentUtil.toPeople(mContext, model.getAuthor_id()));
                });
            }
        }
    }

    void updateStatus(int position, int status) {
        mDataSet.get(position).setIn_collection(status);
    }

    static class ViewHolder extends BaseViewHolder {
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
