package com.duyp.architecture.mvp.dagger.component;

import android.content.Context;

import com.duyp.architecture.mvp.app.MyApplication;
import com.duyp.architecture.mvp.dagger.module.AppModule;
import com.duyp.architecture.mvp.dagger.module.DataModule;
import com.duyp.architecture.mvp.dagger.module.NetworkModule;
import com.duyp.architecture.mvp.dagger.module.UserModule;
import com.duyp.architecture.mvp.dagger.qualifier.ApplicationContext;
import com.duyp.architecture.mvp.data.local.dao.IssueDao;
import com.duyp.architecture.mvp.data.local.dao.RepositoryDao;
import com.duyp.architecture.mvp.data.local.user.UserDataStore;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.remote.GithubService;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

/**
 * Created by Duy Pham on 4/30/17.
 *
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                NetworkModule.class,
                DataModule.class
        }
)
public interface AppComponent {

    @ApplicationContext Context context();

    UserManager userManager();

    UserDataStore userRepo();

    Realm realm();

    GithubService askTutorService();

    UserComponent getUserComponent(UserModule userModule);

    RepositoryDao repositoryDao();

    IssueDao issueDao();

    void inject(MyApplication application);
}
