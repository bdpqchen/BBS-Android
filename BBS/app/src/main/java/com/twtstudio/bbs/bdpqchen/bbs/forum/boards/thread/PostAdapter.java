package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.bbkit.bbcode.BBCodeParse;
import com.twtstudio.bbs.bdpqchen.bbs.bbkit.htmltextview.HtmlTextView;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFooterViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.listener.OnItemClickListener;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.zzhoujay.glideimagegetter.GlideImageGetter;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient.BASE_URL;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_END;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_FOOTER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_HEADER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_JUST_HEADER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_NORMAL;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MAX_LENGTH_POST;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MAX_LENGTH_QUOTE;

/**
 * Created by bdpqchen on 17-5-23.
 */

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ThreadModel.ThreadBean mThreadData = new ThreadModel.ThreadBean();
    private List<ThreadModel.PostBean> mPostData = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener = null;
    private int onePage = MAX_LENGTH_POST;
    private int mPage = 0;
    private boolean mIsEnding = false;
    private boolean mIsNoMore = false;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public int getPostId(int position) {
        return mPostData.get(position).getId();
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
        if (viewType == ITEM_NORMAL) {
            LogUtil.dd("view = normal");
            view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread_post, parent, false);
            view.setOnClickListener(this);
            return new PostHolder(view);
        } else if (viewType == ITEM_FOOTER) {
            LogUtil.dd("view == footer");
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
            LogUtil.dd("position", String.valueOf(position));
            if (holder instanceof PostHolder) {
                ThreadModel.PostBean p = mPostData.get(position);
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
                String contentBefore = p.getContent();
                String contentAfter = contentBefore;
                if (contentBefore.contains("[/") && contentBefore.contains("[") && contentBefore.contains("]")) {
                    contentAfter = BBCodeParse.bbcode2Html(p.getContent());
                    RichText.fromHtml(contentAfter).into(h.mTvPostContent);
                } else {
                    contentAfter = contentAfter.replaceAll("attach:", BASE_URL + "img/");

                    RichText.fromMarkdown(contentAfter).into(h.mTvPostContent);
                }
//                LogUtil.dd("after", contentAfter);
                h.itemView.setTag(position);
            } else if (holder instanceof BaseFooterViewHolder) {
                LogUtil.d("base footer view");
            } else if (holder instanceof HeaderHolder) {
                HeaderHolder headerHolder = (HeaderHolder) holder;
                ThreadModel.PostBean p = mPostData.get(position);
                if (p.getAuthor_id() == 0) {
                    p.setAuthor_name("匿名用户");
                    ImageUtil.loadIconAsBitmap(mContext, R.drawable.avatar_anonymous_left, headerHolder.mCivAvatarThread);
                } else {
                    ImageUtil.loadAvatarAsBitmapByUidWithLeft(mContext, p.getAuthor_id(), headerHolder.mCivAvatarThread);
                }
                headerHolder.mTvTitle.setText(p.getTitle());
                headerHolder.mTvDatetimeThread.setText(StampUtil.getDatetimeByStamp(p.getT_create()));
                headerHolder.mTvUsernameThread.setText(p.getAuthor_name());
//                LogUtil.dd("before", p.getContent());
                String contentBefore = p.getContent();
                String contentAfter = contentBefore;
                if (contentBefore.contains("[/") && contentBefore.contains("[") && contentBefore.contains("]")) {
                    contentAfter = BBCodeParse.bbcode2Html(p.getContent());
                    RichText.fromHtml(contentAfter).into(headerHolder.mTvContent);
                } else {
                    contentAfter = contentAfter.replace("attach:", BASE_URL + "img/");
                    RichText.fromMarkdown(contentAfter).into(headerHolder.mTvContent);
                }
//                LogUtil.dd("after", contentAfter);
            } else if (holder instanceof TheEndViewHolder) {
                LogUtil.dd("the end view");
            } else if (holder instanceof JustHeaderHolder) {
                LogUtil.dd("just header view");
            }
        }
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
//        LogUtil.dd("item position", String.valueOf(position));
//        LogUtil.dd("itemCount", String.valueOf(getItemCount()));
        if (mPostData != null && mPostData.size() > 0) {
            if (position == 0) {
//                LogUtil.dd("return header");
                return ITEM_HEADER;
            }
            if (mPostData.size() == 1) {
                return ITEM_JUST_HEADER;
            }
            if (position + 1 == getItemCount()) {
//                LogUtil.dd("page=", String.valueOf(mPage));
                if (getItemCount() < (mPage) * onePage + 1) {
//                    LogUtil.dd("return end before footer");
                    return ITEM_END;
                }
                if (mIsNoMore) {
                    mIsNoMore = false;
//                    LogUtil.dd(" no more return end");
                    return ITEM_END;
                }
//                LogUtil.dd("return footer");
                return ITEM_FOOTER;
            } else {
//                LogUtil.dd("return normal");
                return ITEM_NORMAL;
            }
        }
        return ITEM_NORMAL;
    }

    public void updateThreadPost(List<ThreadModel.PostBean> postList, int page) {
        mPage = page + 1;
        if (postList == null || postList.size() == 0) {
            mIsNoMore = true;
        }
        if (postList != null) {
            mPostData.addAll(postList);
        }
        notifyDataSetChanged();

    }

    public void refreshList(List<ThreadModel.PostBean> model) {
        mPostData.removeAll(mPostData);
        mPostData.addAll(model);
        notifyDataSetChanged();
    }

    public String comment2reply(int postPosition, String content) {
        ThreadModel.PostBean post = mPostData.get(postPosition);
        String beforeCommendContent = post.getContent();
//        LogUtil.dd("before comment", beforeCommendContent);
        String cut = cutTwoQuote(beforeCommendContent);
//        LogUtil.dd("cut", cut);
        String added = addTwoQuote(cut);
//        LogUtil.dd("added", added);
        content = content +
                "\n > 回复 #" +
                post.getFloor() + " " +
                getAuthorName(postPosition) +
                " :\n\n > " +
                added;
//        LogUtil.dd("content final", content);
        return content;
    }

    private String addTwoQuote(String str0) {
        StringBuilder strNew = new StringBuilder();
        String key = "> ";
        while (str0.contains(key)) {
            int i = str0.indexOf(key);
            String cut = str0.substring(0, i);
            cut = cutIfTooLong(cut);
            str0 = str0.substring(i + 2, str0.length());
            strNew.append(cut).append("\n").append(key).append(key);
        }
        strNew.append(str0);
//        Log.d("str quote two add", strNew.toString());
        return cutIfTooLong(strNew.toString());
    }

    private String cutIfTooLong(String s) {
        if (s.length() > MAX_LENGTH_QUOTE) {
            return s.substring(0, MAX_LENGTH_QUOTE - 1);
        }
        return s;
    }

    private String cutTwoQuote(String str0) {
        StringBuilder strNew = new StringBuilder();
        String key = "> > ";
        if (str0.contains(key)) {
            int i = str0.indexOf(key);
            str0 = str0.substring(0, i);
        }
        strNew.append(str0);
//        Log.d("str quote two cut", strNew.toString());
        return strNew.toString();
    }

    private String getAuthorName(int position) {
        int uid = 0;
        if (mPostData != null && mPostData.size() > 0) {
            uid = mPostData.get(position).getAuthor_id();
        }
        if (uid == 0) {
            return "匿名用户";
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
        @BindView(R.id.htv_post_content)
        HtmlTextView mHtvPostContent;
        @BindView(R.id.tv_floor_post)
        TextView mTvFloorPost;
        @BindView(R.id.tv_reply)
        TextView mTvReply;
        @BindView(R.id.tv_post_content)
        TextView mTvPostContent;

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
        @BindView(R.id.tv_content)
        TextView mTvContent;

        HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class TheEndViewHolder extends RecyclerView.ViewHolder {
        TheEndViewHolder(View view) {
            super(view);
        }
    }

    static class JustHeaderHolder extends RecyclerView.ViewHolder {
        JustHeaderHolder(View view) {
            super(view);
        }
    }
}
