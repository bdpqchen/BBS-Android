package com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.my_reply;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Arsener on 2017/5/28.
 */
public class MyReplyAdapter extends BaseAdapter<MyReplyModel> {

    private View mFooterView;
    private List<MyReplyModel> data;
    private Context context;

    public static int NORMAL_TYPE = 0;//without footer
    public static int FOOTER_TYPE = 1;//with footer

    public MyReplyAdapter(Context context, List<MyReplyModel> data) {

        super(context);
        this.context = context;
        this.data = data;
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public void removeFooterView() {
        Log.d("remove", "removeFooterView: ");
        notifyItemRemoved(2);
    }

    public void clear() {
        data.clear();
    }

    public void addItems(List<MyReplyModel> newData) {
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mFooterView == null) {
            return NORMAL_TYPE;
        }
        if (position == getItemCount() - 1) {
            //最后一个,应该加载Footer
            return FOOTER_TYPE;
        }
        return NORMAL_TYPE;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        //return data.size();
        if (mFooterView == null) {
            return data.size();
        } else {
            return data.size() + 1;
        }
    }

    // 填充onCreateViewHolder方法返回的holder中的控件
    public void onBindViewHolder(BaseViewHolder mholder, int position) {
        // TODO Auto-generated method stub
        //holder.tv.setText(data.get(position));
        if (data != null && data.size() > 0) {
            if (getItemViewType(position) == NORMAL_TYPE) {
                MyReplyAdapter.MyHolder holder = (MyReplyAdapter.MyHolder) mholder;
                holder.tv_title.setText(data.get(position).thread_title);
                holder.tv_title.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                holder.tv_reply.setText(data.get(position).content);
                holder.tv_reply.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                holder.tv_time.setText(StampUtil.getDatetimeByStamp(data.get(position).t_create));
                holder.rlReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ThreadActivity.class);
                        intent.putExtra("intent_thread_id", data.get(position).thread_id);
                        intent.putExtra("intent_thread_title", data.get(position).thread_title);
                        intent.putExtra("intent_thread_floor", data.get(position).floor);
                        context.startActivity(intent);//注意这里的context！！！
                    }
                });
            }
        }
    }

    @Override
    // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 填充布局
        if (mFooterView != null && viewType == FOOTER_TYPE) {
            return new FooterViewHolder(mFooterView);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cv_reply, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    class FooterViewHolder extends BaseViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

    class MyHolder extends BaseViewHolder {

        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_reply)
        TextView tv_reply;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.rl_reply)
        RelativeLayout rlReply;

        public MyHolder(View view) {
            super(view);
        }
    }
}
