package com.twtstudio.bbs.bdpqchen.bbs.individual.collection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.CollectionBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HP on 2017/5/14.
 **/

public class CollectionAdapter extends RecyclerView.Adapter {
    Context context;
    CollectionBean collectionBean;

    public CollectionAdapter(Context context, CollectionBean collectionBean) {
        this.context = context;
        this.collectionBean = collectionBean;
    }

    public class CollectionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.collection_avatar)
        CircleImageView collection_avatar;
        @BindView(R.id.collection_author_name)
        TextView collection_author_name;
        @BindView(R.id.collection_delete)
        ImageView collection_delete;
        @BindView(R.id.collection_summary)
        TextView collecion_summary;
        @BindView(R.id.collection_time)
        TextView collection_time;

        public CollectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_collection, parent, false);
        return new CollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int positionFianl = position;
        String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").
                format(new java.util.Date(collectionBean.data.get(positionFianl).t_create * 1000));
        CollectionViewHolder collectionViewHolder = (CollectionViewHolder) holder;
        collectionViewHolder.collection_author_name.setText(collectionBean.data.get(positionFianl).author_nickname);
        collectionViewHolder.collecion_summary.setText(collectionBean.data.get(positionFianl).title);
        collectionViewHolder.collection_time.setText(date);
    }

    @Override
    public int getItemCount() {
        if (collectionBean == null || collectionBean.data == null)
            return 0;
        return collectionBean.data.size();
    }
}
