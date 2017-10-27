package com.twtstudio.bbs.bdpqchen.bbs.individual.release;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

/**
 * Created by Arsener on 2017/5/13.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0;
    private boolean loading = true;
    private int firstVisibleItem, visibleItemCount, totalItemCount;

    private int page;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
//        log();
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading
                && (totalItemCount - visibleItemCount) <= firstVisibleItem
                && totalItemCount != visibleItemCount) {
            page++;
            onLoadMore();
            loading = true;
        }
    }

    private void log() {
        LogUtil.dd("visibleItemCount", String.valueOf(visibleItemCount));
        LogUtil.dd("totalItemCount", String.valueOf(totalItemCount));
        LogUtil.dd("firstVisibleItem", String.valueOf(firstVisibleItem));
    }

    public void restart() {
        //this.page = 0;
        previousTotal = 0;
    }

    public abstract void onLoadMore();

}
