package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.retrox.bbcode.BBCodeParse;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bdpqchen on 17-5-23.
 */

public class PostAdapter extends BaseAdapter<ThreadModel.PostBean> {

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_HEADER = 1;

    private Context mContext;
    private ThreadModel.ThreadBean mThreadData = new ThreadModel.ThreadBean();


    public PostAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TYPE_NORMAL) {
            LogUtil.dd("view = normal");
            view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread_post, parent, false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            LogUtil.dd("view = header");
            view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread_thread, parent, false);
            return new HeaderHolder(view);
        }
        view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread_thread, parent, false);
        return new HeaderHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mThreadData != null) {
            LogUtil.dd("thread data is not null");
            if (holder instanceof HeaderHolder) {
                LogUtil.dd("holder instance of header");
                HeaderHolder headerHolder = (HeaderHolder) holder;
                headerHolder.mTvUsernameThread.setText(mThreadData.getAuthor_nickname());
//                headerHolder.mco.setText(mThreadData.getAuthor_nickname());
            }

            if (mDataSet != null && mDataSet.size() > 0) {
                LogUtil.dd("post data is not null");
                if (holder instanceof ViewHolder) {
                    LogUtil.dd("holder instance of normal");
                    ThreadModel.PostBean p = mDataSet.get(position);
                    ViewHolder h = (ViewHolder) holder;
                    ImageUtil.loadAvatarByUid(mContext, p.getAuthor_id(), h.mCivAvatarPost);
                    h.mTvNicknamePost.setText(p.getAuthor_nickname());
                    h.mTvPostDatetime.setText(StampUtil.getDatetimeByStamp(p.getT_create()));
                    h.mTvFloorPost.setText(p.getFloor() + "楼");

                    String htmlStr = BBCodeParse.bbcode2Html(p.getContent());
                    /*NaiveHtmlUtils.GetHtmlImageSrcList(htmlStr).forEach(imgUrl ->{
                        LogUtil.dd("image url ", imgUrl);
                    } );*/

                    LogUtil.dd(htmlStr);
                    h.mTvPostContent.setHtml(htmlStr, new HtmlHttpImageGetter(h.mTvPostContent));

                    // TODO: 17-5-23 是否收藏的判定
                }
            }

        }

    }

    @Override
    public int getItemViewType(int position) {

        LogUtil.dd("position", String.valueOf(position));
        if (position > 0) {
            return TYPE_NORMAL;
        } else {
            return TYPE_HEADER;
        }
    }

    /*@Override
    public int getItemCount() {
        return mDataSet.size();
    }
*/
    public void setThreadData(ThreadModel.ThreadBean threadData) {
        mThreadData = threadData;
        notifyDataSetChanged();
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.civ_avatar_post)
        CircleImageView mCivAvatarPost;
        @BindView(R.id.tv_nickname_post)
        TextView mTvNicknamePost;
        @BindView(R.id.tv_post_datetime)
        TextView mTvPostDatetime;
        @BindView(R.id.tv_post_content)
        HtmlTextView mTvPostContent;
        @BindView(R.id.tv_floor_post)
        TextView mTvFloorPost;

        ViewHolder(View view) {
            super(view);
        }
    }

    static class HeaderHolder extends BaseViewHolder {
        @BindView(R.id.civ_avatar_thread)
        CircleImageView mCivAvatarThread;
        @BindView(R.id.tv_username_thread)
        TextView mTvUsernameThread;
        @BindView(R.id.tv_level_thread)
        TextView mTvLevelThread;
        @BindView(R.id.tv_datetime_thread)
        TextView mTvDatetimeThread;
//        @BindView(R.id.iv_star_thread)
//        ImageView mIvStarThread;
        @BindView(R.id.iv_stared_thread)
        ImageView mIvStaredThread;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.htv_content)
        HtmlTextView mHtvContent;

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }


}
