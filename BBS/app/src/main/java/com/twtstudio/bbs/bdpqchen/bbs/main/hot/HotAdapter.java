package com.twtstudio.bbs.bdpqchen.bbs.main.hot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListActivity;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainModel;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ANONYMOUS_NAME;


/**
 * Created by bdpqchen on 17-6-5.
 */

public class HotAdapter extends BaseAdapter<MainModel.HotBean> {


    public HotAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HotViewHolder holder = new HotViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_hot, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder0, int position) {

        if (mDataSet != null && mDataSet.size() > 0) {
            if (holder0 instanceof HotViewHolder) {
                HotViewHolder holder = (HotViewHolder) holder0;
                MainModel.HotBean model = mDataSet.get(position);
                if (model.getAnonymous() == 1) {
                    model.setAuthor_name(ANONYMOUS_NAME);
                    ImageUtil.loadIconAsBitmap(mContext, R.drawable.avatar_anonymous_left, holder.mCivHotAvatar);
                } else {
                    ImageUtil.loadAvatarAsBitmapByUidWithLeft(mContext, model.getAuthor_id(), holder.mCivHotAvatar);
                }
                holder.mTvUsername.setText(model.getAuthor_name());
                holder.mTvBoardName.setText(TextUtil.getBoardName(model.getBoard_name()));
                holder.mTvThreadTitle.setText(model.getTitle());
                holder.mTvPostCount.setText(model.getC_post() + "");
                holder.mTvHotTime.setText("发布于 " + StampUtil.getDatetimeByStamp(model.getT_create()));

                holder.mTvBoardName.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, ThreadListActivity.class);
                    intent.putExtra(Constants.INTENT_BOARD_ID, model.getBoard_id());
                    intent.putExtra(Constants.INTENT_BOARD_TITLE, model.getBoard_name());
                    mContext.startActivity(intent);
                });
                holder.mLlHotBody.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, ThreadActivity.class);
                    intent.putExtra(Constants.INTENT_THREAD_ID, model.getId());
                    intent.putExtra(Constants.INTENT_THREAD_TITLE, model.getTitle());
                    mContext.startActivity(intent);
                });
            }
        }

    }


    static class HotViewHolder extends BaseViewHolder {
        @BindView(R.id.civ_hot_avatar)
        CircleImageView mCivHotAvatar;
        @BindView(R.id.tv_username)
        TextView mTvUsername;
        @BindView(R.id.tv_board_name)
        TextView mTvBoardName;
        @BindView(R.id.ll_layer_header)
        RelativeLayout mLlLayerHeader;
        @BindView(R.id.tv_thread_title)
        TextView mTvThreadTitle;
        @BindView(R.id.tv_post_count)
        TextView mTvPostCount;
        @BindView(R.id.tv_hot_time)
        TextView mTvHotTime;
        @BindView(R.id.ll_hot_body)
        LinearLayout mLlHotBody;

        HotViewHolder(View view) {
            super(view);

        }
    }
}
