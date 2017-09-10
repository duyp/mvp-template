package com.duyp.architecture.mvp.dagger.module;

import android.app.Application;
import android.content.Context;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvp.app.AppDatabase;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.local.user.UserRepo;
import com.duyp.architecture.mvp.data.remote.GithubService;
import com.duyp.architecture.mvp.data.remote.ServiceFactory;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by air on 4/30/17.
 * Module for app component
 */

@Module
public class AppModule {

    protected Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {
        return AppDatabase.getDatabase(application);
    }

    @Provides
    @Singleton
    static Gson provideGson() {
        return ServiceFactory.makeGsonForRealm();
    }

    @Provides
    @Singleton
    CustomSharedPreferences provideMySharedPreferences(Context context) {
        return CustomSharedPreferences.getInstance(context);
    }

    @Provides
    @Singleton
    UserRepo provideUserRepo(CustomSharedPreferences sharedPreferences, Gson gson, AppDatabase appDatabase) {
        return new UserRepo(appDatabase, sharedPreferences, gson);
    }

    @Provides
    @Singleton
    protected UserManager provideUserManager(Context context, UserRepo userDataStore,
                                             EventBus eventBus, GithubService service) {
        return new UserManager(context, userDataStore, eventBus, service);
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return EventBus.getDefault();
    }
}