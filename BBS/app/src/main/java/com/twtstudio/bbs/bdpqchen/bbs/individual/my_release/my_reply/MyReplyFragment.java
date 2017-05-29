package com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.my_reply;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
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

    private LinearLayoutManager layoutManager;
    private List<MyReplyModel> data = new ArrayList<>();
    private MyReplyAdapter myReplyAdapter;
    private EndlessRecyclerOnScrollListener eros;

    public static MyReplyFragment newInstance() {
        MyReplyFragment fragment = new MyReplyFragment();
        return fragment;
    }

    @Override
    protected void initFragment() {

        myReplyAdapter = new MyReplyAdapter(getActivity(), data);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(myReplyAdapter);
        srl.setOnRefreshListener(this);
        eros = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                mPresenter.getMyReplyList();
            }
        };
        rv.addOnScrollListener(eros);
//
//        View footer = LayoutInflater.from(MyReleaseActivity.this).inflate(R.layout.footer_view, rv, false);
//        myRecyclerAdapter.setFooterView(footer);

        mPresenter.initMyReplyList();
    }

    @Override
    public void onError(Throwable throwable) {
        Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show();
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
        myReplyAdapter.addItems(data);
//        View footer = LayoutInflater.from(MyReleaseActivity.this).inflate(R.layout.footer_view, rv, false);
//        myRecyclerAdapter.setFooterView(footer);
        myReplyAdapter.notifyDataSetChanged();
//        myRecyclerAdapter.removeFooterView();
//        myRecyclerAdapter.notifyDataSetChanged();
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
