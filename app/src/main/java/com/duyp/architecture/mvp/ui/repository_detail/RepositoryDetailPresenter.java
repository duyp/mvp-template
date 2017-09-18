package com.duyp.architecture.mvp.ui.repository_detail;

import android.content.Context;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.base.presenter.BasePresenter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.repos.RepositoriesRepo;
import com.duyp.architecture.mvp.data.repos.RepositoryDetailRepo;
import com.duyp.architecture.mvp.ui.repositories.RepositoryView;

import javax.inject.Inject;

/**
 * Created by duypham on 9/17/17.
 */

@PerFragment
public class RepositoryDetailPresenter extends BasePresenter<RepositoryDetailView> {

    private final RepositoryDetailRepo repositoryDetailRepo;

    @Inject
    public RepositoryDetailPresenter(@ActivityContext Context context, UserManager userManager, RepositoryDetailRepo repositoryDetailRepo) {
        super(context, userManager);
        this.repositoryDetailRepo = repositoryDetailRepo;
    }

    void initRepo(@NonNull Repository repository) {
        repositoryDetailRepo.initRepo(repository);
        fetchData();
    }

    void fetchData() {
        addRequest(false, repositoryDetailRepo.getRepository(), repository -> {
            getView().populateData(repository);
        });
    }
}
