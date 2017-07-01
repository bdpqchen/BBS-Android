package com.twtstudio.bbs.bdpqchen.bbs.individual.star;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-6-29.
 */

public class StarActivity extends BaseActivity<StarPresenter> implements StarContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_star)
    RecyclerView mRvStar;
    @BindView(R.id.srl_star)
    SwipeRefreshLayout mSrlStar;

    private StarAdapter mAdapter;
    private boolean mRefreshing = false;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_star;
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
        getActivityComponent().inject(this);
    }

    @Override
    protected Activity supportSlideBack() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new StarAdapter(this, mPresenter);
        mRvStar.setAdapter(mAdapter);
        mRvStar.addItemDecoration(new RecyclerViewItemDecoration(1));
        mRvStar.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mSrlStar.setOnRefreshListener(()-> {
            mRefreshing = true;
            mPresenter.getStarList();
            setRefresh();
        });
        mPresenter.getStarList();
    }

    private void setRefresh() {
        mSrlStar.setRefreshing(mRefreshing);
    }

    @Override
    public void onGetStarList(List<StarModel> list) {
        LogUtil.dd("onGetStarList");
        if (list != null && list.size() > 0){
            if (mRefreshing){
                mRefreshing = false;
                setRefresh();
                mAdapter.refreshList(list);
            }else{
                mAdapter.addList(list);
            }
        }
    }

    @Override
    public void onGetStarFailed(String m) {
        LogUtil.dd("onGetStarFailed" + m);
    }

    @Override
    public void onStar(int position) {
        mAdapter.updateStatus(position, 0);
        showSnack("已收藏");
    }

    @Override
    public void onStarFailed(String m) {
        showErrorSnack(m);
    }

    @Override
    public void onUnStar(int position) {
        mAdapter.updateStatus(position, 1);
        showSnack("已取消收藏");
    }

    @Override
    public void onUnStarFailed(String s) {
        showErrorSnack(s);
    }

    private void showSnack(String s){
        SnackBarUtil.normal(mActivity, s);
        mAdapter.notifyDataSetChanged();
    }
    private void showErrorSnack(String s){
        SnackBarUtil.error(mActivity, s);
        mAdapter.notifyDataSetChanged();
    }

}
