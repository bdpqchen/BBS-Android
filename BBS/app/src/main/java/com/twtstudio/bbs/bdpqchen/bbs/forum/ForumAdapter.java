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
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_FORUM_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_FORUM_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_NORMAL;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_SIMPLE;


/**
 * Created by bdpqchen on 17-5-11.
 */

public class ForumAdapter extends BaseAdapter<ForumModel> {
    FragmentActivity mActivity;

    boolean isSimple = PrefUtil.isSimpleForum();

    public ForumAdapter(Context context, FragmentActivity activity) {
        super(context);
        mContext = context;
        mActivity = activity;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_NORMAL) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_forum, parent, false);
            return new ViewHolder(view);
        } else if (viewType == ITEM_SIMPLE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_forum_simple, parent, false);
            return new SimpleViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder0, int position) {
        if (mDataSet != null && mDataSet.size() > 0) {
            if (holder0 instanceof ViewHolder) {
                ViewHolder viewHolder = (ViewHolder) holder0;
                ForumModel model = mDataSet.get(position);
                String coverUrl = RxDoHttpClient.BASE_URL + "forum/" + model.getId() + "/cover";
                viewHolder.mForumInfo.setText(model.getInfo());
                ImageUtil.loadForumCover(mContext, coverUrl, viewHolder.mForumCover);
                viewHolder.mForumName.setText(model.getName());
                viewHolder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, BoardsActivity.class);
                    intent.putExtra(INTENT_FORUM_ID, model.getId());
                    intent.putExtra(INTENT_FORUM_TITLE, model.getName());
                    mContext.startActivity(intent);
                });
            }else if (holder0 instanceof SimpleViewHolder){
                SimpleViewHolder viewHolder = (SimpleViewHolder) holder0;
                ForumModel model = mDataSet.get(position);
                viewHolder.mForumInfo.setText(model.getInfo());
                viewHolder.mForumName.setText(model.getName());
                viewHolder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, BoardsActivity.class);
                    intent.putExtra(INTENT_FORUM_ID, model.getId());
                    intent.putExtra(INTENT_FORUM_TITLE, model.getName());
                    mContext.startActivity(intent);
                });
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (isSimple) {
            return ITEM_SIMPLE;
        } else {
            return ITEM_NORMAL;
        }
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.forum_name)
        TextView mForumName;
        @BindView(R.id.forum_cover)
        ImageView mForumCover;
        @BindView(R.id.forum_info)
        TextView mForumInfo;
        ViewHolder(View view) {
            super(view);
        }
    }

    static class SimpleViewHolder extends BaseViewHolder{
        @BindView(R.id.forum_name)
        TextView mForumName;
        @BindView(R.id.forum_info)
        TextView mForumInfo;
        SimpleViewHolder(View view) {
            super(view);
        }
    }
}
