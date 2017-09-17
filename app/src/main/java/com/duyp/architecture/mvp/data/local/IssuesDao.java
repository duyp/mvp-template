package com.duyp.architecture.mvp.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.duyp.architecture.mvp.data.model.Issue;

import java.util.List;

/**
 * Created by duypham on 9/17/17.
 */

@Dao
public interface IssuesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Issue> issues);

    @Query("SELECT * FROM Issue WHERE repo_id = :repoId ORDER BY updatedAt DESC")
    LiveData<List<Issue>> getRepoIssues(Long repoId);
}
