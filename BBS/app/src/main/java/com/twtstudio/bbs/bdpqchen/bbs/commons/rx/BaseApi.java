package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by bdpqchen on 17-4-27.
 */

public interface BaseApi {

    @FormUrlEncoded
    @POST("login")
    Observable<BaseResponse<LoginModel>> doLogin(@Field("username") String username, @Field("password") String password);

    @GET("forum")
    Observable<BaseResponse<List<ForumModel>>> getForums();
}
