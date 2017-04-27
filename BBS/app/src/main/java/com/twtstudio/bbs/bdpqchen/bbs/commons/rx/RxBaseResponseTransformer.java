package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class RxBaseResponseTransformer<T> implements Function< RxBaseResponse<T>, T> {

    @Override
    public T apply(@NonNull RxBaseResponse<T> tRxBaseResponse) throws Exception {
        if (tRxBaseResponse.getError_code() == -1){
            return tRxBaseResponse.getData();
        }else{
            throw new RxBaseResponseException(tRxBaseResponse);
        }
    }
}
