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

import javax.inject.Inject;

/**
 * Created by duypham on 9/12/17.
 *
 */

@PerFragment
public class ProfilePresenter extends BasePresenter<ProfileView> {

    LiveData<User> userLiveData;

    @Inject
    ProfilePresenter(@ActivityContext Context context, UserManager userManager) {
        super(context, userManager);
    }

    void initUser(@Nullable User user) {
        if (user == null) {
            if (getUserManager().isUserSessionStarted()) {
                userLiveData = getUserManager().getUserRepo().getUserLiveData();
            } else {
                throw new IllegalStateException("User session not started yet!");
            }
        } else {
            userLiveData = user.toLiveData();
        }
        userLiveData.observe(getLifeCircleOwner(), user1 -> {
            getView().onUserUpdated(user1);
        });
    }

    void updateMyUser() {
        addRequest(getGithubService().login(getUserRepo().getUserToken()), true, response -> {
            getUserManager().updateUserIfEquals(response);
        });
    }
}
