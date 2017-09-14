package com.duyp.architecture.mvp.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.duyp.architecture.mvp.data.model.Repository;

import java.util.List;

/**
 * Created by phamd on 9/14/2017.
 * {@link Repository} Data Access Object
 */

@Dao
public interface RepositoryDao {

    @Query("SELECT * FROM Repository")
    LiveData<List<Repository>> getAllRepositories();

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
}
