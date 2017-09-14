package com.duyp.architecture.mvp.utils.roomConverters;

import android.arch.persistence.room.TypeConverter;

import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.model.User;
import com.google.gson.Gson;

/**
 * Created by phamd on 9/14/2017.
 */

public class RepositoryConverter {

    @TypeConverter
    public Repository toRepository(String s) {
        return new Gson().fromJson(s, Repository.class);
    }

    @TypeConverter
    public String from(Repository repository) {
        return new Gson().toJson(repository);
    }
}
