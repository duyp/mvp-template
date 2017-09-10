package com.duyp.architecture.mvp.base.activity;

import android.os.Bundle;

import com.duyp.architecture.mvp.base.BaseActivity;
import com.duyp.architecture.mvp.dagger.InjectionHelper;
import com.duyp.architecture.mvp.dagger.component.UserActivityComponent;
import com.duyp.architecture.mvp.dagger.component.UserComponent;
import com.duyp.architecture.mvp.dagger.module.ActivityModule;
import com.duyp.architecture.mvp.data.local.user.UserManager;

/**
 * Base activity in user scope
 */
public abstract class BaseUserActivity extends BaseActivity {

    UserActivityComponent mUserActivityComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUserComponent();
    }

    /**
     * Ensure user session has started
     */
    protected void setupUserComponent() {
        UserManager userManager = InjectionHelper.getAppComponent(this).userManager();
        if (userManager.checkForSavedUserAndStartSessionIfHas(null)) {
            onUserComponentSetup(userManager.getUserComponent());
        } else {
            finishAffinity();
            userManager.logout();
        }
    }

    // used to inject the data which we was sign in
    protected void onUserComponentSetup(UserComponent userComponent) {
        mUserActivityComponent = userComponent.getUserActivityComponent(new ActivityModule(this));
        inject(mUserActivityComponent);
        initialize();
    }

    // must be override to use presenter
    protected abstract void inject(UserActivityComponent component);

    protected abstract void initialize();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (refWatcher != null && mUserActivityComponent != null) {
            refWatcher.watch(mUserActivityComponent);
        }
        mUserActivityComponent = null;
    }
}
