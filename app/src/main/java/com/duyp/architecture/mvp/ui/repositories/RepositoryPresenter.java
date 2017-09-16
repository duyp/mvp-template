package com.duyp.architecture.mvp.ui.repositories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.duyp.architecture.mvp.base.presenter.BaseListPresenter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.RepositoriesRepo;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.Repository;

import static com.duyp.architecture.mvp.data.SimpleNetworkBoundSourceLiveData.*;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by phamd on 9/14/2017.\
 * Presenter for repositories fragment
 */

@PerFragment
class RepositoryPresenter extends BaseListPresenter<RepositoryView> {

    @NonNull
    @Getter
    private final RepositoryAdapter adapter;

    private final RepositoriesRepo repositoriesRepo;

    private Long sinceRepoId = null;
    private boolean canLoadMore = false;
    private String searchRepoName = "";

    @Inject
    RepositoryPresenter(@ActivityContext Context context, UserManager userManager, RepositoriesRepo repositoriesRepo, @NonNull RepositoryAdapter adapter) {
        super(context, userManager);
        this.adapter = adapter;
        this.repositoriesRepo = repositoriesRepo;
    }

    private void fetchAllRepositories() {
        addRequest(repositoriesRepo.getAllRepositories(sinceRepoId), repositories -> {
            populateData(repositories);
            canLoadMore = true;
            sinceRepoId = repositories.get(repositories.size() - 1).getId();
        });
    }

    void findRepositories(String name) {
        sinceRepoId = null;
        searchRepoName = name;
        if (!name.isEmpty()) {
            canLoadMore = false;
            addRequest(repositoriesRepo.findRepositories(searchRepoName), this::populateData);
        }
    }

    private void populateData(List<Repository> repositories) {
        Log.d(TAG, "RepositoryPresenter: GOT " + repositories.size() + " items");
        adapter.setData(repositories);
        setRefreshed(false);
    }

    @Override
    protected void fetchData() {
        if (!searchRepoName.isEmpty()) {
            findRepositories(searchRepoName);
        } else {
            if (isRefreshed()) {
                sinceRepoId = null;
            }
            fetchAllRepositories();
        }
    }

    @Override
    public boolean canLoadMore() {
        return canLoadMore;
    }

    @Override
    public boolean isDataEmpty() {
        return adapter.getData() != null && adapter.getData().isEmpty();
    }
}
