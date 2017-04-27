package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @deprecated
 * Created by bdpqchen on 17-4-27.
 */

public class RxResponseHelper  {

    public static <T> ObservableTransformer<RxBaseResponse<T>, T> handleResult(){
        return new ObservableTransformer<RxBaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<RxBaseResponse<T>> observable) {
                observable.flatMap(new Function<RxBaseResponse<T>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull RxBaseResponse<T> tRxBaseResponse) throws Exception {
//                        if (RxBaseResponse.getError_code() == -1)
                        return null;
                    }
                });
                return null;
            }
        };
    }


}
