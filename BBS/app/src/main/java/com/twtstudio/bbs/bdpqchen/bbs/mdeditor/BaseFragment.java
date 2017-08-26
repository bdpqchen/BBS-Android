/*
 * Copyright 2016. SHENQINCI(沈钦赐)<dev@qinc.me>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.twtstudio.bbs.bdpqchen.bbs.mdeditor;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * fragment基类
 * Created by 沈钦赐 on 2016/1/25.
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected View rootView;
    private Unbinder mUnbinder;
    protected abstract int getLayoutId();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        if (rootView == null) {
            rootView = View.inflate(getActivity(), getLayoutId(), null);
            if (rootView == null)
                throw new IllegalStateException(this.getClass().getSimpleName() + ":LayoutID找不到对应的布局");

        }
//        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, rootView);
        initFragment();

    }

    protected abstract void initFragment();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mContext = null;
        rootView = null;
        if (mUnbinder != null)
        mUnbinder.unbind();
    }


    public boolean hasMenu() {
        return false;
    }


    /**
     * 返回键，预留给所在activity调用
     * On back pressed boolean.
     *
     * @return the boolean
     */
    public boolean onBackPressed() {
        return false;
    }

    /**
     * 需要重写hasMenu() 返回True，才会创建菜单
     *
     * @param menu     the menu
     * @param inflater the inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * 需要重写hasMenu() 返回True，才会回调
     * On options item selected boolean.
     *
     * @param item the item
     * @return the boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }




}
