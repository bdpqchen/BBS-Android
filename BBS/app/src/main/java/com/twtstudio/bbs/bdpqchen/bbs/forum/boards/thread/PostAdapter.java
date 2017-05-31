package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFooterViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.listener.OnItemClickListener;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.retrox.bbcode.BBCodeParse;

import org.sufficientlysecure.htmltextview.GlideImageGeter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bdpqchen on 17-5-23.
 */

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int TYPE_POST = 0;
    private static final int TYPE_THREAD = 1;
    private static final int TYPE_FOOTER = 2;


    private Context mContext;
    private ThreadModel.ThreadBean mThreadData = new ThreadModel.ThreadBean();

    private List<ThreadModel.PostBean> mPostData = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener = null;
    private int onePage = 50;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public int getPostId(int position) {
        return mPostData.get(position - 1).getId();
    }

    public String comment2reply(int postPosition, String content) {
        //帖主不算
        postPosition--;
        if (postPosition >= 0) {
            ThreadModel.PostBean post = mPostData.get(postPosition);
            content = "[quote]引用 #"
                    + post.getFloor() + " "
                    + getAuthorName(false, postPosition) + "的评论：\n"
                    + cutRedundancy(post.getContent())
                    + "[/quote]\n"
                    + content + "\n";
        }
        return content;
    }

    private String getAuthorName(boolean is1floor, int position) {
        int uid = 0;
        if (mPostData != null && mPostData.size() > 0) {
            uid = mPostData.get(position).getAuthor_id();
        }
        if (is1floor) {
            uid = mThreadData.getAuthor_id();
        }
        if (uid == 0) {
            return "匿名用户";
        } else {
            if (is1floor) {
                return mThreadData.getAuthor_name();
            } else {
                return mPostData.get(position).getAuthor_name();
            }
        }
    }

    String getDynamicHint(int postPosition) {
        String hint;
        if (postPosition == 0) {
            hint = "评论帖主 " + getAuthorName(true, 0);
        } else {
            int p = postPosition - 1;
            ThreadModel.PostBean post = mPostData.get(p);
            hint = "回复 " + post.getFloor() + "楼 " + getAuthorName(false, p);
        }
        return hint;

    }

    private String cutRedundancy(String former) {
        int wantLen = 40;
        if (former.length() > wantLen) {
            former = former.substring(wantLen);
        }
        return former;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public PostAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TYPE_POST) {
            LogUtil.dd("view = normal");
            view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread_post, parent, false);
            view.setOnClickListener(this);
            return new PostHolder(view);
        } else if (viewType == TYPE_THREAD) {
            LogUtil.dd("view = header");
            view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread_thread, parent, false);
            return new HeaderHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_footer_common, parent, false);
            return new BaseFooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mThreadData != null) {
            if (holder instanceof HeaderHolder) {
                HeaderHolder headerHolder = (HeaderHolder) holder;
                if (mThreadData.getAuthor_id() == 0) {
                    mThreadData.setAuthor_name("匿名用户");
                    ImageUtil.loadIconAsBitmap(mContext, R.drawable.avatar_anonymous_left, headerHolder.mCivAvatarThread);
                } else {
                    ImageUtil.loadAvatarAsBitmapByUid(mContext, mThreadData.getAuthor_id(), headerHolder.mCivAvatarThread);
                }
                headerHolder.mTvDatetimeThread.setText(StampUtil.getDatetimeByStamp(mThreadData.getT_create()));
                headerHolder.mTvUsernameThread.setText(mThreadData.getAuthor_name());
                headerHolder.mTvTitle.setText(mThreadData.getTitle());
                // TODO: 17-5-26 Level is not set
                String str = BBCodeParse.bbcode2Html(mThreadData.getContent());
                headerHolder.mHtvContent.setHtml(str, new GlideImageGeter(headerHolder.mHtvContent.getContext(), headerHolder.mHtvContent));
            }
            if (mPostData != null && mPostData.size() > 0) {
                if (holder instanceof PostHolder) {
                    ThreadModel.PostBean p = mPostData.get(position - 1);
                    PostHolder h = (PostHolder) holder;
                    if (p.getAuthor_id() == 0) {
                        p.setAuthor_name("匿名用户");
                        ImageUtil.loadIconAsBitmap(mContext, R.drawable.avatar_anonymous_right, h.mCivAvatarPost);
                    } else {
                        ImageUtil.loadAvatarAsBitmapByUidWithRight(mContext, p.getAuthor_id(), h.mCivAvatarPost);
                    }
                    h.mTvUsernamePost.setText(p.getAuthor_name());
                    h.mTvPostDatetime.setText(StampUtil.getDatetimeByStamp(p.getT_create()));
                    h.mTvFloorPost.setText(p.getFloor() + "楼");
                    String htmlStr = BBCodeParse.bbcode2Html(p.getContent());
                    h.mTvPostContent.setHtml(htmlStr, new GlideImageGeter(h.mTvPostContent.getContext(), h.mTvPostContent));
                    h.itemView.setTag(position);
                } else if (holder instanceof BaseFooterViewHolder) {
                    LogUtil.d("base footer view");
                }
            }


        }
    }

    @Override
    public int getItemViewType(int position) {
//        LogUtil.dd("position", String.valueOf(position));
        if (mThreadData != null && mPostData != null) {
            if (position >= onePage) {
                return TYPE_FOOTER;
            }
        }
        if (position > 0) {
            return TYPE_POST;
        } else {
            return TYPE_THREAD;
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mThreadData != null) {
            count++;
        } else {
            return 0;
        }
        if (mPostData != null && mPostData.size() != 0) {
            count += mPostData.size();
        }
        if (count == 0) {
            return count;
        } else {
            if (count >= onePage) {
                count++;
            }
            return count;
        }

    }

    public void setPostData(List<ThreadModel.PostBean> postData) {
        mPostData = postData;
        notifyDataSetChanged();
    }

    public void setThreadData(ThreadModel.ThreadBean threadData) {
        mThreadData = threadData;
        notifyDataSetChanged();
    }

    public void clearData() {
        mThreadData = null;
        mPostData.clear();
    }

    public void addData(List<ThreadModel.PostBean> post, int page) {
        mPostData.addAll(post);
        onePage *= page + 1;

        notifyDataSetChanged();
    }

    public void refreshList(ThreadModel model) {
        if (model != null && model.getThread() != null){
            clearData();
            mPostData.addAll(model.getPost());
            mThreadData = model.getThread();
        }
    }

    static class PostHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_avatar_post)
        CircleImageView mCivAvatarPost;
        @BindView(R.id.tv_username_post)
        TextView mTvUsernamePost;
        @BindView(R.id.tv_post_datetime)
        TextView mTvPostDatetime;
        @BindView(R.id.tv_post_content)
        HtmlTextView mTvPostContent;
        @BindView(R.id.tv_floor_post)
        TextView mTvFloorPost;
        @BindView(R.id.tv_reply)
        TextView mTvReply;

        PostHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_avatar_thread)
        CircleImageView mCivAvatarThread;
        @BindView(R.id.tv_username_thread)
        TextView mTvUsernameThread;
        @BindView(R.id.tv_level_thread)
        TextView mTvLevelThread;
        @BindView(R.id.tv_datetime_thread)
        TextView mTvDatetimeThread;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.htv_content)
        HtmlTextView mHtvContent;

        HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
