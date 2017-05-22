package com.twtstudio.bbs.bdpqchen.bbs.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Arsener on 2017/5/13.
 */

public class MyReleaseActivity extends BaseActivity<MyReleasePresenter> implements MyReleaseContract.View, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private LinearLayoutManager layoutManager;
    private List<MyReleaseBean> data = new ArrayList<>();
    private MyRecyclerAdapter myRecyclerAdapter;
    private EndlessRecyclerOnScrollListener eros;
    private int page = 0;
    private boolean ready = true;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_release;
    }

    @Override
    protected Toolbar getToolbarView() {
        toolbar.setTitle("我的发布");
        return toolbar;
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
        //setContentView(R.layout.activity_release);

        ButterKnife.bind(this);

        myRecyclerAdapter = new MyRecyclerAdapter(this, data);
        init();

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setHasFixedSize(true);rv.setLayoutManager(layoutManager);
        rv.setAdapter(myRecyclerAdapter);
        srl.setOnRefreshListener(this);
        eros = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {

                //loadMoreData();
            }
        };
        rv.addOnScrollListener(eros);

    }

    public void init() {
//        for (int i = 0; i < 10; i++) {
//            MyReleaseBean rb = new MyReleaseBean();
//            rb.title = "this is " + (page * 10 + i) + " hahaha";
//            rb.visit = page * 10 + i;
//            rb.time = "YY/MM/DD";
//            data.add(rb);
//        }
//
//        page = page + 1;
        data.add(new MyReleaseBean("校友向回学校想在食堂吃个饭，没卡咋办？", 23, "2017年2月21日"));
        data.add(new MyReleaseBean("校友向回学校想在食堂吃个饭，没卡咋办？", 23, "2017年2月21日"));
        data.add(new MyReleaseBean("厉害了word天大！4项成果获得了2016年国家级别的奖项！", 2324, "2017年2月21日"));
        data.add(new MyReleaseBean("校友向回学校想在食堂吃个饭，没卡咋办？", 23, "2017年2月21日"));
        data.add(new MyReleaseBean("校友向回学校想在食堂吃个饭，没卡咋办？", 23, "2017年2月21日"));
        data.add(new MyReleaseBean("厉害了word天大！4项成果获得了2016年国家级别的奖项！", 2324, "2017年2月21日"));
        data.add(new MyReleaseBean("校友向回学校想在食堂吃个饭，没卡咋办？", 23, "2017年2月21日"));
        data.add(new MyReleaseBean("校友向回学校想在食堂吃个饭，没卡咋办？", 23, "2017年2月21日"));
        data.add(new MyReleaseBean("厉害了word天大！4项成果获得了2016年国家级别的奖项！", 2324, "2017年2月21日"));
        data.add(new MyReleaseBean("校友向回学校想在食堂吃个饭，没卡咋办？", 23, "2017年2月21日"));
        data.add(new MyReleaseBean("校友向回学校想在食堂吃个饭，没卡咋办？", 23, "2017年2月21日"));
        data.add(new MyReleaseBean("厉害了word天大！4项成果获得了2016年国家级别的奖项！", 2324, "2017年2月21日"));
        data.add(new MyReleaseBean("校友向回学校想在食堂吃个饭，没卡咋办？", 23, "2017年2月21日"));
        data.add(new MyReleaseBean("校友向回学校想在食堂吃个饭，没卡咋办？", 23, "2017年2月21日"));
        data.add(new MyReleaseBean("厉害了word天大！4项成果获得了2016年国家级别的奖项！", 2324, "2017年2月21日"));
    }

    @Override
    public void onRefresh() {
        data.clear();
        page = 0;
        eros.restart();
        init();
        srl.setRefreshing(false);
        myRecyclerAdapter.notifyDataSetChanged();
    }

    public void loadMoreData(){
        List<MyReleaseBean> moreList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MyReleaseBean rb = new MyReleaseBean();
            rb.title = "this is " + (page * 10 + i) + " hahaha";
            rb.visit = page * 10 + i;
            rb.time = "YY/MM/DD";
            data.add(rb);
        }
        page++;
        data.addAll(moreList);
        View footer = LayoutInflater.from(this).inflate(R.layout.footer_view, rv, false);
        myRecyclerAdapter.setFooterView(footer);
        myRecyclerAdapter.notifyDataSetChanged();
    }

}
