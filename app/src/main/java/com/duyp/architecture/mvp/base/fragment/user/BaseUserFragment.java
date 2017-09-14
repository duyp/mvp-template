package com.duyp.architecture.mvp.base.fragment.user;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvp.base.fragment.BaseFragment;
import com.duyp.architecture.mvp.dagger.InjectionHelper;
import com.duyp.architecture.mvp.dagger.component.UserComponent;
import com.duyp.architecture.mvp.dagger.component.UserFragmentComponent;
import com.duyp.architecture.mvp.dagger.module.FragmentModule;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.User;

import lombok.Getter;

/**
 * Created by Duy Pham on 5/25/2017.
 * Base Fragment with presenter and component in user scope
 */

public abstract class BaseUserFragment extends BaseFragment {

    @Getter
    UserFragmentComponent userFragmentComponent;

    @Override
    @CallSuper
    protected void initialize(View view) {
        setupUserComponent();
    }

    // info - Application process was killed by the system
    // info - must call in onCreate
    protected void setupUserComponent() {
        UserManager userManager = InjectionHelper.getAppComponent(this).userManager();
        if (userManager.checkForSavedUserAndStartSessionIfHas()) {
            onUserComponentSetup(userManager.getUserComponent());
        } else {
            onStartUserSessionError();
        }
    }

    final protected void onUserComponentSetup(UserComponent userComponent) {
        userFragmentComponent = userComponent.getUserFragmentComponent(new FragmentModule(this));
        inject(userFragmentComponent);
    }

    protected abstract void inject(UserFragmentComponent component);

    /**
     * called after successfully starting user session
     * and after @{@link BaseFragment#initialize(View)}
     * @param userLiveData retrieved user live data
     */
    protected abstract void onUserSessionStarted(LiveData<User> userLiveData);

    /**
     * Called when can't start user session
     */
    protected void onStartUserSessionError() {
        // place your default action here
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (refWatcher != null && userFragmentComponent != null) {
            refWatcher.watch(userFragmentComponent);
        }
        userFragmentComponent = null;
    }
}
