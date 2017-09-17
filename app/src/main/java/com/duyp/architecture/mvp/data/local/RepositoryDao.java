package com.duyp.architecture.mvp.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.duyp.architecture.mvp.data.model.Repository;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by phamd on 9/14/2017.
 * {@link Repository} Data Access Object
 */

@Dao
public interface RepositoryDao {


    @Query("SELECT * FROM Repository")
    LiveData<List<Repository>> getAllRepositories();

//    @Query("SELECT * FROM Repository " +
//            "INNER JOIN User ON User.id = Repository.user_id " +
//            "WHERE User.login = :userName")
    @Query("SELECT * FROM Repository WHERE user_login = :userLogin")
    LiveData<List<Repository>> getUserRepositories(String userLogin);

    @Query("SELECT * FROM Repository LIMIT :limit")
    LiveData<List<Repository>> getAllRepositories(int limit);

    @Query("SELECT * FROM Repository WHERE name LIKE :name")
    LiveData<List<Repository>> findAllByName(String name);

    @Query("SELECT * FROM Repository WHERE language LIKE :language")
    LiveData<List<Repository>> findAllByLanguage(String language);

    @Query("SELECT * FROM Repository WHERE user_name LIKE :userName")
    LiveData<List<Repository>> findAllByUser(String userName);

    @Query("SELECT * FROM Repository where id = :id LIMIT 1")
    Repository getRepository(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addRepository(Repository repository);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAllRepositories(List<Repository> repositories);

    @Delete
    void deleteRepository(Repository repository);

    @Query("DELETE FROM Repository WHERE id = :id")
    void deleteRepository(Long id);

    @Query("DELETE FROM Repository")
    void deleteAllRepositories();

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateRepository(Repository repository);
}
