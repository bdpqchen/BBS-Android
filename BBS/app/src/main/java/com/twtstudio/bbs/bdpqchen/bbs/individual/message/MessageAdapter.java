package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TransUtil;
import com.twtstudio.bbs.bdpqchen.bbs.htmltextview.GlideImageGeter;
import com.twtstudio.bbs.bdpqchen.bbs.htmltextview.HtmlTextView;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseFooterViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel;

import java.util.Objects;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_MSG_APPEAL;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_MSG_AT_USER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_MSG_COMMENT;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_MSG_LETTER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_MSG_REPLY;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.TAG_MSG_APPEAL;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.TAG_MSG_AT_USER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.TAG_MSG_COMMENT;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.TAG_MSG_LETTER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.TAG_MSG_REPLY;

/**
 * Created by Ricky on 2017/5/19.
 */

public class MessageAdapter extends BaseAdapter<MessageModel> {

    private LayoutInflater inflater;
    private int mPage = 0;
    private MessagePresenter mPresenter;

    public MessageAdapter(Context context, MessagePresenter presenter) {
        super(context);
        inflater = LayoutInflater.from(context);
        mPresenter = presenter;
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_MSG_REPLY || viewType == ITEM_MSG_COMMENT || viewType == ITEM_MSG_LETTER || viewType == ITEM_MSG_AT_USER) {
            return new CommentView(inflater.inflate(R.layout.item_rv_message_comment, parent, false));
        } else if (viewType == ITEM_MSG_APPEAL) {
            return new AppealView(inflater.inflate(R.layout.item_rv_message_appeal, parent, false));
        } else {
            return new BaseViewHolder(inflater.inflate(R.layout.item_empty, parent, false));
        }
//        return new TheEndViewHolder(inflater.inflate(R.layout.item_common_no_more, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mDataSet != null && mDataSet.size() > 0) {
            MessageModel item = mDataSet.get(position);
            int tag = mDataSet.get(position).getTag();
            if (holder instanceof CommentView) {
                CommentView iHolder = (CommentView) holder;
                setViewStatus(item.getRead(), iHolder.mTvRedDot);
                ImageUtil.loadAvatarButDefault(mContext, item.getAuthor_id(), iHolder.mCivMessage);
                iHolder.mCivMessage.setOnClickListener(v -> {
                    startToPeople(mContext, item.getAuthor_id(), iHolder.mCivMessage);
                });
                if (item.getContent_model() != null) {
                    LogUtil.dd("load avatarr", String.valueOf(item.getAuthor_id()));
                    if (tag == TAG_MSG_COMMENT || tag == TAG_MSG_REPLY || tag == TAG_MSG_AT_USER) {
                        MessageModel.ContentModel model = item.getContent_model();
                        iHolder.mTvComposeTitle.setText(item.getAuthor_name());
                        iHolder.mTvDatetime.setText(StampUtil.getMessageDatetimeByStamp(model.getT_create()));
                        String action = TextUtil.getMsgActionText(tag);
                        String content = TextUtil.formatContent(model.getContent(), action);
                        action = action + "了你";
                        if (Objects.equals(action, "提到了你"))
                            content = " 在 " + model.getThread_title() + " 中" + action;
                        else content = "<p>" + action + ":" + content.substring(3);
                        iHolder.mHtvSummary.setHtml(content, new GlideImageGeter(mContext, iHolder.mHtvSummary));
                        iHolder.itemView.setOnClickListener(v -> {
                            iHolder.mTvRedDot.setVisibility(View.GONE);
                            mContext.startActivity(IntentUtil.toThread(mContext,
                                    model.getThread_id(), model.getThread_title(), model.getFloor()));
                        });
                    }
                }
                if (tag == 1) {
                    iHolder.mTvComposeTitle.setText(TextUtil.getTwoNames(item.getAuthor_name(), item.getAuthor_nickname()));
                    iHolder.mTvDatetime.setText(StampUtil.getMessageDatetimeByStamp(item.getT_create()));
                    String content = item.getContent();
                    if (content.length() > 17) {
                        content = content.substring(0, 17) + "...";
                    }
                    iHolder.mHtvSummary.setText(content);
                    iHolder.itemView.setOnClickListener(v -> {
                        mContext.startActivity(IntentUtil.toLetter(mContext, item.getAuthor_id(), item.getAuthor_name()));
                    });
                }
            } else if (holder instanceof AppealView) {
                AppealView iHolder = (AppealView) holder;
                setViewStatus(item.getRead(), iHolder.mRedDot);
                ImageUtil.loadAvatarButDefault(mContext, item.getAuthor_id(), iHolder.mCivMessage);
                iHolder.mCivMessage.setOnClickListener(v -> {
                    startToPeople(mContext, item.getAuthor_id(), iHolder.mCivMessage);
                });
                iHolder.mTvAppealName.setText(TextUtil.getTwoNames(item.getAuthor_name(), item.getAuthor_nickname()));
                iHolder.mTvDatetime.setText(StampUtil.getMessageDatetimeByStamp(item.getT_create()));
                if (item.getContent_model() != null) {
                    MessageModel.ContentModel content = item.getContent_model();
                    iHolder.mTvAppealMsg.setText("请求成为好友 " + content.getMessage());
                    int status = content.getStatus();
                    if (status == 0) {
                        iHolder.mTvAppealGrant.setOnClickListener(v -> {
                            mPresenter.confirmFriend(position, item.getId(), 1);
                        });
                        iHolder.mTvAppealReject.setOnClickListener(v -> {
                            mPresenter.confirmFriend(position, item.getId(), 0);
                        });
                    } else {
                        String confirm = "已同意";
                        if (status == -1) {
                            confirm = "已拒绝";
                        }
                        iHolder.mTvAppealGrant.setText(confirm);
                        iHolder.mTvAppealReject.setVisibility(View.GONE);
                    }
                }
            } else if (holder instanceof TheEndViewHolder) {

            } else if (holder instanceof BaseFooterViewHolder) {

            }
        }
    }

    private void startToPeople(Context context, int uid, View view) {
        context.startActivity(IntentUtil.toPeople(mContext, uid), TransUtil.getAvatarTransOptions(context, view));
    }

    private void setViewStatus(int status, View v) {
        if (v != null) {
            if (status == 0) {
                v.setVisibility(View.VISIBLE);
            } else {
                v.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataSet != null && mDataSet.size() > 0) {
            switch (mDataSet.get(position).getTag()) {
                case TAG_MSG_LETTER:
                    return ITEM_MSG_LETTER;
                case TAG_MSG_COMMENT:
                    return ITEM_MSG_COMMENT;
                case TAG_MSG_REPLY:
                    return ITEM_MSG_REPLY;
                case TAG_MSG_APPEAL:
                    return ITEM_MSG_APPEAL;
                case TAG_MSG_AT_USER:
                    return ITEM_MSG_AT_USER;
            }
        }
        return -1;
    }

    public void updateRead(int position, int read) {
        mDataSet.get(position).setRead(read);
    }

    public void updateConfirm(int position, int isConfirm) {
        mDataSet.get(position).getContent_model().setStatus(isConfirm);
    }

    static class CommentView extends BaseViewHolder {
        @BindView(R.id.tv_summary)
        HtmlTextView mHtvSummary;
        @BindView(R.id.tv_datetime)
        TextView mTvDatetime;
        @BindView(R.id.red_dot)
        View mTvRedDot;
        @BindView(R.id.tv_compose_title)
        TextView mTvComposeTitle;
        @BindView(R.id.civ_message)
        CircleImageView mCivMessage;

        public CommentView(View itemView) {
            super(itemView);
        }

    }

    public class TheEndViewHolder extends BaseViewHolder {
        public TheEndViewHolder(View view) {
            super(view);
        }
    }

    static class AppealView extends BaseViewHolder {
        @BindView(R.id.civ_message)
        CircleImageView mCivMessage;
        @BindView(R.id.tv_appeal_name)
        TextView mTvAppealName;
        @BindView(R.id.tv_appeal_msg)
        TextView mTvAppealMsg;
        @BindView(R.id.tv_datetime)
        TextView mTvDatetime;
        @BindView(R.id.tv_appeal_reject)
        TextView mTvAppealReject;
        @BindView(R.id.tv_appeal_grant)
        TextView mTvAppealGrant;
        @BindView(R.id.red_dot)
        View mRedDot;

        AppealView(View view) {
            super(view);
        }
    }
}
