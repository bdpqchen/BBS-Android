package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import android.content.Context;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by bdpqchen on 17-4-27.
 */

public abstract class RxBaseSubscriber<T> implements Subscriber<T> {

    private Context mContext;

    public RxBaseSubscriber(Context context) {
        mContext = context;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        LogUtil.d("onSubscribe()");

    }

    @Override
    public void onNext(T t) {
        LogUtil.d("onNext()");
        _onNext(t);
    }

    @Override
    public void onError(Throwable throwable) {
        LogUtil.d("onError()");
        String msg = "";
        throwable.printStackTrace();
        if (throwable instanceof SocketTimeoutException) {
            msg = "网络请求超时...请重试";
        } else if (throwable instanceof UnknownHostException) {
            // TODO: 17-4-27 无网络请求监听，扼杀在请求阶段
            msg = "没有可用网络";
        } else if (throwable instanceof RxBaseResponseException){
            if (((RxBaseResponseException) throwable).getErrorCode() == 1000){
                // TODO: 17-4-27 状态码对应错误信息
                msg = throwable.getMessage();
            }
        }
        _onError(msg);

    }

    @Override
    public void onComplete() {
        LogUtil.d("onComplete()--->not ed");
    }

    public abstract void _onError(String msg);

    public abstract void _onNext(T t);


}
