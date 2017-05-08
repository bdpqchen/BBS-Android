package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;


import android.util.Log;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class RxBaseResponseTransformer<T> implements Function<RxBaseResponse<T>, T> {

    @Override
    public T apply(@NonNull RxBaseResponse<T> tRxBaseResponse) throws Exception {
        LogUtil.d("RxBaseTransformer", tRxBaseResponse.getErr());
        if (tRxBaseResponse.getErr() == 0){
            LogUtil.d("11111111111111111111111");
            return tRxBaseResponse.getData();
        }else{
            throw new RxBaseResponseException(tRxBaseResponse);
        }
    }


}
