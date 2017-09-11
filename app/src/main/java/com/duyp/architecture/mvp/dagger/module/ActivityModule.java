package com.duyp.architecture.mvp.dagger.module;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.duyp.androidutils.image.glide.loader.SimpleGlideLoader;
import com.duyp.androidutils.image.glide.loader.TransitionGlideLoader;
import com.duyp.androidutils.navigator.ActivityNavigator;
import com.duyp.androidutils.navigator.Navigator;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityFragmentManager;
import com.duyp.architecture.mvp.dagger.scopes.PerActivity;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
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

    private final FragmentActivity mActivity;

    public ActivityModule(FragmentActivity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    protected FragmentActivity provideActivity() { return mActivity; }

    @Provides
    @PerActivity
    @ActivityContext
    protected Context provideContext() { return mActivity; }

    @Provides
    @PerActivity
    @ActivityFragmentManager
    protected FragmentManager provideFragmentManager() { return mActivity.getSupportFragmentManager(); }

    @Provides
    @PerActivity
    protected Navigator provideNavigator() { return new ActivityNavigator(mActivity); }

    @Provides
    @PerActivity
    protected NavigatorHelper provideNavigatorHelper(Navigator navigator) {
        return new NavigatorHelper(navigator);
    }

    @Provides
    @PerActivity
    protected SimpleGlideLoader provideDefaultGlide() {
        return new SimpleGlideLoader(mActivity);
    }

    @Provides
    @PerActivity
    protected TransitionGlideLoader provideTransitionGlide() {
        return new TransitionGlideLoader(mActivity);
    }
}
