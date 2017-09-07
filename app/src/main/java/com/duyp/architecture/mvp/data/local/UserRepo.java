package com.duyp.architecture.mvp.data.local;

import android.support.annotation.NonNull;

import com.duyp.androidutils.CustomSharedPreferences;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by duypham on 9/7/17.
 * User repository for storing and retrieving user data from shared preference
 */

@Singleton
public class UserRepo {

    @NonNull
    private Gson mGson;

    @NonNull
    private CustomSharedPreferences mSharedPreferences;

    @Inject
    public UserRepo(@NonNull CustomSharedPreferences sharedPreferences, @NonNull Gson gson) {
        this.mSharedPreferences = sharedPreferences;
        this.mGson = gson;
    }
}
