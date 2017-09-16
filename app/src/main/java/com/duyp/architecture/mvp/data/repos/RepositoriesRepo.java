package com.duyp.architecture.mvp.data.repos;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.duyp.architecture.mvp.app.AppDatabase;
import com.duyp.architecture.mvp.base.repo.BaseRepo;
import com.duyp.architecture.mvp.data.Resource;
import com.duyp.architecture.mvp.data.local.RepositoryDao;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.model.def.RepoTypes;
import com.duyp.architecture.mvp.data.remote.GithubService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

import static com.duyp.architecture.mvp.data.SimpleNetworkBoundSourceLiveData.*;
/**
 * Created by duypham on 9/15/17.
 *
 */

public class RepositoriesRepo extends BaseRepo {

    public static final int PER_PAGE = 100;

    protected final RepositoryDao repositoryDao;

    private LiveData<List<Repository>> liveData;

    private int currentPage = 1;

    @Inject
    public RepositoriesRepo(LifecycleOwner owner, GithubService githubService, AppDatabase appDatabase) {
        super(owner, githubService, appDatabase);
        this.repositoryDao = appDatabase.repositoryDao();
    }

    public Flowable<Resource<List<Repository>>> getAllRepositories(Long sinceId) {
        Log.d(TAG, "RepositoriesRepo: getting all repo with sinceId = " + sinceId);
        if (sinceId != null) {
            currentPage ++;
        } else {
            currentPage = 1;
        }
        removeObserves();
        liveData = repositoryDao.getAllRepositories(currentPage * PER_PAGE);
        return createResource(getGithubService().getAllPublicRepositories(sinceId), liveData, repositoryDao::addAllRepositories);
    }

    public Flowable<Resource<List<Repository>>> findRepositories(String repoName) {
        Log.d(TAG, "RepositoriesRepo: finding repo: " + repoName);
        String nameToSearch = "%" + repoName + "%";
        removeObserves();
        liveData = repositoryDao.findAllByName(nameToSearch);
        return createResource(getGithubService().getAllPublicRepositories(null), liveData, repositoryDao::addAllRepositories);
    }

    public Flowable<Resource<List<Repository>>> getUserRepositories(String userNameLogin) {
        removeObserves();
        liveData = repositoryDao.getUserRepositories(userNameLogin);
        return createResource(getGithubService().getUserRepositories(userNameLogin, RepoTypes.ALL),
                liveData, repositoryDao::addAllRepositories);
    }

    private void removeObserves() {
        if (liveData != null) {
            liveData.removeObservers(getOwner());
        }
    }
}