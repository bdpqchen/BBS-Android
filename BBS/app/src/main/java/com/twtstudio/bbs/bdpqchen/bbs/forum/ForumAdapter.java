package com.twtstudio.bbs.bdpqchen.bbs.forum;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_FORUM_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_FORUM_TITLE;


/**
 * Created by bdpqchen on 17-5-11.
 */

public class ForumAdapter extends BaseAdapter<TwoForumModel> {

    FragmentActivity mActivity;

    public ForumAdapter(Context context, FragmentActivity activity) {
        super(context);
        mContext = context;
        mActivity = activity;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_forum, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        if (mDataSet != null && mDataSet.size() > 0) {
            ViewHolder viewHolder = (ViewHolder) holder;
            TwoForumModel model = mDataSet.get(position);
            String coverUrl1 = RxDoHttpClient.BASE_URL + "forum/" + model.model1.getId() + "/cover";
            String coverUrl2 = RxDoHttpClient.BASE_URL + "forum/" + model.model2.getId() + "/cover";
            viewHolder.mTvName1.setText(model.model1.getName());
            ImageUtil.loadForumCover(mContext, coverUrl1, viewHolder.mIvCover1);
            viewHolder.mTvName2.setText(model.model2.getName());
            ImageUtil.loadForumCover(mContext, coverUrl2, viewHolder.mIvCover2);
            viewHolder.mIvCover1.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, BoardsActivity.class);
                intent.putExtra(INTENT_FORUM_ID, model.model1.getId());
                intent.putExtra(INTENT_FORUM_TITLE, model.model1.getName());
                LogUtil.dd("forum----", model.model1.getName());
                mContext.startActivity(intent);
            });
            viewHolder.mIvCover2.setOnClickListener(v -> {
                if (model.model2.getId() == 0) {
                    SnackBarUtil.notice(mActivity, "都说了敬请期待..");
                    return;
                }
                Intent intent = new Intent(mContext, BoardsActivity.class);
                intent.putExtra(INTENT_FORUM_ID, model.model2.getId());
                intent.putExtra(INTENT_FORUM_TITLE, model.model2.getName());
                LogUtil.dd("forum2----", model.model1.getName());
                mContext.startActivity(intent);

            });
        }

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_cover_1)
        ImageView mIvCover1;
        @BindView(R.id.tv_name_1)
        TextView mTvName1;
        @BindView(R.id.iv_cover_2)
        ImageView mIvCover2;
        @BindView(R.id.tv_name_2)
        TextView mTvName2;

        ViewHolder(View view) {
            super(view);
        }
    }
}
