package com.duyp.architecture.mvp.ui.user_repositories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.repos.RepositoriesRepo;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.ui.repositories.RepositoryAdapter;
import com.duyp.architecture.mvp.ui.repositories.RepositoryPresenter;

import javax.inject.Inject;

/**
 * Created by duypham on 9/16/17.
 * Presenter for {@link UserRepositoryFragment}
 */

@PerFragment
public class UserRepositoryPresenter extends RepositoryPresenter {

    private User targetUser;

    @Inject
    UserRepositoryPresenter(@ActivityContext Context context, UserManager userManager,
                            RepositoriesRepo repositoriesRepo, @NonNull RepositoryAdapter adapter) {
        super(context, userManager, repositoriesRepo, adapter);
    }

    void initTargetUser(@Nullable User user) {
        targetUser = user != null ? user : getUserRepo().getUser();
    }

    private void getUserRepositories() {
        addRequest(getRepositoriesRepo().getUserRepositories(targetUser.getLogin()), this::populateData);
    }

    @Override
    protected void fetchData() {
        getUserRepositories();
    }
}
