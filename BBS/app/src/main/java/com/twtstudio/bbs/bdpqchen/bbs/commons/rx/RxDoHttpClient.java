package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginModel;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;
import com.twtstudio.bbs.bdpqchen.bbs.main.latestPost.LatestPostModel;


import java.io.File;
import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class RxDoHttpClient<T> {

//    public static final String BASE_URL = "http://202.113.13.162:8080/";
    //将会遇到证书 CA 问题
    public static final String BASE_URL = "https://bbs.twtstudio.com/";
    private Retrofit mRetrofit;
    public BaseApi mApi;
    public ResponseTransformer<T> mTransformer;
    public SchedulersHelper mSchedulerHelper;

    // 由于https在连接的过程中会遇到
    //javax.net.ssl.SSLHandshakeException: java.security.cert.CertPathValidatorException: Trust anchor for certification path not found.
    //由于无证书的连接是不可信的，在此，建立Okhttp3连接时，选择信任所有的证书。参照
    //https://blog.ijustyce.win/post/retrofit2%E4%B9%8Bhttps.html
    private static OkHttpClient.Builder getUnSaveBuilder() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
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
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RxDoHttpClient(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

//        OkHttpClient client = new OkHttpClient.Builder()
        OkHttpClient client = getUnSaveBuilder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
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

    private String getLatestAuthentication(){
        return PrefUtil.getAuthUid() + "|" + PrefUtil.getAuthToken();
    }


    public Observable<BaseResponse<LoginModel>> doLogin(String username, String password) {
        return mApi.doLogin(username, password);
    }

    public Observable<BaseResponse<List<ForumModel>>> getForumList() {
        return mApi.getForums();
    }
    public Observable<BaseResponse<LatestPostModel> getLatestPost() {
        return mApi.getLatestPost();
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

    public Observable<BaseResponse<IndividualInfoModel>> getIndividualInfo(){
        return mApi.getIndividualInfo(getLatestAuthentication());
    }

    public Observable<BaseResponse<IndividualInfoModel>> doUpdateInfo(Bundle bundle) {
        return mApi.doUpdateInfo(getLatestAuthentication(), bundle.getString(Constants.BUNDLE_NICKNAME, ""), bundle.getString(Constants.BUNDLE_SIGNATURE, ""));
    }


    public Observable<BaseResponse<BaseModel>> doUpdateAvatar(String imagePath) {
        File file = new File(imagePath);//filePath 图片地址
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("img_file", file.getName(), imageBody);//imgfile 后台接收图片流的参数名
        List<MultipartBody.Part> parts = builder.build().parts();
        return mApi.doUpdateAvatar(getLatestAuthentication(), parts);
    }

    public Observable<BaseResponse<BoardsModel>> getBoardList(int forumId) {
        return mApi.getBoardList(String.valueOf(forumId));
    }
    public Observable<BaseResponse<ThreadModel>> getThreadList(int threadId, int page) {
        return mApi.getThreadList(getLatestAuthentication(), String.valueOf(threadId), String.valueOf(page));
    }

}
