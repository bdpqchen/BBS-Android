package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import android.content.Context;
import android.util.Log;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;


import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by bdpqchen on 17-4-27.
 */

public abstract class SimpleObserver<T> extends DisposableObserver<T> {

    private Context mContext;

    public SimpleObserver() {

    }

    public SimpleObserver(Context context) {
        mContext = context;
    }

    @Override
    public void onNext(T t) {
        LogUtil.d(t);
        LogUtil.d("onNext()");
        _onNext(t);
    }

    @Override
    public void onError(Throwable throwable) {
//        LogUtil.d(throwable.getMessage());
//        LogUtil.d(throwable.getCause());
        LogUtil.dd("onError()");
        // TODO: 17-4-27 无网络请求监听，扼杀在请求阶段
        String msg = "网络错误";
        if (throwable instanceof SocketTimeoutException) {
            msg = "网络请求超时...请重试";
        } else if (throwable instanceof UnknownHostException) {
            msg = "找不到服务器了..";
        } else if (throwable instanceof ResponseException) {
            msg = throwable.getMessage();
        } else if (throwable instanceof HttpException) {

            msg = "网络错误";

        }

        _onError(msg);

        // TODO: 17-5-8 定义各种网络请求错误
    }



    @Override
    public void onComplete() {
//        LogUtil.d("onComplete()--->not ed");
    }

    public abstract void _onError(String msg);

    public abstract void _onNext(T t);


}
