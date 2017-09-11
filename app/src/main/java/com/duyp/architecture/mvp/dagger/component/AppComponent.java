package com.duyp.architecture.mvp.dagger.component;

import android.content.Context;

import com.duyp.architecture.mvp.app.AppDatabase;
import com.duyp.architecture.mvp.app.MyApplication;
import com.duyp.architecture.mvp.dagger.module.AppModule;
import com.duyp.architecture.mvp.dagger.module.NetworkModule;
import com.duyp.architecture.mvp.dagger.module.UserModule;
import com.duyp.architecture.mvp.dagger.qualifier.ApplicationContext;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.local.user.UserRepo;
import com.duyp.architecture.mvp.data.remote.GithubService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Duy Pham on 4/30/17.
 *
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                NetworkModule.class
        }
)
public interface AppComponent {

    @ApplicationContext Context context();

    UserManager userManager();

    UserRepo userRepo();

    AppDatabase appDatabase();

    GithubService askTutorService();

    UserComponent getUserComponent(UserModule userModule);

    void inject(MyApplication application);
}
