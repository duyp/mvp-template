package com.duyp.architecture.mvp.dagger.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.duyp.androidutils.ContextUtils;
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
        return (AppCompatActivity) ContextUtils.getActivityFromContext(view.getContext());
    }

    @Provides
    Navigator provideNavigator(AppCompatActivity activity) {
        return new ActivityNavigator(activity);
    }

    @Provides
    NavigatorHelper provideNavigatorHelper(Navigator navigator) {
        return new NavigatorHelper(navigator);
    }
}