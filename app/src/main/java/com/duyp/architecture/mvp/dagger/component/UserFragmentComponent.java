package com.duyp.architecture.mvp.dagger.component;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.duyp.androidutils.navigator.FragmentNavigator;
import com.duyp.architecture.mvp.dagger.module.FragmentModule;
import com.duyp.architecture.mvp.dagger.qualifier.ChildFragmentManager;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;

import dagger.Subcomponent;

/**
 * Created by duypham on 7/22/17.
 * Sub-component of User Component in Fragment Scope
 * in {@link PerFragment} scope, use {@link FragmentModule} as modules
 */

@PerFragment
@Subcomponent(modules = {FragmentModule.class})
public interface UserFragmentComponent {

    @ChildFragmentManager
    FragmentManager childFragmentManager();

    FragmentNavigator navigator();

    Fragment baseFragment();
}
