package com.duyp.architecture.mvp.dagger.component;

import android.arch.lifecycle.LifecycleOwner;

import com.duyp.architecture.mvp.dagger.module.FragmentModule;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.ui.login.LoginFragment;
import com.duyp.architecture.mvp.ui.profile.ProfileFragment;
import com.duyp.architecture.mvp.ui.repositories.RepositoriesFragment;
import com.duyp.architecture.mvp.ui.repository_detail.RepositoryDetailFragment;
import com.duyp.architecture.mvp.ui.repository_detail.commits.CommitFragment;
import com.duyp.architecture.mvp.ui.repository_detail.issues.IssuesFragment;
import com.duyp.architecture.mvp.ui.user_repositories.UserRepositoryFragment;

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
@PerFragment
@Component(dependencies = {AppComponent.class}, modules = {FragmentModule.class})
public interface FragmentComponent {

    LifecycleOwner lifeCycleOwner();

    void inject(LoginFragment fragment);
    void inject(RepositoriesFragment fragment);
    void inject(UserRepositoryFragment fragment);
    void inject(RepositoryDetailFragment fragment);
    void inject(IssuesFragment fragment);
    void inject(CommitFragment fragment);
    void inject(ProfileFragment fragment);
}
