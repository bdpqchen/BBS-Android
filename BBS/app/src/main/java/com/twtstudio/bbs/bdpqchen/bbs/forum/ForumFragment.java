package com.twtstudio.bbs.bdpqchen.bbs.forum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-5-3.
 */

public class ForumFragment extends BaseFragment<ForumPresenter> implements ForumContract.View {


    public static final String INTENT_FORUM_ID = "intent_forum_id";
    public static final String INTENT_FORUM_TITLE = "intent_forum_title";
    @BindView(R.id.tv_title_toolbar)
    TextView mTvTitleToolbar;
    @BindView(R.id.rv_forum_list)
    RecyclerView mRvForumList;

    GridLayoutManager mGridLayoutManager;
    ForumAdapter mAdapter;
    Unbinder unbinder;
    Activity mActivity;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_forum;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    public static ForumFragment newInstance(){
        return new ForumFragment();
    }

    @Override
    protected void initFragment() {

        mActivity = this.getActivity();
        mTvTitleToolbar.setText("论坛区");
        mGridLayoutManager = new GridLayoutManager(this.getActivity(), 2, GridLayoutManager.VERTICAL, false);
        mGridLayoutManager.generateDefaultLayoutParams();
        mAdapter = new ForumAdapter(this.getContext());
        mRvForumList.setLayoutManager(mGridLayoutManager);
        mRvForumList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            if (mAdapter.getItemForumId(position) == 0){
                SnackBarUtil.notice(this.getActivity(), "都说了敬请期待..");
                return;
            }
            Intent intent = new Intent(mActivity, BoardsActivity.class);
            intent.putExtra(INTENT_FORUM_ID, mAdapter.getItemForumId(position));
            intent.putExtra(INTENT_FORUM_TITLE, mAdapter.getItemForumTitle(position));
            startActivity(intent);
            mAdapter.getItemForumId(position);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void showForumList(List<ForumModel> forumModel) {
        if (forumModel != null && forumModel.size() != 0){
            mAdapter.clearAll();
            if (forumModel.size() % 2 != 0) {
                ForumModel model = new ForumModel();
                model.setId(0);
                model.setName("敬请期待");
                model.setInfo("为了对称");
                forumModel.add(model);
            }
//        mGridLayoutManager.setSpanCount(forumModel.size() / 2);
            mAdapter.addList(forumModel);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void failedToGetForum(String msg) {
        SnackBarUtil.error(this.getActivity(), msg);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getForumList();
//        initFragment();

    }

    private void hideProgressBar(){

    }
}
