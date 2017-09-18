package com.duyp.architecture.mvp.data.repos;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.base.data.BaseRepo;
import com.duyp.architecture.mvp.base.data.LiveRealmObject;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.Resource;
import com.duyp.architecture.mvp.data.local.dao.RepositoryDao;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.remote.GithubService;

import javax.inject.Inject;

import io.reactivex.Flowable;
import lombok.Getter;

/**
 * Created by duypham on 9/17/17.
 *
 */

@PerFragment
public class RepositoryDetailRepo extends BaseRepo {

    private final RepositoryDao mRepositoryDao;

    @Getter
    private LiveRealmObject<Repository> data;

    @Inject
    public RepositoryDetailRepo(LifecycleOwner owner, GithubService githubService, RepositoryDao repositoryDao) {
        super(owner, githubService);
        mRepositoryDao = repositoryDao;
    }

    public LiveRealmObject<Repository> initRepo(@NonNull Long repoId) {
        data = mRepositoryDao.getById(repoId);
        return data;
    }

    public Flowable<Resource<Repository>> getRepository() {
        return createResource(getGithubService().getRepository(data.getValue().getOwner().getLogin(), data.getValue().getName()),repository -> {
                    // github api dose not support, so we need do it manually
                    repository.setMemberLoginName(repository.getMemberLoginName());
                    mRepositoryDao.addOrUpdate(repository);
                });
    }
}
