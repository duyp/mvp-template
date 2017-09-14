package com.duyp.architecture.mvp.utils.roomConverters;

import android.arch.persistence.room.TypeConverter;

import com.duyp.architecture.mvp.data.Permissions;
import com.duyp.architecture.mvp.data.model.User;
import com.google.gson.Gson;

/**
 * Created by phamd on 9/14/2017.
 */

public class PermissionsConverter {

    @TypeConverter
    public Permissions toUser(String s) {
        return new Gson().fromJson(s, Permissions.class);
    }

    @TypeConverter
    public String from(Permissions permissions) {
        return new Gson().toJson(permissions);
    }
}
