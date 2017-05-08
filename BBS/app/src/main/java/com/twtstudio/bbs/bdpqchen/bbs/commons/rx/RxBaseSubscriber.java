package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import android.content.Context;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;


import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by bdpqchen on 17-4-27.
 */

public abstract class RxBaseSubscriber<T> extends DisposableObserver<T> {

    private Context mContext;

    public RxBaseSubscriber(){

    }

    public RxBaseSubscriber(Context context) {
        mContext = context;
    }

    @Override
    public void onNext(T t) {
        LogUtil.d("onNext()");
        _onNext(t);
    }

    @Override
    public void onError(Throwable throwable) {
        LogUtil.d("onError()");
        // TODO: 17-4-27 无网络请求监听，扼杀在请求阶段
        String msg = "";
        if (throwable instanceof SocketTimeoutException) {
            msg = "网络请求超时...请重试";
        } else if (throwable instanceof UnknownHostException) {
            msg = "找不到服务器了..";
        } else if (throwable instanceof RxBaseResponseException){
            if (((RxBaseResponseException) throwable).getErrorCode() == 1000){
                // TODO: 17-4-27 状态码对应错误信息
                msg = throwable.getMessage();
            }
        }else{
            msg = "网络错误";
            HttpException httpException = (HttpException)throwable;
            httpException.code();
            // TODO: 17-5-8 定义各种网络请求错误
            switch (httpException.code()){
                case 500:
                    msg = "网络错误！500";
                    break;

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
