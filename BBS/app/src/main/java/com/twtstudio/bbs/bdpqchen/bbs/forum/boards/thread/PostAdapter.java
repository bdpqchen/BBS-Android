package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseFooterViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IsUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TransUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.view.ThumbView;
import com.twtstudio.bbs.bdpqchen.bbs.commons.viewholder.TheEndViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel;
import com.twtstudio.bbs.bdpqchen.bbs.htmltextview.GlideImageGeter;
import com.twtstudio.bbs.bdpqchen.bbs.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ANONYMOUS_NAME;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_END;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_FOOTER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_HEADER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_JUST_HEADER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_NORMAL;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MAX_LENGTH_POST;

/**
 * Created by bdpqchen on 17-5-23.
 */

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ThreadModel.PostBean> mPostData = new ArrayList<>();
    private int mPage = 0;
    private boolean mIsNoMore = false;

    List<ThreadModel.PostBean> getPostList() {
        return mPostData;
    }

    private OnPostClickListener mListener;

    public interface OnPostClickListener {
        void onLikeClick(int position, boolean isLike, boolean isPost);

        void onReplyClick(int position);
    }

    int getPostId(int position) {
        return mPostData.get(position).getId();
    }

    PostAdapter(Context context, OnPostClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_NORMAL) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread_post, parent, false);
            return new PostHolder(view);
        } else if (viewType == ITEM_FOOTER) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_common_footer, parent, false);
            return new BaseFooterViewHolder(view);
        } else if (viewType == ITEM_HEADER) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread_thread, parent, false);
            return new HeaderHolder(view);
        } else if (viewType == ITEM_END) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_common_no_more, parent, false);
            return new TheEndViewHolder(view);
        } else if (viewType == ITEM_JUST_HEADER) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_common_just_header, parent, false);
            return new JustHeaderHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mPostData != null && mPostData.size() > 0) {
            if (holder instanceof HeaderHolder) {
                HeaderHolder headerHolder = (HeaderHolder) holder;
                ThreadModel.PostBean p = mPostData.get(position);
                if (IsUtil.INSTANCE.is1(p.getAnonymous())) {
                    p.setAuthor_name(ANONYMOUS_NAME);
                    p.setAuthor_id(0);
                    headerHolder.mCivAvatarThread.setOnClickListener(null);
                } else {
                    headerHolder.mCivAvatarThread.setOnClickListener(v -> {
                        startToPeople(p.getAuthor_id(), p.getAuthor_name(), headerHolder.mCivAvatarThread);
                    });
                }
                ImageUtil.loadAvatarButAnon(mContext, p.getAuthor_id(), headerHolder.mCivAvatarThread);
                headerHolder.mTvTitle.setText(p.getTitle());
                headerHolder.mTvUsernameThread.setText(TextUtil.getNameWithFriend(p.getAuthor_name(), p.getAuthor_nickname(), p.getFriend()));
                headerHolder.mHtvContent.setHtml(p.getContent_converted(), new GlideImageGeter(mContext, headerHolder.mHtvContent));
                headerHolder.mTvDatetimeThread.setText(TextUtil.getThreadDateTime(p.getT_create(), p.getT_modify()));
            } else if (holder instanceof PostHolder) {
                ThreadModel.PostBean p = mPostData.get(position);
                PostHolder h = (PostHolder) holder;
                int uid = p.getAuthor_id();
                if (IsUtil.INSTANCE.is1(p.getAnonymous())) {
                    p.setAuthor_name(ANONYMOUS_NAME);
                    p.setAuthor_id(0);
                    h.mCivAvatarPost.setOnClickListener(null);
                } else {
                    h.mCivAvatarPost.setOnClickListener(v -> {
                        startToPeople(uid, p.getAuthor_name(), h.mCivAvatarPost);
                    });
                }
                ImageUtil.loadAvatarButAnon(mContext, uid, h.mCivAvatarPost);
                h.mTvUsernamePost.setText(TextUtil.getNameWithFriend(p.getAuthor_name(), p.getAuthor_nickname(), p.getFriend()));
                h.mTvPostDatetime.setText(StampUtil.getDatetimeByStamp(p.getT_create()));
                h.mTvFloorPost.setText(String.valueOf(p.getFloor() + "楼"));
                h.mHtvPostContent.setHtml(p.getContent_converted(), new GlideImageGeter(mContext, h.mHtvPostContent));
                final boolean isLiked = IsUtil.INSTANCE.is1(p.getLiked());
                h.mThumbView.setIsLiked(isLiked);
                h.mThumbView.setLikeCount(p.getLike());
                h.mThumbView.setThumbOnClickListener(v -> {
                    mListener.onLikeClick(position, !isLiked, true);
                });
                h.mIvReply.setOnClickListener(v -> mListener.onReplyClick(position));
            } else if (holder instanceof BaseFooterViewHolder) {
//                LogUtil.dd("base footer view");
            } else if (holder instanceof TheEndViewHolder) {
//                LogUtil.dd("the end view");
            } else if (holder instanceof JustHeaderHolder) {
//                LogUtil.dd("just header view");
            }
        }
    }

    private void startToPeople(int uid, String username, View transitionView) {
        mContext.startActivity(IntentUtil.toPeople(mContext, uid, username),
                TransUtil.getAvatarTransOptions(mContext, transitionView));
    }

    @Override
    public int getItemCount() {
        if (mPostData == null || mPostData.size() == 0) {
            return 0;
        } else {
            return mPostData.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mPostData != null && mPostData.size() > 0) {
            if (position == 0) {
                return ITEM_HEADER;
            }
            if (mPostData.size() == 1) {
                return ITEM_JUST_HEADER;
            }
            if (position + 1 == getItemCount()) {
                if (getItemCount() < (mPage) * MAX_LENGTH_POST + 1) {
                    return ITEM_END;
                }
                if (mIsNoMore) {
                    mIsNoMore = false;
                    return ITEM_END;
                }
                return ITEM_FOOTER;
            } else {
                return ITEM_NORMAL;
            }
        }
        return ITEM_NORMAL;
    }

    void updateThreadPost(List<ThreadModel.PostBean> postList, int page) {
        mPage = page + 1;
        if (postList == null || postList.size() == 0) {
            mIsNoMore = true;
        }
        if (postList != null) {
            mPostData.addAll(postList);
        }
        notifyDataSetChanged();

    }

    void refreshList(List<ThreadModel.PostBean> model) {
        mPostData.removeAll(mPostData);
        mPostData.addAll(model);
        notifyDataSetChanged();
    }

    void refreshThisPage(List<ThreadModel.PostBean> postList, int page) {
//        LogUtil.dd("refreshThisPage()");
        LogUtil.dd(String.valueOf(mPostData.size()));
        if (page == 0) {
            mPostData.removeAll(mPostData);
        } else {
            for (int i = page * MAX_LENGTH_POST; i < mPostData.size(); i++) {
                LogUtil.dd(String.valueOf(mPostData.size()));
                mPostData.remove(i);
                LogUtil.dd(String.valueOf(mPostData.size()));
            }
        }
        mPostData.addAll(postList);
        notifyDataSetChanged();
        LogUtil.dd(String.valueOf(mPostData.size()));
    }

    void likeItem(int position, boolean isLike) {
        int add = 1;
        int liked = 1;
        if (!isLike) {
            add = -1;
            liked = 0;
        }
        ThreadModel.PostBean entity = mPostData.get(position);
        entity.setLike(entity.getLike() + add);
        entity.setLiked(liked);
        mPostData.set(position, entity);
        notifyItemChanged(position);
    }

    String comment2reply(int postPosition, String content) {
        ThreadModel.PostBean post = mPostData.get(postPosition);
        String added = TextUtil.addTwoQuote(TextUtil.cutTwoQuote(post.getContent()));
        return content + TextUtil.getCommentContent(post.getFloor(), getAuthorName(postPosition)) + added;
    }

    private String getAuthorName(int position) {
        int uid = 0;
        if (mPostData != null && mPostData.size() > 0) {
            uid = mPostData.get(position).getAuthor_id();
        }
        if (uid == 0) {
            return ANONYMOUS_NAME;
        } else {
            if (mPostData.get(position) != null) {
                return mPostData.get(position).getAuthor_name();
            }
        }
        return ".";
    }

    String getDynamicHint(int postPosition) {
        String hint;
        if (postPosition == 0) {
            hint = "评论帖主 " + getAuthorName(0);
        } else {
            ThreadModel.PostBean post = mPostData.get(postPosition);
            hint = "回复 " + post.getFloor() + "楼 " + getAuthorName(postPosition);
        }
        return hint;
    }

    static class PostHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_avatar_post)
        CircleImageView mCivAvatarPost;
        @BindView(R.id.tv_username_post)
        TextView mTvUsernamePost;
        @BindView(R.id.tv_post_datetime)
        TextView mTvPostDatetime;
        @BindView(R.id.tv_floor_post)
        TextView mTvFloorPost;
        @BindView(R.id.htv_post_content)
        HtmlTextView mHtvPostContent;
        @BindView(R.id.iv_post_reply)
        ImageView mIvReply;
        @BindView(R.id.custom_thumb_post)
        ThumbView mThumbView;

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
        @BindView(R.id.tv_datetime_thread)
        TextView mTvDatetimeThread;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.htv_content)
        HtmlTextView mHtvContent;
//        @BindView(R.id.tv_thread_like)
//        TextView mTvLike;
//        @BindView(R.id.iv_thread_like)
//        ImageView mIvLike;
//        @BindView(R.id.iv_thread_comment)
//        ImageView mIvComment;

        HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class JustHeaderHolder extends RecyclerView.ViewHolder {
        JustHeaderHolder(View view) {
            super(view);
        }
    }
}
