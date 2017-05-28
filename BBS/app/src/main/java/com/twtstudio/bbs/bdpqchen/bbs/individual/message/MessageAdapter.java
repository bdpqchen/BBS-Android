package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel;

import butterknife.BindView;

/**
 * Created by Ricky on 2017/5/19.
 */

public class MessageAdapter extends BaseAdapter<MessageModel> {


    LayoutInflater inflater;
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
        MessageModel item = mDataSet.get(position);
        CommentView holderConverted = (CommentView) holder;
        int tag = mDataSet.get(position).getTag();
        if (tag == 2){
            MessageModel.ContentBean model = item.getContent_model();
//            holderConverted.mTvThreadTitle.setText(model.getThread_title());
            holderConverted.mTvDatetimeMessage.setText(StampUtil.getDatetimeByStamp(item.getT_create()));
//            holderConverted.mTvSummary.setText(comment.getContent());
            if (item.getRead() == 0){
                holderConverted.mTvRedDot.setVisibility(View.VISIBLE);
            }
        }
    }

    static class CommentView extends BaseViewHolder {

        @BindView(R.id.tv_thread_title)
        TextView mTvThreadTitle;
        @BindView(R.id.tv_summary)
        TextView mTvSummary;
        @BindView(R.id.tv_datetime_message)
        TextView mTvDatetimeMessage;
        @BindView(R.id.red_dot)
        View mTvRedDot;

        public CommentView(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }


}
