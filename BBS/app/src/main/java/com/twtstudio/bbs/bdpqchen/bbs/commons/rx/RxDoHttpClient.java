package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class RxDoHttpClient {

    private static final String BASE_URL = "http://202.113.13.162:8080/";
    private Retrofit mRetrofit;
    public BaseApi mApi;
    public RxBaseResponseTransformer mTransformer;
    public RxSchedulersHelper mSchedulerHelper;


    public <T> ObservableTransformer<RxBaseResponse<T>, T> transformer(){
        return new ObservableTransformer<RxBaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<RxBaseResponse<T>> observable) {


                return Observable.empty();
            }
        };
    }

    public RxDoHttpClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(BaseApi.class);
        mTransformer = new RxBaseResponseTransformer();
        mSchedulerHelper = new RxSchedulersHelper();


//        CompositeSubscription
//        Subscription subscription =
    }

    public void getDataList(){

    }

    public Observable<RxBaseResponse<LoginModel>> doLogin(String username, String password) {
        return mApi.doLogin(username, password);
    }

    public Observable<RxBaseResponse<List<ForumModel>>> getForums(){
        return mApi.getForums();
    }

//    public


}
