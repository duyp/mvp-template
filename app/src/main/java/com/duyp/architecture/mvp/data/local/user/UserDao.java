package com.duyp.architecture.mvp.data.local.user;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.duyp.architecture.mvp.data.model.User;

/**
 * Created by duypham on 9/10/17.
 * User data access object
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM User where id = :userId LIMIT 1")
    LiveData<User> getUser(Long userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM User")
    void deleteAllUsers();
}