package com.duyp.architecture.mvp.data.repos;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.base.data.BaseRepo;
import com.duyp.architecture.mvp.base.data.LiveRealmResults;
import com.duyp.architecture.mvp.data.Resource;
import com.duyp.architecture.mvp.data.local.RealmDatabase;
import com.duyp.architecture.mvp.data.local.dao.IssueDao;
import com.duyp.architecture.mvp.data.model.Issue;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.remote.GithubService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import lombok.Getter;

/**
 * Created by duypham on 9/17/17.
 *
 */

public class IssuesRepo extends BaseRepo {

    private Repository mRepository;

    private IssueDao mIssuesDao;

    @Getter
    private LiveRealmResults<Issue> data;

    @Inject
    public IssuesRepo(LifecycleOwner owner, GithubService githubService, RealmDatabase realmDatabase) {
        super(owner, githubService, realmDatabase);
        this.mIssuesDao = realmDatabase.getIssueDao();
    }

    public void initRepo(@NonNull Long repoId) {
        this.mRepository = getRealmDatabase().getRepositoryDao().getById(repoId).getData();
        data = mIssuesDao.getRepoIssues(mRepository.getId());
    }

    public Flowable<Resource<List<Issue>>> getRepoIssues(boolean refresh) {
        return createResource(refresh, getGithubService().getRepoIssues(mRepository.getOwner().getLogin(), mRepository.getName()), (issues, isRefresh) -> {
            if (isRefresh) {
                mIssuesDao.deleteAll();
            }
            for (Issue issue : issues) {
                issue.setRepoId(mRepository.getId());
            }
            mIssuesDao.addAll(issues);
        });
    }
}