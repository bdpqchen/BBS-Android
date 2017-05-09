package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class RxDoHttpClient<T> {

    private static final String BASE_URL = "http://202.113.13.162:8080/";
    private Retrofit mRetrofit;
    public BaseApi mApi;
    public ResponseTransformer<T> mTransformer;
    public SchedulersHelper mSchedulerHelper;

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
                .addConverterFactory(DirtyJsonConverter.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(BaseApi.class);
        mTransformer = new ResponseTransformer<>();
        mSchedulerHelper = new SchedulersHelper();


    }

    public void getDataList(){

    }

    public Observable<BaseResponse<LoginModel>> doLogin(String username, String password) {
        return mApi.doLogin(username, password);
    }

    public Observable<BaseResponse<List<ForumModel>>> getForums(){
        return mApi.getForums();
    }

    public Observable<BaseResponse<RegisterModel>> doRegister(Bundle bundle) {
        return mApi.doRegister(
                bundle.getString(Constants.BUNDLE_REGISTER_USERNAME),
                bundle.getString(Constants.BUNDLE_REGISTER_CID),
                bundle.getString(Constants.BUNDLE_REGISTER_PASSWORD),
                bundle.getString(Constants.BUNDLE_REGISTER_STU_NUM),
                bundle.getString(Constants.BUNDLE_REGISTER_REAL_NAME)
                );
    }

    public Observable<BaseResponse<IndividualInfoModel>> getIndividualInfo(){
        String authentication = PrefUtil.getAuthUid() + "|" + PrefUtil.getAuthToken();
        return mApi.getIndividualInfo(authentication);
    }



}
