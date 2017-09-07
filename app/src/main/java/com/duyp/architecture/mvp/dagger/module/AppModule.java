package com.duyp.architecture.mvp.dagger.module;

import android.app.Application;
import android.content.Context;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvp.data.local.UserManager;
import com.duyp.architecture.mvp.data.local.UserRepo;
import com.duyp.architecture.mvp.data.remote.GithubService;
import com.duyp.architecture.mvp.data.remote.ServiceFactory;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObject;

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
    static Gson provideGson() {
        return ServiceFactory.makeGson();
    }

    @Provides
    @Singleton
    CustomSharedPreferences provideMySharedPreferences(Context context) {
        return CustomSharedPreferences.getInstance(context);
    }

    @Provides
    UserRepo provideUserDataStore(CustomSharedPreferences sharedPreferences, Gson gson) {
        return new UserRepo(sharedPreferences, gson);
    }

    @Provides
    @Singleton
    protected UserManager provideUserManager(Context context, UserRepo userDataStore, Realm realm,
                                             EventBus eventBus, GithubService service) {
        return new UserManager(context, userDataStore, realm, eventBus, service);
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    Realm provideRealm(Context context) {
        Realm.init(context);
        int schemaVersion = 1; // current version
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .schemaVersion(schemaVersion)
                .migration((realm, oldVersion, newVersion) -> {
                    // migrate Realm for new version here
                })
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        return Realm.getDefaultInstance();
    }
}