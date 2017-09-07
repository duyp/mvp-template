package com.duyp.architecture.mvp.dagger.module;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.duyp.androidutils.navigator.ActivityNavigator;
import com.duyp.androidutils.navigator.Navigator;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityFragmentManager;
import com.duyp.architecture.mvp.dagger.scopes.PerActivity;
import com.duyp.architecture.mvp.utils.NavigatorHelper;

import dagger.Module;
import dagger.Provides;

/* Copyright 2016 Patrick Löwenstein
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. */

/**
 * Modified by Duy Pham
 */
@Module
public class ActivityModule {

    private final AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity provideActivity() { return mActivity; }

    @Provides
    @PerActivity
    @ActivityFragmentManager
    FragmentManager provideFragmentManager() { return mActivity.getSupportFragmentManager(); }

    @Provides
    @PerActivity
    Navigator provideNavigator() { return new ActivityNavigator(mActivity); }

    @Provides
    NavigatorHelper provideNavigatorHelper(Navigator navigator) {
        return new NavigatorHelper(navigator);
    }

}
