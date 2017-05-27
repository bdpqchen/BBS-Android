package com.twtstudio.bbs.bdpqchen.bbs.individual.updatePassword;

import com.twtstudio.bbs.bdpqchen.bbs.individual.model.SimpleBean;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HP on 2017/5/23.
 */

public class UpdatePasswordClient {

    UpdatePasswordPresenter updatePasswordPresenter;
    private OkHttpClient client;

    private static OkHttpClient.Builder getUnSaveBuilder() {
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

    public UpdatePasswordClient(UpdatePasswordPresenter updatePasswordPresenter) {
        this.updatePasswordPresenter = updatePasswordPresenter;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = getUnSaveBuilder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    void sendUpdatePasswordInfo(String uidToken, String oldPassword, String newPassword) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://bbs.twtstudio.com/api/")
                .build();
        UpdatePasswordApi updatePasswordApi = retrofit.create(UpdatePasswordApi.class);

        Call<SimpleBean> call = updatePasswordApi.sentUpdatePasswordInfo(uidToken, oldPassword, newPassword);
        call.enqueue(new Callback<SimpleBean>() {
            @Override
            public void onResponse(Call<SimpleBean> call, Response<SimpleBean> response) {
                updatePasswordPresenter.dealFeedbackInfo(response.body());
            }

            @Override
            public void onFailure(Call<SimpleBean> call, Throwable t) {
            }
        });
    }
}
