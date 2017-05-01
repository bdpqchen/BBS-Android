package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class RxSchedulersHelper {

    public static <T> ObservableTransformer<T, T> io_main() {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        //有些Observable会依赖一些资源，当该Observable完成后释放这些资源。
                        //如果释放资源比较耗时的话，可以通过unSubscribeOn来指定释放资源代码执行的线程。
                        .unsubscribeOn(Schedulers.newThread());
            }
        };
    }





}
