package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginModel;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by bdpqchen on 17-4-27.
 */

public interface BaseApi {

    @FormUrlEncoded
    @POST("login")
    Observable<RxBaseResponse<LoginModel>> doLogin(@Field("username") String username, @Field("password") String password);



}
