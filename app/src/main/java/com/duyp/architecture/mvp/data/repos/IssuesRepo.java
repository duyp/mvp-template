package com.duyp.architecture.mvp.data.repos;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.app.AppDatabase;
import com.duyp.architecture.mvp.base.repo.BaseRepo;
import com.duyp.architecture.mvp.data.Resource;
import com.duyp.architecture.mvp.data.local.IssuesDao;
import com.duyp.architecture.mvp.data.model.Issue;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.remote.GithubService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by duypham on 9/17/17.
 *
 */

public class IssuesRepo extends BaseRepo {

    private IssuesDao mIssuesDao;

    private Repository mRepository;

    private LiveData<List<Issue>> data;

    @Inject
    public IssuesRepo(LifecycleOwner owner, GithubService githubService, AppDatabase appDatabase) {
        super(owner, githubService, appDatabase);
        mIssuesDao = appDatabase.issuesDao();
    }

    public void initRepo(@NonNull Repository repository) {
        this.mRepository = repository;
        data = mIssuesDao.getRepoIssues(mRepository.getId());
    }

    public Flowable<Resource<List<Issue>>> getRepoIssues() {
        return createResource(getGithubService().getRepoIssues(mRepository.getOwner().getLogin(), mRepository.getName()),
                data, issues -> {
                    for (Issue issue : issues) {
                        issue.setRepoId(mRepository.getId());
                    }
                    mIssuesDao.insertAll(issues);
                });
    }
}
