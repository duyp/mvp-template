package com.duyp.architecture.mvp.data.local.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.androidutils.functions.PlainAction;
import com.duyp.androidutils.functions.PlainConsumer;
import com.duyp.architecture.mvp.app.AppDatabase;
import com.duyp.architecture.mvp.data.Constants;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.utils.DbTaskHelper;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by duypham on 9/7/17.
 * User repository for storing and retrieving user data from database / shared preference
 */

public class UserRepo {

    private static final long USER_ID_NOT_EXIST = -1;

    @NonNull
    private final Gson mGson;

    @NonNull
    private final CustomSharedPreferences mSharedPreferences;

    @NonNull
    private final MutableLiveData<User> mUserLiveData;

    @Inject
    public UserRepo(@NonNull CustomSharedPreferences sharedPreferences, @NonNull Gson gson) {
        this.mSharedPreferences = sharedPreferences;
        this.mGson = gson;
        mUserLiveData = new MutableLiveData<>();
    }

    public CustomSharedPreferences getSharedPreferences() {
        return  mSharedPreferences;
    }

    /**
     * Save user to shared preference
     * @param user {@link User} to be saved
     */
    public LiveData<User> setUser(@NonNull User user) {
        mSharedPreferences.setPreferences(Constants.PREF_USER, mGson.toJson(user));
        mUserLiveData.setValue(user);
        return mUserLiveData;
    }

    @Nullable
    public User getUser() {
        String userJson = mSharedPreferences.getPreferences(Constants.PREF_USER, "");
        if (userJson != null && !userJson.equals("")) {
            return fromJson(userJson);
        }
        return null;
    }

    /**
     * Update user data by new user if are the same
     * @param newUser new User data
     * @return true if user updated
     */
    public boolean updateUserIfEquals(@NonNull User newUser) {
        if (newUser.equals(getUser())) {
            setUser(newUser);
            return true;
        }
        return false;
    }

    public LiveData<User> getUserLiveData() {
        return mUserLiveData;
    }

    /**
     * Save user api token to shared preferences
     * @param token user api token
     */
    public void setUserToken(String token) {
        mSharedPreferences.setPreferences(Constants.PREF_USER_TOKEN, token);
    }

    /**
     * @return saved user token
     */
    public String getUserToken() {
        return mSharedPreferences.getPreferences(Constants.PREF_USER_TOKEN, "");
    }

    /**
     * Clear user from database
     */
    public void clearUser() {
        mSharedPreferences.setPreferences(Constants.PREF_USER_ID, USER_ID_NOT_EXIST);
        mSharedPreferences.setPreferences(Constants.PREF_USER_TOKEN, "");
        mSharedPreferences.setPreferences(Constants.PREF_USER, "");
        if (mUserLiveData != null) {
            mUserLiveData.setValue(null);
        }
    }

    /**
     * create User object from json string
     * @param userJson given json
     * @return {@link User} object
     */
    public User fromJson(@NonNull String userJson) {
        return mGson.fromJson(userJson, User.class);
    }

    /**
     * Clone a user
     * @param user given user
     * @return the copy {@link User} of given user
     */
    public User cloneUser(@NonNull User user) {
        String userJson = mGson.toJson(user);
        return fromJson(userJson);
    }
}
