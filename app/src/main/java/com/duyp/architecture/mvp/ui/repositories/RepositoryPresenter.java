package com.duyp.architecture.mvp.ui.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.duyp.architecture.mvp.app.AppDatabase;
import com.duyp.architecture.mvp.base.presenter.BaseListPresenter;
import com.duyp.architecture.mvp.base.presenter.BasePresenter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.local.RepositoryDao;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.utils.DbTaskHelper;
import com.duyp.architecture.mvp.utils.api.OnRequestSuccessListener;

import java.util.List;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by phamd on 9/14/2017.
 *
 */

@PerFragment
public class RepositoryPresenter extends BaseListPresenter<RepositoryView> {

    @NonNull
    @Getter
    private final RepositoryAdapter adapter;

    private final RepositoryDao repositoryDao;

    @NonNull
    private final LiveData<List<Repository>> repositories;

    private Long sinceRepoId;

    @Inject
    public RepositoryPresenter(@ActivityContext Context context, UserManager userManager, AppDatabase appDatabase, RepositoryAdapter adapter) {
        super(context, userManager);
        this.adapter = adapter;
        this.repositoryDao = appDatabase.repositoryDao();
        repositories = repositoryDao.getAllRepositories();
    }

    @Override
    public void bindView(RepositoryView view) {
        super.bindView(view);
        repositories.observe(getLifeCircleOwner(), repositories1 -> {
            adapter.setData(repositories1);
        });
    }

    public void fetchAllRepositories() {
        addRequest(getGithubService().getAllPublicRepositories(sinceRepoId), true, response -> {
            Log.d("repo", "Got repositories: " + response.size());
            DbTaskHelper.doTaskOnBackground(() -> repositoryDao.addAllRepositories(response));
            sinceRepoId = response.get(response.size() - 1).getId();
            setRefreshed(false);
        });
    }

    @Override
    protected void fetchData() {
        fetchAllRepositories();
    }

    @Override
    public boolean canLoadMore() {
        return true;
    }

    @Override
    public boolean isDataEmpty() {
        return repositories.getValue() == null || repositories.getValue().size() == 0;
    }
}
