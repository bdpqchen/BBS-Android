package com.twtstudio.bbs.bdpqchen.bbs.message;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFooterViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity;
import com.twtstudio.bbs.bdpqchen.bbs.message.model.MessageModel;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity.INTENT_THREAD_FLOOR;

/**
 * Created by Ricky on 2017/5/19.
 */

public class MessageAdapter extends BaseAdapter<MessageModel> {

    private LayoutInflater inflater;
    private int mPage = 0;


    public MessageAdapter(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentView(inflater.inflate(R.layout.item_rv_message_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
//        LogUtil.dd("datalistsize", String.valueOf(mDataSet.size()));
        if (mDataSet != null && mDataSet.size() > 0) {
            if (holder instanceof CommentView) {
                MessageModel item = mDataSet.get(position);
                CommentView iHolder = (CommentView) holder;
                if (item.getContent_model() != null) {
                    int tag = mDataSet.get(position).getTag();
                    if (tag == 2 || tag == 3) {
                        MessageModel.ContentModel model = item.getContent_model();
                        iHolder.mTvDatetime.setText(StampUtil.getDatetimeByStamp(model.getT_create()));
                        iHolder.mTvSummary.setText(model.getContent());
                        ImageUtil.loadAvatarAsBitmapByUid(mContext, item.getAuthor_id(), iHolder.mCivMessage);
                        iHolder.itemView.setOnClickListener(v -> {
                            Intent intent = new Intent(mContext, ThreadActivity.class);
                            intent.putExtra(INTENT_THREAD_ID, model.getThread_id());
                            intent.putExtra(INTENT_THREAD_TITLE, model.getThread_title());
                            intent.putExtra(INTENT_THREAD_FLOOR, model.getFloor());

//                            intent.putExtra(INTENT_BOARD_TITLE, )
                            mContext.startActivity(intent);
                        });
                        if (iHolder.mTvRedDot != null) {
                            if (item.getRead() == 0) {
                                iHolder.mTvRedDot.setVisibility(View.VISIBLE);
                            } else {
                                iHolder.mTvRedDot.setVisibility(View.GONE);
                            }
                        }

                        String preCom = item.getAuthor_name() + "  在  " + model.getThread_title();
                        if (tag == 2) {
                            String composed = preCom + "  中评论了你";
                            iHolder.mTvComposeTitle.setText(composed);
                        }
                        if (tag == 3) {
                            String com = preCom + "楼回复了你" + model.getFloor();
                            iHolder.mTvComposeTitle.setText(com);
                        }
                    }
                }
            } else if (holder instanceof TheEndViewHolder) {

            } else if (holder instanceof BaseFooterViewHolder) {

            }
        }
    }

    static class CommentView extends BaseViewHolder {
        @BindView(R.id.tv_summary)
        TextView mTvSummary;
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

}
