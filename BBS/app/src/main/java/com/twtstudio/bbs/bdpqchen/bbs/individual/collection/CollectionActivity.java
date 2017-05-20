package com.twtstudio.bbs.bdpqchen.bbs.individual.collection;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.CollectionBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by whm on 2017/5/12.
 **/

public class CollectionActivity extends BaseActivity<CollectionPresenter> implements CollectionContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collection_recyclerView)
    RecyclerView collection_recyclerView;
    @BindView(R.id.collection_no_collection)
    TextView collection_no_collection;
    CollectionBean collectionBean = new CollectionBean();

    CollectionPresenter collectionPresenter = new CollectionPresenter(this);

    CollectionAdapter collectionAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_collection;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("我的收藏");
        return mToolbar;
    }

    @Override
    protected boolean isShowBackArrow() {
        return true;
    }

    @Override
    protected boolean isSupportNightMode() {
        return true;
    }

    @Override
    protected void inject() {

    }

    @Override
    protected Activity supportSlideBack() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        collection_recyclerView.setLayoutManager(linearLayoutManager);
        collectionAdapter = new CollectionAdapter(this, collectionBean, collectionPresenter);
        collection_recyclerView.setAdapter(collectionAdapter);

        collectionPresenter.loadCollections();
    }

    @Override
    public void setCollectionAdapter(CollectionBean collectionBean) {
        this.collectionBean.err = collectionBean.err;
        this.collectionBean.data = collectionBean.data;
        collectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void makeDeleteSuccessToast() {
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNoCollectionVisible() {
        collection_no_collection.setVisibility(View.VISIBLE);
    }

    @Override
    public void setNoCollectionInvisible() {
        collection_no_collection.setVisibility(View.INVISIBLE);
    }
}
