package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.retrox.bbcode.BBCodeParse;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bdpqchen on 17-5-23.
 */

public class PostAdapter extends BaseAdapter<ThreadModel.PostBean> {


    private Context mContext;

    public PostAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mDataSet != null && mDataSet.size() > 0) {
            ThreadModel.PostBean p = mDataSet.get(position);
            ViewHolder h = (ViewHolder) holder;
            ImageUtil.loadAvatarByUid(mContext, p.getAuthor_id(), h.mCivAvatarPost);
            h.mTvNicknamePost.setText(p.getAuthor_nickname());
            h.mTvPostDatetime.setText(p.getT_modify());
            String bbCodeParse = BBCodeParse.bbcode2Html(p.getContent());
            h.mTvPostContent.setHtml(bbCodeParse);

        }
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
        ViewHolder(View view) {
            super(view);
        }
    }
}
