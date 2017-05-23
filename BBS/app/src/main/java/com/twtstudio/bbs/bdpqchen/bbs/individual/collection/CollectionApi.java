package com.twtstudio.bbs.bdpqchen.bbs.individual.collection;

import com.twtstudio.bbs.bdpqchen.bbs.individual.model.CollectionBean;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.UpdatePasswordBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by whm on 2017/5/14.
 **/

interface CollectionApi {
    @GET("collection")
    Call<CollectionBean> getCollection(@Header("authentication") String uidToken);

    @GET("collection/{tid}")
    Call<ResponseBody> deleteCollection(@Header("authentication") String uidToken, @Path("tid") String tid);


}
