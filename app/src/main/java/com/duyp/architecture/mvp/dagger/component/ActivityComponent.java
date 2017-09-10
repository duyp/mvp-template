package com.duyp.architecture.mvp.dagger.component;

import android.support.v4.app.FragmentManager;

import com.duyp.androidutils.navigator.Navigator;
import com.duyp.architecture.mvp.app.AppDatabase;
import com.duyp.architecture.mvp.dagger.module.ActivityModule;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityFragmentManager;
import com.duyp.architecture.mvp.dagger.scopes.PerActivity;
import com.duyp.architecture.mvp.ui.MainActivity;

import dagger.Component;

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
@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

    @ActivityFragmentManager
    FragmentManager defaultFragmentManager();

    Navigator navigator();

    void inject(MainActivity activity);
}
