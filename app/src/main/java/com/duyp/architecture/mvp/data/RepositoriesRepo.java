package com.duyp.architecture.mvp.data;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.duyp.architecture.mvp.app.AppDatabase;
import com.duyp.architecture.mvp.base.repo.BaseRepo;
import com.duyp.architecture.mvp.data.local.RepositoryDao;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.remote.GithubService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by duypham on 9/15/17.
 *
 */

public class RepositoriesRepo extends BaseRepo {

    private final RepositoryDao repositoryDao;

    private LiveData<List<Repository>> liveData;

    @Inject
    public RepositoriesRepo(LifecycleOwner owner, GithubService githubService, AppDatabase appDatabase) {
        super(owner, githubService);
        this.repositoryDao = appDatabase.repositoryDao();
    }

    public Flowable<Resource<List<Repository>>> getAllRepositories(Long sinceId) {
        Log.d("source", "RepositoriesRepo: getting all repo with sinceId = " + sinceId);
        removeObserves();
        liveData = repositoryDao.getAllRepositories();
        return createResource(getGithubService().getAllPublicRepositories(sinceId),
                liveData,
                repositoryDao::addAllRepositories);
    }

    public Flowable<Resource<List<Repository>>> findRepositories(String repoName) {
        Log.d("source", "RepositoriesRepo: finding repo: " + repoName);
        String nameToSearch = "%" + repoName + "%";
        removeObserves();
        liveData = repositoryDao.findAllByName(nameToSearch);
        return createResource(getGithubService().getAllPublicRepositories(null), liveData, null);
    }

    private void removeObserves() {
        if (liveData != null) {
            liveData.removeObservers(getOwner());
        }
    }
}