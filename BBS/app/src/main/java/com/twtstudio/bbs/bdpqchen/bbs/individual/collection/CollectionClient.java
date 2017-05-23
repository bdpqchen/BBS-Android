package com.twtstudio.bbs.bdpqchen.bbs.individual.collection;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.CollectionBean;
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
 * Created by whm on 2017/5/14.
 **/

class CollectionClient {

    private CollectionPresenter collectionPresenter;
    private OkHttpClient client;
    private String uidToken = PrefUtil.getAuthUid() + "|" + PrefUtil.getAuthToken();

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

    CollectionClient(CollectionPresenter collectionPresenter) {
        this.collectionPresenter = collectionPresenter;


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        client = getUnSaveBuilder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    void loadCollection() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://bbs.twtstudio.com/api/home/")
                .build();
        CollectionApi collectionApi = retrofit.create(CollectionApi.class);


        Call<CollectionBean> call = collectionApi.getCollection(uidToken);
        call.enqueue(new Callback<CollectionBean>() {
            @Override
            public void onResponse(Call<CollectionBean> call, Response<CollectionBean> response) {
                collectionPresenter.setCollectionDate(response.body());
            }

            @Override
            public void onFailure(Call<CollectionBean> call, Throwable t) {
            }
        });
    }


    void deleteCollection(int tid) {

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://bbs.twtstudio.com/api/home/")
                .build();

        CollectionApi collectionApi = retrofit.create(CollectionApi.class);

        Call<SimpleBean> call = collectionApi.deleteCollection(uidToken, String.valueOf(tid));

        call.enqueue(new Callback<SimpleBean>() {
            @Override
            public void onResponse(Call<SimpleBean> call, Response<SimpleBean> response) {
                collectionPresenter.dealDeleteData(response.body());
            }

            @Override
            public void onFailure(Call<SimpleBean> call, Throwable t) {

            }
        });
    }


    // TODO: 2017/5/23 传入Prenster来处理返回数据
    void collectByTid(String tid) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://bbs.twtstudio.com/api/home/")
                .build();
        CollectionApi collectionApi = retrofit.create(CollectionApi.class);
        Call<SimpleBean> call = collectionApi.collectByTid(uidToken, tid);

        call.enqueue(new Callback<SimpleBean>() {
            @Override
            public void onResponse(Call<SimpleBean> call, Response<SimpleBean> response) {
                collectionPresenter.dealCollectData(response.body());
            }

            @Override
            public void onFailure(Call<SimpleBean> call, Throwable t) {

            }
        });
    }
}
