package com.duyp.architecture.mvp.ui.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.duyp.architecture.mvp.app.AppDatabase;
import com.duyp.architecture.mvp.base.presenter.BaseListPresenter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.local.RepositoryDao;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.utils.DbTaskHelper;

import java.util.List;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by phamd on 9/14/2017.\
 * Presenter for repositories fragment
 */

@PerFragment
public class RepositoryPresenterOld extends BaseListPresenter<RepositoryView> {

    @NonNull
    @Getter
    private final RepositoryAdapter adapter;

    private final RepositoryDao repositoryDao;

    //    private final RepositoriesRepo repositoriesRepo;
    @NonNull
    @Getter
    private final MutableLiveData<Pair<Integer, String>> options = new MutableLiveData<>();

    private LiveData<List<Repository>> repositories;

    private Long sinceRepoId;
    private boolean canLoadMore = false;

    @Inject
    RepositoryPresenterOld(@ActivityContext Context context, UserManager userManager, AppDatabase appDatabase, @NonNull RepositoryAdapter adapter) {
        super(context, userManager);
        this.adapter = adapter;
        this.repositoryDao = appDatabase.repositoryDao();
    }

    @Override
    public void bindView(RepositoryView view) {
        super.bindView(view);
        // first time open: load local data
        getView().showProgress();
        repositories = repositoryDao.getAllRepositories();
        initRepoObserver();

        options.observe(getLifeCircleOwner(), options -> {
            if (options != null) {
                if (options.first == 0) return;
                canLoadMore = false; // not allow load more for offline filter
                getView().showProgress();
                if (repositories != null) {
                    repositories.removeObservers(getLifeCircleOwner());
                }
                if (options.second.isEmpty()) {
                    repositories = repositoryDao.getAllRepositories();
                } else {
                    String search = "%" + options.second + "%";
                    switch (options.first) {
                        case 1: // repo name
                            repositories = repositoryDao.findAllByName(search);
                            break;
                        case 2: // user name
                            repositories = repositoryDao.findAllByUser(search);
                            break;
                        case 3: // language
                            repositories = repositoryDao.findAllByLanguage(search);
                            break;
                        default:
                            break;
                    }
                }
                initRepoObserver();
            }
        });
    }

    private void initRepoObserver() {
        repositories.observe(getLifeCircleOwner(), data -> {
            adapter.setData(data);
            if (getView() != null) {
                getView().hideProgress();
            }
        });
    }

    private void fetchAllRepositories() {
        addRequest(getGithubService().getAllPublicRepositories(sinceRepoId), true, response -> {
            if (isRefreshed()) {
                // clear all after inserting
                DbTaskHelper.doTaskOnBackground(repositoryDao::deleteAllRepositories, () -> addAll(response));
            } else {
                addAll(response);
            }
            canLoadMore = true;
            sinceRepoId = response.get(response.size() - 1).getId();
            setRefreshed(false);
        });
    }

    private void addAll(List<Repository> response) {
        DbTaskHelper.doTaskOnBackground(() -> repositoryDao.addAllRepositories(response));
    }

    @Override
    protected void fetchData() {
        fetchAllRepositories();
    }

    @Override
    public boolean canLoadMore() {
        return canLoadMore;
    }

    @Override
    public boolean isDataEmpty() {
        return repositories.getValue() == null || repositories.getValue().size() == 0;
    }
}
