package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import android.text.TextUtils;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by bdpqchen on 17-4-27.
 */

public abstract class SimpleObserver<T> extends DisposableObserver<T> {

    public SimpleObserver() {

    }

    @Override
    public void onNext(T t) {
        LogUtil.dd("onNext()  in SimpleObserver");
        if (t != null) {
            _onNext(t);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        LogUtil.dd("onError() in SimpleObserver");
        String msg = throwable.getMessage();
        if (TextUtils.isEmpty(msg)) {
            msg = "网络错误";
        }
        if (throwable instanceof SocketTimeoutException) {
            msg = "网络请求超时...请重试";
        } else if (throwable instanceof UnknownHostException) {
            msg = "找不到服务器了..";
        } else if (throwable instanceof ResponseException) {
            msg = throwable.getMessage();
            LogUtil.dd("response exception is cased");
            LogUtil.dd("the msg is", throwable.getMessage());
        } else if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;
            try {
                String errorBody = exception.response().errorBody().string();
                JSONObject errorJsonObject = new JSONObject(errorBody);
//                int errCode = errorJsonObject.getInt("err");
                msg = errorJsonObject.getString("data");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        _onError(msg);
    }


    @Override
    public void onComplete() {
//        LogUtil.d("onComplete()--->not ed");
    }

    public abstract void _onError(String msg);

    public abstract void _onNext(T t);


}
