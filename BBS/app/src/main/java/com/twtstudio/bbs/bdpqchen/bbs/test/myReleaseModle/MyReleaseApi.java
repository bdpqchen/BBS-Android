package com.twtstudio.bbs.bdpqchen.bbs.test.myReleaseModle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.test.MyReleaseModle;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by Arsener on 2017/5/17.
 */

public interface MyReleaseApi {
    @GET("home/publish/thread/page/{page}")
    Observable<MyReleaseModle> getMyReleaseList(@Header(Constants.NET_RETROFIT_HEADER_TITLE) String idAndToken, @Path("page") String page);

}
