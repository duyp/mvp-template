package com.duyp.architecture.mvp.ui.repository_detail.issues;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.duyp.architecture.mvp.base.presenter.BaseListPresenter;
import com.duyp.architecture.mvp.base.presenter.BasePresenter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.Issue;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.repos.IssuesRepo;

import java.util.List;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by duypham on 9/17/17.
 *
 */

@PerFragment
public class IssuesPresenter extends BaseListPresenter<IssuesView> {

    private final IssuesRepo mIssueRepo;

    @Getter
    private final IssuesAdapter adapter;

    @Inject
    public IssuesPresenter(@ActivityContext Context context, UserManager userManager, IssuesRepo repo, IssuesAdapter adapter) {
        super(context, userManager);
        mIssueRepo = repo;
        this.adapter = adapter;
    }

    void initRepo(@NonNull Repository repository) {
        mIssueRepo.initRepo(repository);
    }

    @Override
    public boolean canLoadMore() {
        return false;
    }

    @Override
    protected void fetchData() {
        Log.d("source", "fetchData: updating issues...");
        addRequest(mIssueRepo.getRepoIssues(), this::populateData);
    }

    private void populateData(List<Issue> issues) {
        adapter.setData(issues);
        setRefreshed(false);
    }

    @Override
    public boolean isDataEmpty() {
        return adapter.getData() == null || adapter.getData().isEmpty();
    }
}
