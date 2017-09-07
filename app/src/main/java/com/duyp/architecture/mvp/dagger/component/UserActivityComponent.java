package com.duyp.architecture.mvp.dagger.component;

import android.support.v4.app.FragmentManager;

import com.duyp.androidutils.navigator.Navigator;
import com.duyp.architecture.mvp.dagger.module.ActivityModule;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityFragmentManager;
import com.duyp.architecture.mvp.dagger.scopes.PerActivity;

import dagger.Subcomponent;

/**
 * Created by duypham on 7/22/17.
 * Sub-component of User Component
 * in {@link com.duyp.architecture.mvp.dagger.scopes.PerActivity} Scope,
 * use {@link com.duyp.architecture.mvp.dagger.module.ActivityModule} as modules
 */

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface UserActivityComponent {

    @ActivityFragmentManager
    FragmentManager defaultFragmentManager();

    Navigator navigator();

    // create inject methods for your Activities here
}
