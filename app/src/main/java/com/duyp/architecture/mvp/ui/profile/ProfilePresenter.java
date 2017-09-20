package com.duyp.architecture.mvp.ui.profile;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvp.base.presenter.BasePresenter;
import com.duyp.architecture.mvp.base.presenter.BaseUserPresenter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.data.remote.UserService;
import com.duyp.architecture.mvp.data.repos.UserRepo;

import javax.inject.Inject;

/**
 * Created by duypham on 9/12/17.
 *
 */

@PerFragment
public class ProfilePresenter extends BasePresenter<ProfileView> {

    private final UserRepo userRepo;

    @Inject
    ProfilePresenter(@ActivityContext Context context, UserManager userManager, UserRepo userRepo) {
        super(context, userManager);
        this.userRepo = userRepo;
    }

    void initUser(@Nullable User user) {
        String userLogin;
        try {
            userLogin = user != null ? user.getLogin() : getUserManager().getUserRepo().getUser().getLogin();
        } catch (NullPointerException e) {
            throw new IllegalStateException("User session not started yet!");
        }
        userRepo.initUser(userLogin).observe(getLifeCircleOwner(), user1 -> {
            getView().onUserUpdated(user1);
        });
    }

    public void refresh() {
        addRequest(false, userRepo.fetchUser());
    }
}
