package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;


import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class ResponseTransformer<T> implements Function<BaseResponse<T>, T> {

    @Override
    public T apply(@NonNull BaseResponse<T> tBaseResponse) throws ResponseException{
//        LogUtil.d("RxBaseTransformer", tBaseResponse.getErr());
        if (tBaseResponse.getErr() == 0){
            return tBaseResponse.getData();
        }else{
//            LogUtil.d(tBaseResponse.getErr());
            throw new ResponseException(tBaseResponse);
        }
    }


}
