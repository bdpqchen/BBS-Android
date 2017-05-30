package com.twtstudio.bbs.bdpqchen.bbs.forum;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-5-3.
 */

public class ForumFragment extends BaseFragment<ForumPresenter> implements ForumContract.View {


    @BindView(R.id.tv_title_toolbar)
    TextView mTvTitleToolbar;
    @BindView(R.id.rv_forum_list)
    RecyclerView mRvForumList;

    ForumAdapter mAdapter;
    Unbinder unbinder;
    Activity mActivity;
    @BindView(R.id.pb_loading_forum)
    ProgressBar mPbLoadingForum;

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
        // TODO: 17-5-27 到底要不要支持本页面刷新
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
    }

    @Override
    public void failedToGetForum(String msg) {
        SnackBarUtil.error(this.getActivity(), msg);
        hideProgressBar();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getForumList();
//        initFragment();

    }

    private void hideProgressBar() {
        mPbLoadingForum.setVisibility(View.GONE);
    }

}
