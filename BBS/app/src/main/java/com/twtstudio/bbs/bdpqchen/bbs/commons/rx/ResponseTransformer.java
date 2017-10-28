package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;


import android.support.annotation.NonNull;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import io.reactivex.functions.Function;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class ResponseTransformer<T> implements Function<BaseResponse<T>, T> {

    @Override
    public T apply(@NonNull BaseResponse<T> tBaseResponse) throws ResponseException{
        LogUtil.dd("RxBaseTransformer", String.valueOf(tBaseResponse.getErr()));
        if (tBaseResponse.getErr() == 0){
            return tBaseResponse.getData();
        }else{
            throw new ResponseException(tBaseResponse);
        }
    }


}
