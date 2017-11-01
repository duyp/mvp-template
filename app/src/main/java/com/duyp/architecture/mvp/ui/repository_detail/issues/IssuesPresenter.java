package com.duyp.architecture.mvp.ui.repository_detail.issues;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.duyp.architecture.mvp.base.presenter.BaseListPresenter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.local.dao.RepositoryDao;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.repos.IssuesRepo;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by duypham on 9/17/17.
 *
 */

@PerFragment
class IssuesPresenter extends BaseListPresenter<IssuesView> {

    private final IssuesRepo mIssueRepo;

    @Getter
    private final IssuesLiveAdapter adapter;

    @Inject
    public IssuesPresenter(@ActivityContext Context context, UserManager userManager, IssuesRepo repo,
                           IssuesLiveAdapter adapter) {
        super(context, userManager);
        mIssueRepo = repo;
        this.adapter = adapter;
    }

    void initRepo(@NonNull Long repoId) {
        mIssueRepo.initRepo(repoId);
        adapter.updateData(mIssueRepo.getData());
    }

    @Override
    public boolean canLoadMore() {
        return false;
    }

    @Override
    protected void fetchData() {
        Log.d("source", "fetchData: updating issues...");
        addRequest(mIssueRepo.getRepoIssues(isRefreshed()), issues -> this.setRefreshed(false));
    }
}