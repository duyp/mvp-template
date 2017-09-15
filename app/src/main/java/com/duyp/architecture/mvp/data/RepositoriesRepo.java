package com.duyp.architecture.mvp.data;

import com.duyp.architecture.mvp.base.repo.BaseRepo;
import com.duyp.architecture.mvp.data.NetworkBoundSource;
import com.duyp.architecture.mvp.data.Resource;
import com.duyp.architecture.mvp.data.local.RepositoryDao;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.remote.GithubService;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import retrofit2.Response;

/**
 * Created by duypham on 9/15/17.
 */

public class RepositoriesRepo extends BaseRepo {

    private final RepositoryDao repositoryDao;

    public RepositoriesRepo(GithubService githubService, RepositoryDao repositoryDao) {
        super(githubService);
        this.repositoryDao = repositoryDao;
    }

    public Flowable<Resource<List<Repository>>> getAllRepositories(Long sinceId) {
        return Flowable.create(emitter -> {
            new NetworkBoundSource<List<Repository>, List<Repository>>(emitter)
                    .local(repositoryDao.getAllRepositoriesRx())
                    .remote(getGithubService().getAllPublicRepositories(sinceId))
                    .mapper(Response::body)
                    .onSave(repositoryDao::addAllRepositories);
        }, BackpressureStrategy.BUFFER);
    }
}
