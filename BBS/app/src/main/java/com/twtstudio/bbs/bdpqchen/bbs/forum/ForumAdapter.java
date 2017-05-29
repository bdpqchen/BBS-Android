package com.twtstudio.bbs.bdpqchen.bbs.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class ForumAdapter extends BaseAdapter<ForumModel> implements View.OnClickListener {

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public int getItemForumId(int position){
        return mDataSet.get(position).getId();
    }

    public String getItemForumTitle(int position){
        return mDataSet.get(position).getName();
    }

    public ForumAdapter(Context context)
    {
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

//        LogUtil.dd("onBindViewHolder", String.valueOf(position));
//        LogUtil.dd("list.size", String.valueOf(mDataSet.size()));
        if (mDataSet != null && mDataSet.size() > 0){
            ForumModel model = mDataSet.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            LogUtil.dd(model.getName());
            viewHolder.mTvName.setText(model.getName());
            String coverUrl = RxDoHttpClient.BASE_URL + "forum/" + model.getId() + "/cover";
//            String coverUrl = "http://bbs.twtstudio.com/api/" + "forum/" + model.getId() + "/cover";
            ImageUtil.loadForumCover(mContext, coverUrl, viewHolder.mIvBgImage);
            /*HandlerUtil.postDelay(()->{

            }, position * 0);
*/
            viewHolder.itemView.setTag(position);

        }

    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_bg_image)
        ImageView mIvBgImage;
        @BindView(R.id.tv_name)
        TextView mTvName;

        ViewHolder(View view) {
            super(view);
        }
    }
}
