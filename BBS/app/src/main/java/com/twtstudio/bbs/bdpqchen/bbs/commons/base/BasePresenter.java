package com.twtstudio.bbs.bdpqchen.bbs.commons.base;

/**
 * Created by bdpqchen on 17-4-21.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void detachView();
}
