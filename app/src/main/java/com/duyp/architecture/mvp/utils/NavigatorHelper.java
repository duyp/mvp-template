package com.duyp.architecture.mvp.utils;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.androidutils.navigator.Navigator;
import com.duyp.architecture.mvp.data.Constants;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.ui.login.LoginFragment;
import com.duyp.architecture.mvp.ui.profile.ProfileFragment;
import com.duyp.architecture.mvp.ui.repositories.RepositoriesFragment;
import com.duyp.architecture.mvp.ui.repository_detail.RepositoryDetailActivity;
import com.duyp.architecture.mvp.ui.user_repositories.UserRepositoryFragment;

import org.parceler.Parcels;

import lombok.AllArgsConstructor;

/**
 * Created by duypham on 9/7/17.
 * Util class for navigating common page in application
 */

@AllArgsConstructor
public class NavigatorHelper {

    private static final String TAG_LOGIN = "TAG_LOGIN";
    private static final String TAG_PROFILE = "TAG_PROFILE";
    private static final String TAG_ALL_REPO = "TAG_ALL_REPO";

    Navigator mNavigator;

    public void navigateUserProfile(@IdRes int containerId, @Nullable User user) {
        ProfileFragment fragment = mNavigator.findFragmentByTag(TAG_PROFILE);
        if (fragment == null) {
            fragment = ProfileFragment.newInstance(user);
        }
        mNavigator.replaceFragment(containerId, fragment, TAG_PROFILE, null);
    }

    public void navigateLoginFragment(@IdRes int containerId) {
        LoginFragment fragment = mNavigator.findFragmentByTag(TAG_LOGIN);
        if (fragment == null) {
            fragment = new LoginFragment();
        }
        mNavigator.replaceFragment(containerId, fragment, TAG_LOGIN, null);
    }
    
    public void navigateAllRepositoriesFragment(@IdRes int containerId) {
        RepositoriesFragment fragment = mNavigator.findFragmentByTag(TAG_ALL_REPO);
        if (fragment == null) {
            fragment = new RepositoriesFragment();
        }
        mNavigator.replaceFragment(containerId, fragment, TAG_ALL_REPO, null);
    }

    public void navigateMyRepositoriesFragment(@IdRes int containerId) {
        mNavigator.replaceFragment(containerId, UserRepositoryFragment.createInstance(null));
    }

    public void navigateRepositoryDetail(@NonNull Long repoId, View... views) {
        mNavigator.startActivityWithTransition(RepositoryDetailActivity.class, intent -> {
            intent.putExtra(Constants.EXTRA_DATA, repoId);
        }, false, false, views);
    }
}