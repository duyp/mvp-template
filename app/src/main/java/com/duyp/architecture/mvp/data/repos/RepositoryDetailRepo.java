package com.duyp.architecture.mvp.data.repos;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.app.AppDatabase;
import com.duyp.architecture.mvp.base.repo.BaseRepo;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.Resource;
import com.duyp.architecture.mvp.data.local.RepositoryDao;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.remote.GithubService;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by duypham on 9/17/17.
 */

@PerFragment
public class RepositoryDetailRepo extends BaseRepo {

    private final RepositoryDao mRepositoryDao;

    private LiveData<Repository> mData;

    private Repository mRepository;

    @Inject
    public RepositoryDetailRepo(LifecycleOwner owner, GithubService githubService, AppDatabase appDatabase) {
        super(owner, githubService, appDatabase);
        mRepositoryDao = appDatabase.repositoryDao();
    }

    public void initRepo(@NonNull Repository repository) {
        this.mRepository = repository;
        mData = mRepositoryDao.getRepository(repository.getId());
    }

    public Flowable<Resource<Repository>> getRepository() {
        return createResource(getGithubService().getRepository(mRepository.getOwner().getLogin(), mRepository.getName()),
                mData, mRepositoryDao::addRepository);
    }
}
