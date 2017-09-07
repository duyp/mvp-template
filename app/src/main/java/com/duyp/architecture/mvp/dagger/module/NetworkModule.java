package com.duyp.architecture.mvp.dagger.module;

import android.content.Context;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by air on 4/30/17.
 */

@Module
public class NetworkModule {

//    protected static final String NAME_NETWORK_CONTEXT = "NAME_NETWORK_CONTEXT";
//    protected static final String NAME_NETWORK_RETROFIT_NO_AUTH = "NAME_NETWORK_RETROFIT_NO_AUTH";
//    protected static final String NAME_NETWORK_RETROFIT_FIREBASE = "NAME_NETWORK_RETROFIT_FIREBASE";
//    protected static final String NAME_NETWORK_RETROFIT_GEOIP = "NAME_NETWORK_RETROFIT_GEOIP";
//    protected static final String NAME_NETWORK_OK_HTTP_CLIENT_NO_AUTH = "NAME_NETWORK_OK_HTTP_CLIENT_NO_AUTH";
//
//    protected Context context;
//
//    public NetworkModule(Context context) {
//        this.context = context;
//    }
//
//    @Provides
//    @Named(NAME_NETWORK_CONTEXT)
//    public Context providesContext() {
//        return context;
//    }
//
//    @Provides
//    @Named(NAME_NETWORK_OK_HTTP_CLIENT_NO_AUTH)
//    public OkHttpClient provideOkHttpClientNoAuth() {
//        OkHttpClient.Builder httpClient =  new OkHttpClient.Builder()
//                .readTimeout(0, TimeUnit.NANOSECONDS)
//                .connectTimeout(Constants.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
//                .writeTimeout(Constants.TIMEOUT_CONNECTION, TimeUnit.SECONDS);
//
////                .sslSocketFactory(getSSLSocketFactory())
//        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//            httpClient.addInterceptor(logging);
//        }
//        return httpClient.build();
//    }
//
//    @Provides
//    @Singleton
//    @Named(NAME_NETWORK_RETROFIT_NO_AUTH)
//    public Retrofit provideRetrofitNoAuth(Gson gson, @Named(NAME_NETWORK_OK_HTTP_CLIENT_NO_AUTH) OkHttpClient okHttpClient) {
//        return new Retrofit.Builder()
//                .baseUrl(ConstantApi.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(okHttpClient)
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    @Named(NAME_NETWORK_RETROFIT_FIREBASE)
//    public Retrofit provideRetrofitFirebase(Gson gson, @Named(NAME_NETWORK_OK_HTTP_CLIENT_NO_AUTH) OkHttpClient okHttpClient) {
//        return new Retrofit.Builder()
//                .baseUrl(ConstantApi.BASE_FIREBASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(okHttpClient)
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    @Named(NAME_NETWORK_RETROFIT_GEOIP)
//    public Retrofit provideRetrofitGeoIp(Gson gson, @Named(NAME_NETWORK_OK_HTTP_CLIENT_NO_AUTH) OkHttpClient okHttpClient) {
//        return new Retrofit.Builder()
//                .baseUrl(ConstantApi.BASE_URL_IPGEO)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(okHttpClient)
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    public IpGeoService provideIpGeoService(@Named(NAME_NETWORK_RETROFIT_GEOIP) Retrofit retrofit) {
//        return retrofit.create(IpGeoService.class);
//    }
//
//    @Provides
//    @Singleton
//    public AskTutorService provideAskTutorService(@Named(NAME_NETWORK_RETROFIT_NO_AUTH) Retrofit retrofit) {
//        return retrofit.create(AskTutorService.class);
//    }
}
