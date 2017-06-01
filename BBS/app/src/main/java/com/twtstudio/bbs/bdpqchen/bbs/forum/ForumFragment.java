package com.twtstudio.bbs.bdpqchen.bbs.forum;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-5-3.
 */

public class ForumFragment extends BaseFragment<ForumPresenter> implements ForumContract.View {


    /*
    **特别紧急重要更新\n
    * 1. 修复大部分手机的闪退、停止运行情况\n
    * 2. 修复无法匿名回复问题\n
    * 3. 修复回帖后出现重复楼层问题\n
    * 4. 修复刷新未加载问题
    * */

    @BindView(R.id.tv_title_toolbar)
    TextView mTvTitleToolbar;
    @BindView(R.id.rv_forum_list)
    RecyclerView mRvForumList;

    ForumAdapter mAdapter;
    Unbinder unbinder;
    Activity mActivity;
    @BindView(R.id.pb_loading_forum)
    ProgressBar mPbLoadingForum;
    @BindView(R.id.srl_forum)
    SwipeRefreshLayout mSrlForum;
    private boolean mRefreshing = false;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_forum;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    public static ForumFragment newInstance() {
        return new ForumFragment();
    }

    @Override
    protected void initFragment() {

        mActivity = this.getActivity();
        mTvTitleToolbar.setText("论坛区");
        mAdapter = new ForumAdapter(mContext, this.getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvForumList.setLayoutManager(manager);
        mRvForumList.setAdapter(mAdapter);

        mSrlForum.setOnRefreshListener(() -> {
            mPresenter.getForumList();
            mRefreshing = true;
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }


    @Override
    public void showForumList(List<ForumModel> forumModel) {
        if (forumModel != null && forumModel.size() != 0) {
            mAdapter.clearAll();
            if (forumModel.size() % 2 != 0) {
                ForumModel model = new ForumModel();
                model.setId(0);
                model.setName("敬请期待");
                model.setInfo("为了对称");
                forumModel.add(model);
            }
            List<TwoForumModel> modelList = new ArrayList<>();
            int twoSize = forumModel.size() / 2;
            for (int i = 0; i < twoSize; i++) {
                TwoForumModel model = new TwoForumModel();
                model.model1 = forumModel.get(i * 2);
                model.model2 = forumModel.get(i * 2 + 1);
                modelList.add(model);
            }
            mAdapter.addList(modelList);
            mAdapter.notifyDataSetChanged();
        }
        hideProgressBar();
        mRefreshing = false;
        mSrlForum.setRefreshing(false);
    }

    @Override
    public void failedToGetForum(String msg) {
        SnackBarUtil.error(this.getActivity(), msg);
        hideProgressBar();
        mSrlForum.setRefreshing(false);
        mRefreshing = false;

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getForumList();

    }

    private void hideProgressBar() {
        mPbLoadingForum.setVisibility(View.GONE);
    }

}
