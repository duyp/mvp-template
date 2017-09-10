package com.duyp.architecture.mvp.dagger.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.dagger.qualifier.OkHttpAuth;
import com.duyp.architecture.mvp.dagger.scopes.UserScope;
import com.duyp.architecture.mvp.data.local.user.UserRepo;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.data.remote.RemoteConstants;
import com.duyp.architecture.mvp.data.remote.ServiceFactory;
import com.duyp.architecture.mvp.data.remote.UserService;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by air on 5/1/17.
 * Module for user component
 */

@Module
public class UserModule {

    private final String mToken;

    public UserModule(@NonNull String token) {
        mToken = token;
    }

    @UserScope
    @Provides
    User provideUser(UserRepo userRepo) {
        return userRepo.getUser();
    }

    @Provides
    @UserScope
    @OkHttpAuth
    OkHttpClient provideOkHttpClientNoAuth(Context context) {
        OkHttpClient.Builder builder = ServiceFactory.makeOkHttpClientBuilder(context);
        builder.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader(RemoteConstants.HEADER_AUTH, mToken)
                    .build();
            return chain.proceed(request);
        });
        return builder.build();
    }

    @Provides
    @UserScope
    UserService provideUserService(Gson gson, @OkHttpAuth OkHttpClient okHttpClient) {
        return ServiceFactory.makeService(UserService.class, gson, okHttpClient);
    }
}