package com.twtstudio.bbs.bdpqchen.bbs.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class ForumAdapter extends BaseAdapter<TwoForumModel> implements View.OnClickListener {

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /* public int getItemForumId(int position) {
         return mDataSet.get(position).getId();
     }

     public String getItemForumTitle(int position) {
         return mDataSet.get(position).getName();
     }
 */
    public ForumAdapter(Context context) {
        super(context);
        mContext = context;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_forum, parent, false);
        view.setOnClickListener(this);
        holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        if (mDataSet != null && mDataSet.size() > 0) {
            ViewHolder viewHolder = (ViewHolder) holder;
            TwoForumModel model = mDataSet.get(position);
            String coverUrl1 = RxDoHttpClient.BASE_URL + "forum/" + model.model1.getId() + "/cover";
            String coverUrl2 = RxDoHttpClient.BASE_URL + "forum/" + model.model2.getId() + "/cover";
            viewHolder.mTvName1.setText(model.model1.getName());
            ImageUtil.loadForumCover(mContext, coverUrl1, viewHolder.mIvCover1);
            viewHolder.mTvName2.setText(model.model1.getName());
            ImageUtil.loadForumCover(mContext, coverUrl2, viewHolder.mIvCover2);
//            viewHolder.itemView.setTag(position);

        }

    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_cover_1)
        ImageView mIvCover1;
        @BindView(R.id.tv_name_1)
        TextView mTvName1;
        @BindView(R.id.iv_cover_2)
        ImageView mIvCover2;
        @BindView(R.id.tv_name_2)
        TextView mTvName2;

        ViewHolder(View view) {
            super(view);
        }
    }
}
