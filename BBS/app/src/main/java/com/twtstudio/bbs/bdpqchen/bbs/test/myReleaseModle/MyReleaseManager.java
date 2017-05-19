package com.twtstudio.bbs.bdpqchen.bbs.test.myReleaseModle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.test.MyReleaseBean;
import com.twtstudio.bbs.bdpqchen.bbs.test.MyReleaseModle;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arsener on 2017/5/17.
 */

public class MyReleaseManager {
    private static final String BASE_URL = "https://bbs.twtstudio.com/";
    private static final int CONNECT_TIME_OUT = 5;
    private OkHttpClient mClient;
    private Retrofit retrofit;
    private MyReleaseApi myReleaseApi;
    private Observable<MyReleaseModle> mPageObservable;

    private MyReleaseManager() {

        mClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        myReleaseApi = retrofit.create(MyReleaseApi.class);

    }

    public static MyReleaseManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private String getLatestAuthentication(){
        return PrefUtil.getAuthUid() + "|" + PrefUtil.getAuthToken();
    }

    public void getMyReleaseList(Consumer<List<MyReleaseBean>> onNext, Consumer<Throwable> onError, int page) {
        myReleaseApi.getMyReleaseList(getLatestAuthentication(), String.valueOf(page))
                .map(MyReleaseModle::getData)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    private static class SingletonHolder {
        private static final MyReleaseManager INSTANCE = new MyReleaseManager();
    }
}
