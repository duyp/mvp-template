package com.duyp.architecture.mvp.app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.duyp.architecture.mvp.data.local.RepositoryDao;
import com.duyp.architecture.mvp.data.local.user.UserDao;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.model.User;

@Database(entities = {User.class, Repository.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "git_db")
                            .build();
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();
    public abstract RepositoryDao repositoryDao();
}