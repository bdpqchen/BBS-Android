package com.twtstudio.bbs.bdpqchen.bbs.individual.my_release;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Arsener on 2017/5/28.
 */

public class MyReleaseFragment extends BaseFragment<MyReleasePresenter> implements MyReleaseContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.tv_none_publish)
    TextView mTvNonePublish;
    Unbinder unbinder;

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
        srl.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        eros = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                mPresenter.getMyReleaseList();
            }
        };
        rv.addOnScrollListener(eros);
        mPresenter.initMyReleaseList();
        rv.addItemDecoration(new RecyclerViewItemDecoration(5));
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
        if (data != null && data.size() != 0) {
            myRecyclerAdapter.addItems(data);
            myRecyclerAdapter.notifyDataSetChanged();
            mTvNonePublish.setVisibility(View.GONE);
        }else{
            mTvNonePublish.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_release;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

}
