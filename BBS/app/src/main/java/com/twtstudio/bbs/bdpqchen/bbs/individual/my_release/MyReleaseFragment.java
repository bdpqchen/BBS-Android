package com.twtstudio.bbs.bdpqchen.bbs.individual.my_release;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Arsener on 2017/5/28.
 */

public class MyReleaseFragment extends BaseFragment<MyReleasePresenter> implements MyReleaseContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private LinearLayoutManager layoutManager;
    private List<MyReleaseModel> data = new ArrayList<>();
    private MyRecyclerAdapter myRecyclerAdapter;
    private EndlessRecyclerOnScrollListener eros;

    public static MyReleaseFragment newInstance() {
        MyReleaseFragment fragment = new MyReleaseFragment();
        return fragment;
    }

    @Override
    protected void initFragment() {

        myRecyclerAdapter = new MyRecyclerAdapter(getActivity(), data);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(myRecyclerAdapter);
        srl.setOnRefreshListener(this);
        eros = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                mPresenter.getMyReleaseList();
            }
        };
        rv.addOnScrollListener(eros);
//
//        View footer = LayoutInflater.from(MyReleaseActivity.this).inflate(R.layout.footer_view, rv, false);
//        myRecyclerAdapter.setFooterView(footer);

        mPresenter.initMyReleaseList();
    }

    @Override
    public void onError(Throwable throwable) {
        Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        data.clear();
        eros.restart();
        mPresenter.initMyReleaseList();
        srl.setRefreshing(false);
        myRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearMyReleaseList() {
        myRecyclerAdapter.clear();
    }

    @Override
    public void showMyReleaseList(List<MyReleaseModel> data) {
        myRecyclerAdapter.addItems(data);
//        View footer = LayoutInflater.from(MyReleaseActivity.this).inflate(R.layout.footer_view, rv, false);
//        myRecyclerAdapter.setFooterView(footer);
        myRecyclerAdapter.notifyDataSetChanged();
//        myRecyclerAdapter.removeFooterView();
//        myRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_release;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }
}
