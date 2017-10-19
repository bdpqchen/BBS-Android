package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.old.RegisterOldModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.IdentifyModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.retrieve.RetrieveModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.create_thread.CreateThreadModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.PostModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.UploadImageModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.friend.FriendModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.letter.LetterModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.publish.PublishEntity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.reply.ReplyEntity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.star.StarModel;
import com.twtstudio.bbs.bdpqchen.bbs.main.hot.HotEntity;
import com.twtstudio.bbs.bdpqchen.bbs.main.latest.LatestEntity;
import com.twtstudio.bbs.bdpqchen.bbs.people.PeopleModel;
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchUserModel;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ANONYMOUS;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.BUNDLE_TOKEN;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.BUNDLE_UID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.CAPTCHA_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.CAPTCHA_VALUE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.CID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.CONFIRM;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.CONTENT;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.EMAIL;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.FRIEND_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.KEYWORD;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MESSAGE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.NET_RETROFIT_HEADER_REQUEST;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.NET_RETROFIT_HEADER_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PASSWORD;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.REAL_NAME;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.REPLY_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.STUNUM;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.TID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.TO_UID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.UID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.USERNAME;

/**
 * Created by bdpqchen on 17-4-27.
 */

public interface BaseApi {

    String header = Constants.NET_RETROFIT_HEADER_TITLE;

    @FormUrlEncoded
    @POST("passport/login")
    Observable<BaseResponse<LoginModel>> doLogin(
            @Field("username") String username,
            @Field("password") String password);

    @GET("forum")
    Observable<BaseResponse<List<ForumModel>>> getForums();

    @FormUrlEncoded
    @POST("passport/register/new")
    Observable<BaseResponse<RegisterModel>> doRegister(
            @Field(Constants.BUNDLE_REGISTER_USERNAME) String string,
            @Field(Constants.BUNDLE_REGISTER_CID) String string1,
            @Field(Constants.BUNDLE_REGISTER_PASSWORD) String string2,
            @Field(Constants.BUNDLE_REGISTER_STU_NUM) String string3,
            @Field(Constants.BUNDLE_REGISTER_REAL_NAME) String string4);

    @FormUrlEncoded
    @POST("passport/register/old")
    Observable<BaseResponse<RegisterOldModel>> doRegisterOld(
            @Field(Constants.TOKEN) String token,
            @Field(Constants.BUNDLE_REGISTER_USERNAME) String string,
            @Field(Constants.BUNDLE_REGISTER_PASSWORD) String string2,
            @Field(Constants.BUNDLE_REGISTER_CID) String string1,
            @Field(Constants.BUNDLE_REGISTER_REAL_NAME) String string4);

    @GET("home")
    Observable<BaseResponse<IndividualInfoModel>> getIndividualInfo(
            @Header(Constants.NET_RETROFIT_HEADER_TITLE) String idAndToken);

    @FormUrlEncoded
    @PUT("home")
    Observable<BaseResponse<BaseModel>> doUpdateInfoNickname(
            @Header(Constants.NET_RETROFIT_HEADER_TITLE) String idAndToken,
            @Field(Constants.BUNDLE_NICKNAME) String nickname
    );

    @FormUrlEncoded
    @PUT("home")
    Observable<BaseResponse<BaseModel>> doUpdateInfoSignature(
            @Header(Constants.NET_RETROFIT_HEADER_TITLE) String idAndToken,
            @Field(Constants.BUNDLE_SIGNATURE) String signature
    );

    @FormUrlEncoded
    @PUT("home")
    Observable<BaseResponse<BaseModel>> doUpdateInfoAll(
            @Header(Constants.NET_RETROFIT_HEADER_TITLE) String idAndToken,
            @Field(Constants.BUNDLE_SIGNATURE) String signature,
            @Field(Constants.BUNDLE_NICKNAME) String nickname
    );


