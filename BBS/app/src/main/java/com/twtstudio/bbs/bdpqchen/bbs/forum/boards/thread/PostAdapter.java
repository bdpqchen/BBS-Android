package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.rjeschke.txtmark.Processor;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.bbkit.htmltextview.GlideImageGeter;
import com.twtstudio.bbs.bdpqchen.bbs.bbkit.htmltextview.HtmlTextView;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFooterViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.listener.OnItemClickListener;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.view_holder.TheEndViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel;

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
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MAX_LENGTH_QUOTE;

/**
 * Created by bdpqchen on 17-5-23.
 */

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<ThreadModel.PostBean> mPostData = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener = null;
    private int onePage = MAX_LENGTH_POST;
    private int mPage = 0;
    private boolean mIsEnding = false;
    private boolean mIsNoMore = false;
    private boolean mEnding;
    private boolean mIsFinding = false;
    public List<ThreadModel.PostBean> getPostList(){
        return mPostData;
    }

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
//            LogUtil.dd("view = normal");
            view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_thread_post, parent, false);
//            view.setOnClickListener(this);
            return new PostHolder(view);
        } else if (viewType == ITEM_FOOTER) {
//            LogUtil.dd("view == footer");
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
//            LogUtil.dd("position", String.valueOf(position));
            if (holder instanceof HeaderHolder) {
                HeaderHolder headerHolder = (HeaderHolder) holder;
                ThreadModel.PostBean p = mPostData.get(position);
                if (p.getAuthor_id() == 0) {
                    p.setAuthor_name(ANONYMOUS_NAME);
                    ImageUtil.loadIconAsBitmap(mContext, R.drawable.avatar_anonymous_left, headerHolder.mCivAvatarThread);
                } else {
                    ImageUtil.loadAvatarAsBitmapByUidWithLeft(mContext, p.getAuthor_id(), headerHolder.mCivAvatarThread);
                }
                headerHolder.mTvTitle.setText(p.getTitle());
                headerHolder.mTvDatetimeThread.setText(StampUtil.getDatetimeByStamp(p.getT_create()));
                headerHolder.mTvUsernameThread.setText(TextUtil.getTwoNames(p.getAuthor_name(), p.getAuthor_nickname()));
                String content = formatContent(p.getContent());
                headerHolder.mHtvContent.setHtml(content, new GlideImageGeter(mContext, headerHolder.mHtvContent));
                if (p.getT_modify() > 0 && p.getT_modify() != p.getT_create()){
                    headerHolder.mTvModifyTime.setText(TextUtil.getModifyTime(p.getT_modify()));
                }
            } else if (holder instanceof PostHolder) {
                ThreadModel.PostBean p = mPostData.get(position);
                PostHolder h = (PostHolder) holder;
                if (p.getAuthor_id() == 0) {
                    p.setAuthor_name(ANONYMOUS_NAME);
                    ImageUtil.loadIconAsBitmap(mContext, R.drawable.avatar_anonymous_right, h.mCivAvatarPost);
                } else {
                    ImageUtil.loadAvatarAsBitmapByUidWithRight(mContext, p.getAuthor_id(), h.mCivAvatarPost);
                }
                h.mTvUsernamePost.setText(TextUtil.getTwoNames(p.getAuthor_name(), p.getAuthor_nickname()));
                h.mTvPostDatetime.setText(StampUtil.getDatetimeByStamp(p.getT_create()));
                h.mTvFloorPost.setText(p.getFloor() + "楼");
                String content = formatContent(p.getContent());
                h.mHtvPostContent.setHtml(content, new GlideImageGeter(mContext, h.mHtvPostContent));
                h.mTvReply.setTag(position);
                h.mTvReply.setOnClickListener(this);
            } else if (holder instanceof BaseFooterViewHolder) {
                LogUtil.dd("base footer view");
            } else if (holder instanceof TheEndViewHolder) {
                LogUtil.dd("the end view");
            } else if (holder instanceof JustHeaderHolder) {
                LogUtil.dd("just header view");
            }
        }
    }

    private String formatContent(String contentBefore){
        String content = "";
        if (contentBefore != null && contentBefore.length() > 0){
            content = Processor.process(contentBefore);
            content = TextUtil.getReplacedContent(content);
        }
        return content;
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
//            if (position == 0 && mPage == 1 && !mIsEnding) {
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
        String cut = cutTwoQuote(beforeCommendContent);
        String added = addTwoQuote(cut);
        content = content +
                "\n> 回复 #" +
                post.getFloor() + " " +
                getAuthorName(postPosition) +
                " :\n> \n> " +
                added;
        LogUtil.dd("content final", content);
        return content;
    }


    //添加两层的引用并截断1层 和 2层太长的部分
    private String addTwoQuote(String str0) {
        String key = "> ";
        if (str0.contains(key)){
            int p = str0.indexOf(key);
            String start = str0.substring(0, p);
            start = cutIfTooLong(start);
            start = start.replaceAll("\\n", "\n> ");
            String end  = str0.substring(p, str0.length());
            end = cutIfTooLong(end);
            end = "\n" + end;
            end = end.replaceAll("> ", "\n> > ");
            str0 = start + end;
        }else{
            str0 = cutIfTooLong(str0);
            str0 = str0.replaceAll("\\n", "\n> ");
        }
        return str0;
    }

    private String cutIfTooLong(String s) {
        if (s.length() > MAX_LENGTH_QUOTE + 1) {
            return s.substring(0, MAX_LENGTH_QUOTE) + "...";
        }
        return s;
    }

    //去掉最后面的两层的引用
    private String cutTwoQuote(String str0) {
        StringBuilder strNew = new StringBuilder();
        String key = "> > ";
        if (str0.contains(key)) {
            //去掉末尾的\n
            int i = str0.indexOf(key);
            str0 = str0.substring(0, i);
            String strStart = str0.substring(0, i - 3);
            String strEnd = str0.substring(str0.length() - 3, str0.length());
            strEnd = strEnd.replace("\n", "");
            str0 = strStart + strEnd;
        }
        strNew.append(str0);
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

    public void setEnding(boolean ending) {
        mEnding = ending;
    }

    public void findIt() {
        mIsFinding = false;
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
        @BindView(R.id.tv_reply)
        TextView mTvReply;
        @BindView(R.id.htv_post_content)
        HtmlTextView mHtvPostContent;
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
        @BindView(R.id.tv_modify_time)
        TextView mTvModifyTime;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.htv_content)
        HtmlTextView mHtvContent;

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
