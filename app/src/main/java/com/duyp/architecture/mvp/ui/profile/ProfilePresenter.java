package com.duyp.architecture.mvp.ui.profile;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.duyp.architecture.mvp.base.presenter.BaseUserPresenter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.data.remote.UserService;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by duypham on 9/12/17.
 *
 */

@PerFragment
public class ProfilePresenter extends BaseUserPresenter<ProfileView>{

    @Inject
    ProfilePresenter(@ActivityContext Context context, LiveData<User> user, UserService userService, UserManager userManager) {
        super(context, user, userService, userManager);
    }

    @Override
    public void bindView(ProfileView view) {
        super.bindView(view);
        getUserLiveData().observe((LifecycleOwner)view, user -> {
            getView().onUserUpdated(user);
        });
    }

    void updateMyUser() {
        addRequest(getGithubService().login(getUserRepo().getUserToken()), true, response -> {
            getUserManager().updateUserIfEquals(response);
        });
    }
}