    @FormUrlEncoded
    @PUT("home")
    Observable<BaseResponse<BaseModel>> doUpdatePassword(
            @Field(PASSWORD) String newP,
            @Field(Constants.OLD_PASSWORD) String old);

    @GET("forum/{forumId}")
    Observable<BaseResponse<BoardsModel>> getBoardList(@Path("forumId") String forumId);

    @GET("board/{boardId}/page/{page}")
    Observable<BaseResponse<ThreadListModel>> getThreadList(
            @Header(Constants.NET_RETROFIT_HEADER_TITLE) String idAndToken,
            @Header(Constants.NET_RETROFIT_HEADER_REQUEST) String requestedWith,
            @Path("boardId") String boardId,
            @Path("page") String page);

    @GET("home/message/page/{page}")
    Observable<BaseResponse<List<MessageModel>>> getMessageList(
            @Header(Constants.NET_RETROFIT_HEADER_TITLE) String latestAuthentication,
            @Path("page") String page);

    @FormUrlEncoded
    @POST("passport/login/old")
    Observable<BaseResponse<IdentifyModel>> getIdentifyContent(
            @Field(Constants.BUNDLE_REGISTER_USERNAME) String username,
            @Field(Constants.BUNDLE_REGISTER_PASSWORD) String password);

    @FormUrlEncoded
    @POST("passport/retrieve")
    Observable<BaseResponse<RetrieveModel>> doRetrievePassword(
            @Field(STUNUM) String string,
            @Field(USERNAME) String string1,
            @Field(REAL_NAME) String string2,
            @Field(CID) String string3);

    @FormUrlEncoded
    @POST("passport/reset-pass")
    Observable<BaseResponse<BaseModel>> resetPassword(
            @Field(BUNDLE_UID) String uid,
            @Field(BUNDLE_TOKEN) String token,
            @Field(PASSWORD) String password);

    @FormUrlEncoded
    @POST("passport/appeal")
    Observable<BaseResponse<BaseModel>> appealPassport(
            @Field(USERNAME) String string,
            @Field(CID) String string1,
            @Field(REAL_NAME) String string2,
            @Field(STUNUM) String string3,
            @Field(EMAIL) String string4,
            @Field(MESSAGE) String string5,
            @Field(CAPTCHA_ID) String string6,
            @Field(CAPTCHA_VALUE) String string7);

    @GET("thread/{thread}/page/{page}")
    Observable<BaseResponse<ThreadModel>> getThread(
            @Path("thread") String threadId,
            @Path("page") String postPage);

    @GET("home/publish/thread/page/{page}")
    Observable<BaseResponse<List<PublishEntity>>> getPublishList(
            @Path("page") String page);

    @GET("home/publish/post/page/{page}")
    Observable<BaseResponse<List<ReplyEntity>>> getReplyList(
            @Path("page") String page);

    @FormUrlEncoded
    @POST("board/{bid}")
    Observable<BaseResponse<CreateThreadModel>> doPublishThread(
            @Header(Constants.NET_RETROFIT_HEADER_TITLE) String latestAuthentication,
            @Path("bid") int anInt,
            @Field(TITLE) String string,
            @Field(Constants.CONTENT) String string1);

    @FormUrlEncoded
    @POST("thread/{tid}")
    Observable<BaseResponse<PostModel>> doComment(
            @Header(Constants.NET_RETROFIT_HEADER_TITLE) String latestAuthentication,
            @Path("tid") int threadId,
            @Field(CONTENT) String comment,
            @Field(REPLY_ID) int reply);

    @FormUrlEncoded
    @POST("thread/{tid}")
    Observable<BaseResponse<PostModel>> doCommentAnonymous(
            @Path("tid") int threadId,
            @Field(CONTENT) String comment,
            @Field(REPLY_ID) int reply,
            @Field(ANONYMOUS) int is);

    @FormUrlEncoded
    @POST("home/collection")
    Observable<BaseResponse<BaseModel>> starThread(
            @Header(Constants.NET_RETROFIT_HEADER_TITLE) String head,
            @Field(TID) int id);

