package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;


import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.HttpException;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class ResponseTransformer<T> implements Function<BaseResponse<T>, T> {

    @Override
    public T apply(@NonNull BaseResponse<T> tBaseResponse) throws ResponseException, HttpException{
//        LogUtil.dd("RxBaseTransformer", String.valueOf(tBaseResponse.getErr()));
        if (tBaseResponse.getErr() == 0){
            return tBaseResponse.getData();
        }else{
//            LogUtil.dd(tBaseResponse.getErr());
            throw new ResponseException(tBaseResponse);
        }
    }


}
