package com.duyp.architecture.mvp.data.local.dao;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvp.base.data.BaseRealmDaoImpl;
import com.duyp.architecture.mvp.base.data.LiveRealmObject;
import com.duyp.architecture.mvp.data.model.User;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by duypham on 9/20/17.
 *
 */

public class UserDao extends BaseRealmDaoImpl<User>{

    @Inject
    public UserDao(Realm realm) {
        super(realm, User.class);
    }

    public LiveRealmObject<User> getUser(String userLogin) {
        return asLiveData(query().equalTo("login", userLogin).findFirst());
    }
}
