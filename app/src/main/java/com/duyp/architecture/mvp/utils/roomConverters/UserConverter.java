package com.duyp.architecture.mvp.utils.roomConverters;

import android.arch.persistence.room.TypeConverter;

import com.duyp.architecture.mvp.data.model.User;
import com.google.gson.Gson;

/**
 * Created by phamd on 9/14/2017.
 */

public class UserConverter {

    @TypeConverter
    public User toUser(String userJson) {
        return new Gson().fromJson(userJson, User.class);
    }

    @TypeConverter
    public  String from(User user) {
        return new Gson().toJson(user);
    }
}
