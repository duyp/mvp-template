package com.duyp.architecture.mvp.dagger.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by air on 5/1/17.
 * Module for user component
 */

@Module
public class UserModule {
//
//    public static final String NAME_RETROFIT_AU = "NAME_RETROFIT_AU";
//    public static final String NAME_OK_HTTP_CLIENT_AUTH = "NAME_OK_HTTP_CLIENT_AUTH";
//
//    @NonNull
//    private String mUserToken;
//
//    @NonNull
//    private User mUser;
//
//    public UserModule(@NonNull User user, @NonNull String token) {
//        this.mUserToken = token;
//        this.mUser = user;
//    }
//
//    @UserScope
//    @Provides
//    User provideUser() {
//        return mUser;
//    }
//
//    @UserScope
//    @Provides
//    @Named(NAME_OK_HTTP_CLIENT_AUTH)
//    OkHttpClient provideOkHttpClientAuth() {
//
//        OkHttpClient.Builder httpClient =  new OkHttpClient.Builder()
//                .addInterceptor(chain -> {
//                    Request request = chain.request().newBuilder()
//                            .addHeader(Constants.AUTHORIZE_HEADER, mUserToken)
//                            .build();
//                    return chain.proceed(request);
//                })
//                .readTimeout(0, TimeUnit.NANOSECONDS)
//                .connectTimeout(Constants.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
//                .writeTimeout(Constants.TIMEOUT_CONNECTION, TimeUnit.SECONDS);
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
//    @UserScope
//    @Provides
//    @Named(NAME_RETROFIT_AU)
//    Retrofit provideRetrofitAuth(Gson gson, @Named(NAME_OK_HTTP_CLIENT_AUTH) OkHttpClient okHttpClient) {
//        return new Retrofit.Builder()
//                .baseUrl(ConstantApi.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(okHttpClient)
//                .build();
//    }
//
//    @UserScope
//    @Provides
//    UserService provideUserService(@Named(NAME_RETROFIT_AU) Retrofit retrofit) {
//        return retrofit.create(UserService.class);
//    }
//
//    @UserScope
//    @Provides
//    UserFirebaseManager provideUserFirebaseManager(Context context, IpGeoService ipGeoService, UserManager userManager) {
//        return new UserFirebaseManager(context, ipGeoService, userManager);
//    }
}