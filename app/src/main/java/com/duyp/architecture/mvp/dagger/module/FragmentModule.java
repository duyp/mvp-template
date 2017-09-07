package com.duyp.architecture.mvp.dagger.module;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.duyp.androidutils.navigator.ChildFragmentNavigator;
import com.duyp.androidutils.navigator.FragmentNavigator;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityFragmentManager;
import com.duyp.architecture.mvp.dagger.qualifier.ChildFragmentManager;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.utils.NavigatorHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Module for fragment component, modified by Duy Pham (Copyright 2016 Patrick Löwenstein)
 */
@Module
public class FragmentModule {

    private final Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @PerFragment
    @Provides
    Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @PerFragment
    @ChildFragmentManager
    FragmentManager provideChildFragmentManager() { return mFragment.getChildFragmentManager(); }

    @Provides
    @PerFragment
    FragmentNavigator provideFragmentNavigator() { return new ChildFragmentNavigator(mFragment); }

    @Provides
    NavigatorHelper provideNavigatorHelper(FragmentNavigator navigator) {
        return new NavigatorHelper(navigator);
    }

    @Provides
    @PerFragment
    RequestManager providesGlide() {
        return Glide.with(mFragment.getContext());
    }

    @Provides
    @PerFragment
    AppCompatActivity provideActivity() {
        Activity activity = mFragment.getActivity();
        if (activity instanceof AppCompatActivity) {
            return (AppCompatActivity) mFragment.getActivity();
        } else {
            throw new IllegalStateException("Must using AppCompatActivity");
        }
    }

    @Provides
    @PerFragment
    @ActivityFragmentManager
    FragmentManager provideActivityFragmentManager(AppCompatActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
