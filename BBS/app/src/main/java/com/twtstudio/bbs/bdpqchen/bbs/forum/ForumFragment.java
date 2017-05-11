package com.twtstudio.bbs.bdpqchen.bbs.forum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

    @Override
    protected void initFragment() {
        mActivity = this.getActivity();
        mTvTitleToolbar.setText("论坛区");
        mPresenter.getForumList();
        mGridLayoutManager = new GridLayoutManager(this.getActivity(), 2, GridLayoutManager.VERTICAL, false);
        mGridLayoutManager.generateDefaultLayoutParams();
        mAdapter = new ForumAdapter(this.getContext());
        mRvForumList.setLayoutManager(mGridLayoutManager);
        mRvForumList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ForumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(mActivity, BoardsActivity.class);
                intent.putExtra(INTENT_FORUM_ID, mAdapter.getItemForumId(position));
                startActivity(intent);
                mAdapter.getItemForumId(position);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);


        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void showForumList(List<ForumModel> forumModel) {
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

    @Override
    public void failedToGetForum(String msg) {
        SnackBarUtil.error(this.getActivity(), msg);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
