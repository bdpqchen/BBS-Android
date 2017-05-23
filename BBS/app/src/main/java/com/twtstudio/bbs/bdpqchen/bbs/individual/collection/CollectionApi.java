package com.twtstudio.bbs.bdpqchen.bbs.individual.collection;

import com.twtstudio.bbs.bdpqchen.bbs.individual.model.CollectionBean;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.SimpleBean;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by whm on 2017/5/14.
 **/

interface CollectionApi {
    @GET("collection")
    Call<CollectionBean> getCollection(@Header("authentication") String uidToken);


    @DELETE("collection/{tid}")
    Call<SimpleBean> deleteCollection(@Header("authentication") String uidToken, @Path("tid") String tid);

    @FormUrlEncoded
    @POST("collection")
    Call<SimpleBean> collectByTid(@Header("authentication") String uidToken, @Field("tid") String tid);
}
