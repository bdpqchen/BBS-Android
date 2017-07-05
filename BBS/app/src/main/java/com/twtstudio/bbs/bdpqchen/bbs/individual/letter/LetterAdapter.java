package com.twtstudio.bbs.bdpqchen.bbs.individual.letter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseEndViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseHeaderViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_EMPTY;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_END;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_HEADER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_LEFT;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_RIGHT;


/**
 * Created by bdpqchen on 17-7-4.
 */

public class LetterAdapter extends BaseAdapter<LetterModel> {

    private static final int myUid = PrefUtil.getAuthUid();
    private boolean isTopping = false;
    private boolean isNotEnoughOnePage = false;
    LetterAdapter(Context context) {
        super(context);
        mContext = context;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LogUtil.dd("viewtypeis", String.valueOf(viewType));
        if (viewType == ITEM_LEFT) {
            return new LeftView(LayoutInflater.from(mContext).inflate(R.layout.item_letter_left, parent, false));
        } else if (viewType == ITEM_RIGHT) {
            return new RightView(LayoutInflater.from(mContext).inflate(R.layout.item_letter_right, parent, false));
        } else if (viewType == ITEM_HEADER) {
            return new BaseHeaderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_common_header, parent, false));
        }else if (viewType == ITEM_END){
            return new BaseEndViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_letter_end, parent, false));
        }else if (viewType == ITEM_EMPTY){
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_common_empty, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder0, int position) {

        if (mDataSet != null && mDataSet.size() > 0) {
//            LogUtil.dd("position", String.valueOf(position));
            if (holder0 instanceof LeftView) {
                LetterModel model = mDataSet.get(position);
                LeftView holder = (LeftView) holder0;
                ImageUtil.loadAvatarAsBitmapByUidWithLeft(mContext, model.getAuthor_id(), holder.mCivAvatarLetter);
//                LogUtil.dd("model", model.getContent());
                holder.mTvContent.setText(model.getContent());

            }else if (holder0 instanceof RightView){
                RightView holder = (RightView) holder0;
                LetterModel model = mDataSet.get(position);
                ImageUtil.loadAvatarAsBitmapByUidWithRight(mContext, model.getAuthor_id(), holder.mCivAvatarLetter);
                holder.mTvContent.setText(model.getContent());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (position + 1 == getItemCount() && mPage > 0) {
        if (position + 1 == getItemCount()) {
//            LogUtil.dd("top, page", isTopping + "|" + mPage);
            if (isNotEnoughOnePage){
                return ITEM_EMPTY;
            }
            if (isTopping){
                return ITEM_END;
            }
            return ITEM_HEADER;
        }
        if (mDataSet.get(position).getAuthor_id() == myUid) {
            return ITEM_RIGHT;
        } else {
            return ITEM_LEFT;
        }

    }


    public void setTopping(boolean topping) {
        isTopping = topping;
        notifyDataSetChanged();
    }
    public void setNotEnoughOnePage(boolean enough) {
        isNotEnoughOnePage = enough;
        notifyDataSetChanged();
    }

    public void setPage(int page){
        mPage = page;
        notifyDataSetChanged();
    }

    static class LeftView extends BaseViewHolder {
        @BindView(R.id.civ_avatar_letter)
        CircleImageView mCivAvatarLetter;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        LeftView(View view) {
            super(view);
        }
    }

    static class RightView extends BaseViewHolder {
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.civ_avatar_letter)
        CircleImageView mCivAvatarLetter;

        RightView(View view) {
            super(view);
        }
    }
}
