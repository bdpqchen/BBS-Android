package com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.my_reply;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Arsener on 2017/5/28.
 */

public class MyReplyFragment extends BaseFragment<MyReplyPresenter> implements MyReplyContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.tv_none_reply)
    TextView mTvNoneReply;

    private LinearLayoutManager layoutManager;
    private List<MyReplyModel> data = new ArrayList<>();
    private MyReplyAdapter myReplyAdapter;
    private EndlessRecyclerOnScrollListener eros;

    public static MyReplyFragment newInstance() {
        return new MyReplyFragment();
    }

    @Override
    protected void initFragment() {

        myReplyAdapter = new MyReplyAdapter(getActivity(), data);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(myReplyAdapter);
        rv.addItemDecoration(new RecyclerViewItemDecoration(1));
        srl.setOnRefreshListener(this);
        srl.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        eros = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                mPresenter.getMyReplyList();
            }
        };
        rv.addOnScrollListener(eros);
        mPresenter.initMyReplyList();
    }

    @Override
    public void onRefresh() {
        data.clear();
        eros.restart();
        mPresenter.initMyReplyList();
        srl.setRefreshing(false);
        myReplyAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearMyReplyList() {
        myReplyAdapter.clear();
    }

    @Override
    public void showMyReplyList(List<MyReplyModel> data) {
        if (data != null && data.size() > 0) {
            myReplyAdapter.addItems(data);
            myReplyAdapter.notifyDataSetChanged();
            mTvNoneReply.setVisibility(View.GONE);
        } else {
            mTvNoneReply.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_myreply;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }


}