    @DELETE("home/collection/{tid}")
    Observable<BaseResponse<BaseModel>> unStarThread(
            @Header(header) String latestAuthentication,
            @Path(TID) int id);

    @POST("home/message/read")
    Observable<BaseResponse<BaseModel>> doClearUnreadMessage();

    @FormUrlEncoded
    @POST("board/{bid}")
    Observable<BaseResponse<CreateThreadModel>> doPublishThreadAnonymous(
            @Path("bid") int anInt,
            @Field(TITLE) String string,
            @Field(CONTENT) String string1,
            @Field(ANONYMOUS) int is);


    @GET("home/message/count")
    Observable<BaseResponse<Integer>> getUnreadCount();

    @Multipart
    @PUT("home/avatar")
    Observable<BaseResponse<BaseModel>> doUpdateAvatar(
            @Header(NET_RETROFIT_HEADER_TITLE) String latestAuthentication,
            @Part List<MultipartBody.Part> partList);

    @Multipart
    @POST("attach")
    Observable<BaseResponse<UploadImageModel>> uploadImage(
            @Header(NET_RETROFIT_HEADER_TITLE) String latestAuth,
            @Part List<MultipartBody.Part> parts,
            @Part("name") String image);

    @GET("home/collection")
    Observable<BaseResponse<List<StarModel>>> getStarThreadList();

    @POST("home/friend/confirm")
    @FormUrlEncoded
    Observable<BaseResponse<BaseModel>> confirmFriend(
            @Field(ID) int id,
            @Field(CONFIRM) int bool);

    @GET("home/friend")
    Observable<BaseResponse<List<FriendModel>>> getFriendList();

    @GET("user/{uid}/home")
    Observable<BaseResponse<PeopleModel>> getUserInfo(
            @Path(UID) int uid);

    @GET("home/message/dialog/{uid}/page/{page}")
    Observable<BaseResponse<List<LetterModel>>> getLetterList(
            @Path(UID) int uid,
            @Path("page") int page);

    @POST("home/message")
    @FormUrlEncoded
    Observable<BaseResponse<BaseModel>> sendLetter(
            @Field(TO_UID) int to_uid,
            @Field(CONTENT) String content);

    @POST("home/friend")
    @FormUrlEncoded
    Observable<BaseResponse<BaseModel>> addFriend(
            @Field(FRIEND_ID) int uid,
            @Field(MESSAGE) String m);

    @PUT("post/{pid}/like")
    Observable<BaseResponse<BaseModel>> likePost(
            @Path(PID) int pid);

    @PUT("thread/{tid}/like")
    Observable<BaseResponse<BaseModel>> likeThread(
            @Path(TID) int tid);

    @DELETE("post/{pid}/like")
    Observable<BaseResponse<BaseModel>> unlikePost(
            @Path(PID) int pid);

    @DELETE("thread/{tid}/like")
    Observable<BaseResponse<BaseModel>> unlikeThread(
            @Path(TID) int tid);

    @GET("index/latest")
    Observable<BaseResponse<List<LatestEntity>>> getLatestList(@Header(NET_RETROFIT_HEADER_REQUEST) String mobile);

    @GET("index/hot")
    Observable<BaseResponse<List<HotEntity>>> getHotList(@Header(NET_RETROFIT_HEADER_REQUEST) String mobile);

    @DELETE("post/{pid}")
    Observable<BaseResponse<BaseModel>> deletePost(@Path(PID) int pid);

    @DELETE("thread/{tid}")
    Observable<BaseResponse<BaseModel>> deleteThread(@Path(TID) int tid);

    @GET("search/user/{username}")
    Observable<BaseResponse<List<SearchUserModel>>> searchUser(@Path(USERNAME) String keyName);

    @GET("search/user/{keyword}")
    Observable<BaseResponse<List<SearchUserModel>>> searchThread(@Path(KEYWORD) String keyword);



}

