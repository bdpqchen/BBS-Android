package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.bbkit.htmltextview.GlideImageGeter;
import com.twtstudio.bbs.bdpqchen.bbs.bbkit.htmltextview.HtmlTextView;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFooterViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_MSG_APPEAL;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_MSG_COMMENT;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_MSG_LETTER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_MSG_REPLY;
import static com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity.INTENT_THREAD_FLOOR;

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
        if (viewType == 2 || viewType == 3 || viewType == 1) {
            return new CommentView(inflater.inflate(R.layout.item_rv_message_comment, parent, false));
        } else if (viewType == 4) {
            return new AppealView(inflater.inflate(R.layout.item_rv_message_appeal, parent, false));
        } else {

        }
        return new TheEndViewHolder(inflater.inflate(R.layout.item_common_no_more, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mDataSet != null && mDataSet.size() > 0) {
            MessageModel item = mDataSet.get(position);
            int tag = mDataSet.get(position).getTag();
            if (holder instanceof CommentView) {
                CommentView iHolder = (CommentView) holder;
                setViewStatus(item.getRead(), iHolder.mTvRedDot);
                if (item.getContent_model() != null) {
                    ImageUtil.loadAvatarAsBitmapByUid(mContext, item.getAuthor_id(), iHolder.mCivMessage);
                    if (tag == 2 || tag == 3) {
                        MessageModel.ContentModel model = item.getContent_model();
                        iHolder.mTvDatetime.setText(StampUtil.getDatetimeByStamp(model.getT_create()));
                        String content = TextUtil.formatContent(model.getContent());
                        iHolder.mHtvSummary.setHtml(content, new GlideImageGeter(mContext, iHolder.mHtvSummary));
                        iHolder.itemView.setOnClickListener(v -> {
                            iHolder.mTvRedDot.setVisibility(View.GONE);
                            mContext.startActivity(IntentUtil.toThread(mContext,
                                    model.getThread_id(), model.getThread_title(), model.getFloor()));
                        });
                        String what = "回复";
                        if (tag == 2) {
                            what = "评论";
                        }
                        String composed = item.getAuthor_name() + " 在 " + model.getThread_title() + " 中" + what + "了你";
                        iHolder.mTvComposeTitle.setText(composed);
                    }
                }
                if (tag == 1) {
                    iHolder.mTvComposeTitle.setText(TextUtil.getTwoNames(item.getAuthor_name(), item.getAuthor_nickname()));
                    iHolder.mTvDatetime.setText(StampUtil.getDatetimeByStamp(item.getT_create()));
                    iHolder.mHtvSummary.setText(item.getContent());
                }
            } else if (holder instanceof AppealView) {
                AppealView iHolder = (AppealView) holder;
                setViewStatus(item.getRead(), iHolder.mRedDot);
                ImageUtil.loadAvatarAsBitmapByUid(mContext, item.getAuthor_id(), iHolder.mCivMessage);
                iHolder.mTvAppealName.setText(TextUtil.getTwoNames(item.getAuthor_name(), item.getAuthor_nickname()));
                iHolder.mTvDatetime.setText(StampUtil.getDatetimeByStamp(item.getT_create()));
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
                case 1:
                    return ITEM_MSG_LETTER;
                case 2:
                    return ITEM_MSG_COMMENT;
                case 3:
                    return ITEM_MSG_REPLY;
                case 4:
                    return ITEM_MSG_APPEAL;
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
