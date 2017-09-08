package com.duyp.architecture.mvp.dagger.module;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.duyp.androidutils.CommonUtils;
import com.duyp.androidutils.navigator.ActivityNavigator;
import com.duyp.androidutils.navigator.Navigator;
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
public class CustomViewModule {
    View view;

    @Provides
    AppCompatActivity provideActivity() {
        return (AppCompatActivity) CommonUtils.getActivityFromContext(view.getContext());
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
    RequestManager provideGlide() {
        return Glide.with(view.getContext());
    }
}