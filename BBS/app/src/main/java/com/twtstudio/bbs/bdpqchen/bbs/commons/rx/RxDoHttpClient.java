package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.old.RegisterOldModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.IdentifyModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.retrieve.RetrieveActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.retrieve.RetrieveModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.create_thread.CreateThreadModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.PostModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.UploadImageModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.forum.ForumModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.MyReleaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.my_reply.MyReplyModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.star.StarModel;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainModel;

import java.io.File;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class RxDoHttpClient<T> {

//    public static final String BASE = "https://bbs.twtstudio.com";
    public static final String BASE = "https://bbs.tju.edu.cn";
    public static final String BASE_URL = BASE + "/api/";
    private Retrofit mRetrofit;
    public BaseApi mApi;
    public ResponseTransformer<T> mTransformer;
    public SchedulersHelper mSchedulerHelper;

    // 由于https在连接的过程中会遇到
    //javax.net.ssl.SSLHandshakeException: java.security.cert.CertPathValidatorException: Trust anchor for certification path not found.
    //由于无证书的连接是不可信的，在此，建立Okhttp3连接时，选择信任所有的证书。参照
    //https://blog.ijustyce.win/post/retrofit2%E4%B9%8Bhttps.html
    public static OkHttpClient.Builder getUnsafeBuilder() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);

            builder.hostnameVerifier((hostname, session) -> true);
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            okHttpClient.sslSocketFactory(sslSocketFactory);
            okHttpClient.protocols(Collections.singletonList(Protocol.HTTP_1_1));
            okHttpClient.hostnameVerifier((hostname, session) -> true);
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RxDoHttpClient() {

        Interceptor mTokenInterceptor = chain -> {
            Request originalRequest = chain.request();
            Request authorised = originalRequest.newBuilder()
                    .header(Constants.NET_RETROFIT_HEADER_TITLE, getLatestAuthentication())
                    .build();
            return chain.proceed(authorised);
        };
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = getUnsafeBuilder()
                .addInterceptor(interceptor)
                .addInterceptor(mTokenInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS)
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

    public Observable<BaseResponse<MainModel>> getMainData() {
        return mApi.getMainData("Mobile");
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

    public Observable<BaseResponse<RetrieveModel>> doRetrieveUsername(Bundle bundle) {
        return mApi.doRetrieveUsername(bundle.getString(RetrieveActivity.BUNDLE_STU_NUM),
                bundle.getString(RetrieveActivity.BUNDLE_USERNAME),
                bundle.getString(RetrieveActivity.BUNDLE_REAL_NAME),
                bundle.getString(RetrieveActivity.BUNDLE_CID));

    }

    public Observable<BaseResponse<BaseModel>> resetPassword(Bundle bundle) {
        return mApi.resetPassword(bundle.getString(Constants.BUNDLE_UID), Constants.BUNDLE_TOKEN, Constants.PASSWORD);
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

    public Observable<BaseResponse<List<MyReleaseModel>>> getMyReleaseList(int page) {
        return mApi.getMyReleaseList(getLatestAuthentication(), String.valueOf(page));
    }

    public Observable<BaseResponse<List<MyReplyModel>>> getMyReplyList(int page) {
        return mApi.getMyReplyList(getLatestAuthentication(), String.valueOf(page));
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

    public Observable<BaseResponse<List<StarModel>>> getStarThreadList(){
        return mApi.getStarThreadList();
    }

    public Observable<BaseResponse<BaseModel>> starThread(int id) {
        return mApi.starThread(getLatestAuthentication(), id);
    }

    public Observable<BaseResponse<BaseModel>> unStarThread(int id) {
        return mApi.unStarThread(getLatestAuthentication(), id);
    }

    public Observable<BaseResponse<RegisterOldModel>> doRegisterOld(Bundle bundle) {
        return mApi.doRegisterOld(bundle.getString(Constants.TOKEN),
                bundle.getString(Constants.BUNDLE_REGISTER_USERNAME),
                bundle.getString(Constants.PASSWORD),
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

}
