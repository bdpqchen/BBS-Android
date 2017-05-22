package com.twtstudio.bbs.bdpqchen.bbs.individual.my_release;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Arsener on 2017/5/13.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyHolder> {

    private Context context;
    private View mFooterView;
    private List<MyReleaseBean> data;

    public static int NORMAL_TYPE = 0;//without footer
    public static int FOOTER_TYPE = 1;//with footer

    public MyRecyclerAdapter(Context context, List<MyReleaseBean> data) {

        super();
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

    @Override
    // 填充onCreateViewHolder方法返回的holder中的控件
    public void onBindViewHolder(MyHolder holder, int position) {
        // TODO Auto-generated method stub
        //holder.tv.setText(data.get(position));
        if (getItemViewType(position) == NORMAL_TYPE) {

            holder.tv_title.setText(data.get(position).title);
            holder.tv_title.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
            Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");
            holder.tv_icon.setTypeface(iconfont);
            holder.tv_visit.setText(String.valueOf(data.get(position).visit));
            holder.tv_time.setText(data.get(position).time);

            holder.cdv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, ContentActivity.class);
//                    intent.putExtra("index", data.get(position).index);
//                    context.startActivity(intent);//注意这里的context！！！
                }
            });
        }
    }

    @Override
    // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 填充布局
        if (mFooterView != null && viewType == FOOTER_TYPE) {
            return new MyHolder(mFooterView);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_cv_release, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_icon)
        TextView tv_icon;
        @BindView(R.id.tv_visit)
        TextView tv_visit;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.cdv)
        CardView cdv;

        public MyHolder(View view) {
            super(view);
            if (view != mFooterView) {
                ButterKnife.bind(this, view);

                cdv.setRadius(0);//设置图片圆角的半径大小
                cdv.setCardElevation(0);//设置阴影部分大小
            }
        }
    }
}
