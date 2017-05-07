package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class RxBaseResponseTransformer<T> implements Function< RxBaseResponse<T>, T>, ObservableTransformer<RxBaseResponse<LoginModel>, Object> {

    @Override
    public T apply(@NonNull RxBaseResponse<T> tRxBaseResponse) throws Exception {
        if (tRxBaseResponse.getErr() == 1001){
            LogUtil.d("11111111111111111111111");
            return tRxBaseResponse.getData();
        }else{
            throw new RxBaseResponseException(tRxBaseResponse);
        }
    }

    @Override
    public ObservableSource<Object> apply(@NonNull Observable<RxBaseResponse<LoginModel>> observable) {
        return null;
    }

}
