package com.twtstudio.bbs.bdpqchen.bbs.commons.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.viewholder.BaseViewHolder;
import com.twtstudio.bbs.bdpqchen.bbs.commons.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_CREATE_THREAD;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_FOOTER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_NORMAL;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ITEM_UPDATE_AVAILABLE;

/**
 * Created by bdpqchen on 17-4-27.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener {

    protected Context mContext;
    protected List<T> mDataSet = new ArrayList<>();
    protected List<T> mOldDataSet = new ArrayList<>();
    private boolean isShowFooter = false;
    private boolean isShowHeader = false;
    private OnItemClickListener mOnItemClickListener = null;
    protected int mPage = 0;
    private boolean mCreateThread, mUpdateAvailable = false;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public BaseAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        if (isShowFooter || isShowHeader) {
            if (mDataSet == null || mDataSet.size() == 0) {
                return 0;
            } else {
                return mDataSet.size() + 1;
            }
        }
        return mDataSet.size();
    }

    public void refreshList(List<T> items) {

//        mOldDataSet = mDataSet;
//        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffChecker<>(mOldDataSet, items), true);
        mDataSet.clear();
        mDataSet.addAll(items);
        notifyDataSetChanged();
//        result.dispatchUpdatesTo(this);
    }

    public void addList(List<T> items) {
        final int oldSize = mDataSet.size();
        mDataSet.addAll(items);
        final int newSize = mDataSet.size();
        notifyDataSetChanged();
//        notifyItemRangeChanged(oldSize + 1, newSize);
    }

    public int getDataListSize() {
        return mDataSet.size();
    }

    public void addFirst(List<T> items) {
        mDataSet.addAll(0, items);
        notifyDataSetChanged();
    }

    public void addFirst(T item) {
        mDataSet.add(0, item);
        notifyDataSetChanged();
    }

    public void addList(List<T> items, int page) {
        mPage = page;
        addList(items);
    }

    @Override
    public int getItemViewType(int position) {
        //Position = 0 and have to display an item.
        if (mUpdateAvailable && position == 0) {
            return ITEM_UPDATE_AVAILABLE;
        }
        if (mCreateThread && ((position == 1 && mUpdateAvailable) || (!mUpdateAvailable && position == 0))) {
            return ITEM_CREATE_THREAD;
        }
        if (mDataSet != null && mDataSet.size() > 0) {
            if (position + 1 == getItemCount()) {
                return ITEM_FOOTER;
            } else {
                return ITEM_NORMAL;
            }
        }
        return 0;
    }

    public boolean isShowFooter() {
        return isShowFooter;
    }

    public void setShowFooter(boolean showFooter) {
        isShowFooter = showFooter;
    }

    public boolean isShowHeader() {
        return isShowHeader;
    }

    public void setShowHeader(boolean showHeader) {
        isShowHeader = showHeader;
    }

    public void clearAll() {
        mDataSet.clear();
    }

    public void addOne(T model) {
        mDataSet.add(model);
        notifyItemInserted(mDataSet.size() - 1);
    }

    public void setCreateThread(boolean b) {
        this.mCreateThread = b;
    }

    public void setUpdateAvailable(boolean b) {
        this.mUpdateAvailable = b;
    }

    public void setDataSets(List<T> list) {
        mDataSet = list;
        notifyDataSetChanged();
    }

    /**
     * Use for DiffUtil to calculate different items,
     * so this method should't use notifyDataSetChanged().
     */
    public void replaceDataSets(List<T> newList) {
        mDataSet.clear();
        mDataSet.addAll(newList);
    }

    public List<T> getDataSets() {
        return mDataSet;
    }
}
