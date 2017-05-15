package com.twtstudio.bbs.bdpqchen.bbs.individual.collection;

import com.twtstudio.bbs.bdpqchen.bbs.individual.model.CollectionBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * Created by HP on 2017/5/14.
 */

public interface CollectionApi {
    @GET("collection")
    Call<CollectionBean> getCollection(@Header("authentication") String uidToken);
}
