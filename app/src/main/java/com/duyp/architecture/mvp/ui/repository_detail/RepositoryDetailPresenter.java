package com.duyp.architecture.mvp.ui.repository_detail;

import android.content.Context;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.base.data.LiveRealmObject;
import com.duyp.architecture.mvp.base.presenter.BasePresenter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.repos.RepositoryDetailRepo;

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

    Repository initRepo(@NonNull Long repoId) {
        LiveRealmObject<Repository> data = repositoryDetailRepo.initRepo(repoId);
        data.observe(getLifeCircleOwner(), repository -> {
            getView().populateData(repository);
        });
        return data.getData();
    }

    Repository getData() {
        return repositoryDetailRepo.getData().getValue();
    }

    void fetchData() {
        addRequest(false, repositoryDetailRepo.getRepository(), repository -> {
            getView().populateData(repository);
        });
    }
}
