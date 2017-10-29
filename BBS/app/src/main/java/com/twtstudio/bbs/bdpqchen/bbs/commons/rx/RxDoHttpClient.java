package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import android.os.Bundle;

import com.google.gson.GsonBuilder;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.old.RegisterOldModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.IdentifyModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.retrieve.RetrieveModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
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
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchThreadModel;
import com.twtstudio.bbs.bdpqchen.bbs.search.model.SearchUserModel;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.CID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PASSWORD;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.REAL_NAME;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.STUNUM;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.TOKEN;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.UID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.USERNAME;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class RxDoHttpClient {

    public static final String BASE_HOST = Constants.BASE_HOST;
    public static final String BASE = "https://" + BASE_HOST;
    public static final String BASE_URL = BASE + "/api/";
    private BaseApi mApi;
    private static RxDoHttpClient sINSTANCE;

    private RxDoHttpClient() {
        Interceptor mTokenInterceptor = chain -> {
            Request originalRequest = chain.request();
            Request authorised = originalRequest.newBuilder()
                    .header(Constants.NET_RETROFIT_HEADER_TITLE, getLatestAuthentication())
                    .build();
            return chain.proceed(authorised);
        };
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(mTokenInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        GsonBuilder gson = new GsonBuilder().registerTypeHierarchyAdapter(BaseResponse.class, new ErrorJsonAdapter());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(DirtyJsonConverter.create())
                .addConverterFactory(GsonConverterFactory.create(gson.create()))
                .build();
        mApi = retrofit.create(BaseApi.class);

    }

    public static RxDoHttpClient getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new RxDoHttpClient();
        }
        return sINSTANCE;
    }

    private String getLatestAuthentication() {
        return PrefUtil.getAuthUid() + "|" + PrefUtil.getAuthToken();
//        return PrefUtil.getAuthUid() + "" + PrefUtil.getAuthToken();
    }

    public Observable<BaseResponse<LoginModel>> doLogin(String username, String password) {
        return mApi.doLogin(username, password);
    }

    public Observable<BaseResponse<List<ForumModel>>> getForumList() {
        return mApi.getForums();
    }

    public Observable<BaseResponse<List<LatestEntity>>> getLatestList(int page) {
        return mApi.getLatestList("Mobile", String.valueOf(page));
    }

    public Observable<BaseResponse<List<HotEntity>>> getHotList() {
        return mApi.getHotList("Mobile");
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

    public Observable<BaseResponse<IndividualInfoModel>> getIndividualInfo() {
        return mApi.getIndividualInfo(getLatestAuthentication());
    }

    public Observable<BaseResponse<BaseModel>> doUpdateInfo(Bundle bundle, int type) {

        if (type == 1) {
            return mApi.doUpdateInfoNickname(getLatestAuthentication(), bundle.getString(Constants.BUNDLE_NICKNAME, ""));
        } else if (type == 2) {
            return mApi.doUpdateInfoSignature(getLatestAuthentication(), bundle.getString(Constants.BUNDLE_SIGNATURE, ""));
        }
        return mApi.doUpdateInfoAll(getLatestAuthentication(), bundle.getString(Constants.BUNDLE_NICKNAME, ""), bundle.getString(Constants.BUNDLE_SIGNATURE, ""));

    }

    public Observable<BaseResponse<BoardsModel>> getBoardList(int forumId) {
        return mApi.getBoardList(String.valueOf(forumId));
    }

    public Observable<BaseResponse<ThreadListModel>> getThreadList(int threadId, int page) {
        return mApi.getThreadList(getLatestAuthentication(), "Mobile", String.valueOf(threadId), String.valueOf(page));
    }

    public Observable<BaseResponse<List<MessageModel>>> getMessageList(int page) {
        return mApi.getMessageList(getLatestAuthentication(), String.valueOf(page));
    }

    public Observable<BaseResponse<IdentifyModel>> doIdentifyOldUser(String username, String password) {
        return mApi.getIdentifyContent(username, password);

    }

    public Observable<BaseResponse<RetrieveModel>> doRetrievePassword(Bundle bundle) {
        return mApi.doRetrievePassword(
                bundle.getString(STUNUM),
                bundle.getString(USERNAME),
                bundle.getString(REAL_NAME),
                bundle.getString(CID));

    }

    public Observable<BaseResponse<BaseModel>> resetPassword(Bundle bundle) {
        return mApi.resetPassword(bundle.getString(UID), bundle.getString(TOKEN), bundle.getString(PASSWORD));
    }

    public Observable<BaseResponse<BaseModel>> appealPassport(Bundle bundle) {
        return mApi.appealPassport(
                bundle.getString(Constants.BUNDLE_REGISTER_USERNAME),
                bundle.getString(Constants.BUNDLE_REGISTER_CID),
                bundle.getString(Constants.BUNDLE_REGISTER_REAL_NAME),
                bundle.getString(Constants.BUNDLE_REGISTER_STU_NUM),
                bundle.getString(Constants.BUNDLE_EMAIL),
                bundle.getString(Constants.BUNDLE_MESSAGE),
                bundle.getString(Constants.CAPTCHA_ID),
                bundle.getString(Constants.CAPTCHA_VALUE));
    }

    public Observable<BaseResponse<ThreadModel>> getThread(int threadId, int postPage) {
        return mApi.getThread(threadId + "", postPage + "");
    }

    public Observable<BaseResponse<List<PublishEntity>>> getPublishList(int page) {
        return mApi.getPublishList(String.valueOf(page));
    }

    public Observable<BaseResponse<List<ReplyEntity>>> getReplyList(int page) {
        return mApi.getReplyList(String.valueOf(page));
    }

    public Observable<BaseResponse<CreateThreadModel>> doPublishThread(Bundle bundle) {
        if (bundle.getBoolean(Constants.IS_ANONYMOUS, false)) {
            return mApi.doPublishThreadAnonymous(bundle.getInt(Constants.BID),
                    bundle.getString(Constants.TITLE),
                    bundle.getString(Constants.CONTENT), 1);
        }
        return mApi.doPublishThread(getLatestAuthentication(), bundle.getInt(Constants.BID), bundle.getString(Constants.TITLE), bundle.getString(Constants.CONTENT));
    }

    public Observable<BaseResponse<PostModel>> doComment(int threadId, String comment, int replyId, boolean isAno) {
        if (isAno) {
            return mApi.doCommentAnonymous(threadId, comment, replyId, 1);
        }
        return mApi.doComment(getLatestAuthentication(), threadId, comment, replyId);
    }

    public Observable<BaseResponse<List<StarModel>>> getStarThreadList() {
        return mApi.getStarThreadList();
    }

    public Observable<BaseResponse<BaseModel>> starThread(int id) {
        return mApi.starThread(getLatestAuthentication(), id);
    }

    public Observable<BaseResponse<BaseModel>> unStarThread(int id) {
        return mApi.unStarThread(getLatestAuthentication(), id);
    }

    public Observable<BaseResponse<RegisterOldModel>> doRegisterOld(Bundle bundle) {
        return mApi.doRegisterOld(bundle.getString(TOKEN),
                bundle.getString(Constants.BUNDLE_REGISTER_USERNAME),
                bundle.getString(PASSWORD),
                bundle.getString(Constants.BUNDLE_REGISTER_CID),
                bundle.getString(Constants.BUNDLE_REGISTER_REAL_NAME)
        );
    }

    public Observable<BaseResponse<BaseModel>> doClearUnreadMessage() {
        return mApi.doClearUnreadMessage();
    }

    public Observable<BaseResponse<BaseModel>> doUpdatePassword(String newPass, String oldPass) {
        return mApi.doUpdatePassword(newPass, oldPass);
    }

    public Observable<BaseResponse<Integer>> getUnreadCount() {
        return mApi.getUnreadCount();
    }

    public Observable<BaseResponse<BaseModel>> doUpdateAvatar(File file) {
        if (file != null) {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("cropped", file.getName(), imageBody);//imgfile 后台接收图片流的参数名
            List<MultipartBody.Part> parts = builder.build().parts();
            return mApi.doUpdateAvatar(getLatestAuthentication(), parts);
        } else {
            return null;
        }
    }

    public Observable<BaseResponse<UploadImageModel>> uploadImage(String uri) {
        if (uri != null) {
            File file = new File(uri);
            file = new File(file.getAbsolutePath());
//            LogUtil.d("file converted", file);
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("file", file.getName(), imageBody);//imgfile 后台接收图片流的参数名
            List<MultipartBody.Part> parts = builder.build().parts();
            return mApi.uploadImage(getLatestAuthentication(), parts, "image_android");
        } else {
            LogUtil.dd("uri is null!!");
            return null;
        }
    }

    public Observable<BaseResponse<BaseModel>> confirmFriend(int id, int bool) {
        return mApi.confirmFriend(id, bool);
    }

    public Observable<BaseResponse<List<FriendModel>>> getFriendList() {
        return mApi.getFriendList();
    }

    public Observable<BaseResponse<PeopleModel>> getUserInfo(int uid) {
        return mApi.getUserInfo(uid);
    }

    public Observable<BaseResponse<List<LetterModel>>> getLetterList(int uid, int page) {
        return mApi.getLetterList(uid, page);
    }

    public Observable<BaseResponse<BaseModel>> sendLetter(int to_uid, String content) {
        return mApi.sendLetter(to_uid, content);
    }

    public Observable<BaseResponse<BaseModel>> addFriend(int uid, String m) {
        return mApi.addFriend(uid, m);
    }

    public Observable<BaseResponse<BaseModel>> likePost(int pid) {
        return mApi.likePost(pid);
    }

    public Observable<BaseResponse<BaseModel>> likeThread(int tid) {
        return mApi.likeThread(tid);
    }

    public Observable<BaseResponse<BaseModel>> unlikePost(int pid) {
        return mApi.unlikePost(pid);
    }

    public Observable<BaseResponse<BaseModel>> unlikeThread(int tid) {
        return mApi.unlikeThread(tid);
    }

    public Observable<BaseResponse<BaseModel>> deleteThread(int tid) {
        return mApi.deleteThread(tid);
    }

    public Observable<BaseResponse<BaseModel>> deletePost(int pid) {
        return mApi.deletePost(pid);
    }

    public Observable<BaseResponse<List<SearchUserModel>>> searchUser(String username) {
        return mApi.searchUser(username);
    }

    public Observable<BaseResponse<List<SearchThreadModel>>> searchThread(String keyword, int page) {
        return mApi.searchThread(String.valueOf(page), keyword);
    }

}
