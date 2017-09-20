package com.duyp.architecture.mvp.data.repos;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.base.data.BaseRepo;
import com.duyp.architecture.mvp.base.data.LiveRealmObject;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.Resource;
import com.duyp.architecture.mvp.data.local.RealmDatabase;
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
    public RepositoryDetailRepo(LifecycleOwner owner, GithubService githubService, RealmDatabase realmDatabase) {
        super(owner, githubService, realmDatabase);
        mRepositoryDao = realmDatabase.getRepositoryDao();
    }

    public LiveRealmObject<Repository> initRepo(@NonNull Long repoId) {
        data = mRepositoryDao.getById(repoId);
        return data;
    }

    public Flowable<Resource<Repository>> getRepository() {
        Repository localRepo = data.getData();
        return createResource(getGithubService().getRepository(localRepo.getOwner().getLogin(), localRepo.getName()),repository -> {
                    // github api dose not support, so we need do it manually
                    repository.setMemberLoginName(localRepo.getMemberLoginName());
                    mRepositoryDao.addOrUpdate(repository);
                });
    }
}
