package com.duyp.architecture.mvp.dagger.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.duyp.androidutils.CommonUtils;
import com.duyp.androidutils.image.glide.loader.SimpleGlideLoader;
import com.duyp.androidutils.image.glide.loader.TransitionGlideLoader;
import com.duyp.androidutils.navigator.ActivityNavigator;
import com.duyp.androidutils.navigator.Navigator;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerActivity;
import com.duyp.architecture.mvp.utils.AvatarLoader;
import com.duyp.architecture.mvp.utils.NavigatorHelper;

import dagger.Module;
import dagger.Provides;
import lombok.AllArgsConstructor;

/**
 * Created by duypham on 9/6/17.
 * Module for custom view component
 */

@AllArgsConstructor
@Module
@PerActivity
public class CustomViewModule {
    View view;

    @Provides
    AppCompatActivity provideActivity() {
        return (AppCompatActivity) CommonUtils.getActivityFromContext(view.getContext());
    }

    @Provides
    @ActivityContext
    Context context(AppCompatActivity activity) {
        return activity;
    }

    @Provides
    Navigator provideNavigator(AppCompatActivity activity) {
        return new ActivityNavigator(activity);
    }

    @Provides
    NavigatorHelper provideNavigatorHelper(Navigator navigator) {
        return new NavigatorHelper(navigator);
    }

    @Provides
    protected SimpleGlideLoader provideDefaultGlide() {
        return new SimpleGlideLoader(view.getContext());
    }

    @Provides
    protected TransitionGlideLoader provideTransitionGlide() {
        return new TransitionGlideLoader(view.getContext());
    }

    @Provides
    @PerActivity
    protected AvatarLoader provideAvatarLoader() {
        return new AvatarLoader(view.getContext());
    }
}