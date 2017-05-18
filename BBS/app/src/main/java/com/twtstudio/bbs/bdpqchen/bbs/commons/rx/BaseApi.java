package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.MessageModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;
import com.twtstudio.bbs.bdpqchen.bbs.main.historyHot.HistoryHotModel;
import com.twtstudio.bbs.bdpqchen.bbs.main.latestPost.LatestPostModel;
import com.twtstudio.bbs.bdpqchen.bbs.main.topTen.TopTenModel;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by bdpqchen on 17-4-27.
 */

public interface BaseApi {

    @FormUrlEncoded
    @POST("login")
    Observable<BaseResponse<LoginModel>> doLogin(@Field("username") String username, @Field("password") String password);

    @GET("forum")
    Observable<BaseResponse<List<ForumModel>>> getForums();

    @FormUrlEncoded
    @POST("register/new")
    Observable<BaseResponse<RegisterModel>> doRegister(@Field(Constants.BUNDLE_REGISTER_USERNAME) String string,
                                                       @Field(Constants.BUNDLE_REGISTER_CID) String string1,
                                                       @Field(Constants.BUNDLE_REGISTER_PASSWORD) String string2,
                                                       @Field(Constants.BUNDLE_REGISTER_STU_NUM) String string3,
                                                       @Field(Constants.BUNDLE_REGISTER_REAL_NAME) String string4);

    @GET("home")
    Observable<BaseResponse<IndividualInfoModel>> getIndividualInfo(@Header(Constants.NET_RETROFIT_HEADER_TITLE) String idAndToken);

    @FormUrlEncoded
    @PUT("home")
    Observable<BaseResponse<IndividualInfoModel>> doUpdateInfo(
            @Header(Constants.NET_RETROFIT_HEADER_TITLE) String idAndToken,
            @Field(Constants.BUNDLE_NICKNAME) String nickname,
            @Field(Constants.BUNDLE_SIGNATURE) String signature
    );


    @Multipart
    @PUT("user/avatar")
    Observable<BaseResponse<BaseModel>> doUpdateAvatar(
            @Header(Constants.NET_RETROFIT_HEADER_TITLE) String latestAuthentication,
            @Part List<MultipartBody.Part> partList);


    @GET("api/index")
    Observable<BaseResponse<LatestPostModel.DataBean>> getLatestPost();

    @GET("forum/{forumId}")
    Observable<BaseResponse<BoardsModel>> getBoardList(@Path("forumId") String forumId);

    @GET("board/{boardId}/page/{page}")
    Observable<BaseResponse<ThreadModel>> getThreadList(
            @Header(Constants.NET_RETROFIT_HEADER_TITLE) String idAndToken,
            @Path("boardId") String boardId,
            @Path("page") String page);

    @GET("home/message/page/{page}")
    Observable<BaseResponse<List<MessageModel>>> getMessageList(
            @Header(Constants.NET_RETROFIT_HEADER_TITLE) String latestAuthentication,
            @Path("page") String page);

    @GET("topten")
    Observable<BaseResponse<TopTenModel>> getTopTen();
    @GET("historyhot")
    Observable<BaseResponse<HistoryHotModel>> getHistoryHot();

}

