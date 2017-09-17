package com.duyp.architecture.mvp.dagger.module;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.dagger.qualifier.ApplicationContext;
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
    LiveData<User> provideUser(UserRepo userRepo) {
        return userRepo.getUserLiveData();
    }

    @Provides
    @UserScope
    @OkHttpAuth
    OkHttpClient provideOkHttpClientNoAuth(@ApplicationContext Context context) {
        return ServiceFactory.makeOkHttpClientBuilder(context, mToken).build();
    }

    @Provides
    @UserScope
    UserService provideUserService(Gson gson, @OkHttpAuth OkHttpClient okHttpClient) {
        return ServiceFactory.makeService(UserService.class, gson, okHttpClient);
    }
}